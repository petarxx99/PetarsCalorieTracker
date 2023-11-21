package com.PetarsCalorieTracker.person.personbasicinfo;

import com.PetarsCalorieTracker.controllers.MyResponse;
import com.PetarsCalorieTracker.person.roles.Role;
import com.PetarsCalorieTracker.person.roles.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = {"*"})
public class ControllerPersonBasicInfo {

    private final PersonBasicInfoService service;
    private final PasswordEncoder passwordEncoder;

    public ControllerPersonBasicInfo(PersonBasicInfoService service, PasswordEncoder passwordEncoder){
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/register")
    @CrossOrigin(originPatterns = {"*"})
    public MyResponse postPersonBasicInfo(@RequestBody PersonBasicInfo person){
        String username = person.getUsername();
        String email = person.getEmail();

        if (person.getPassword() == null || person.getPassword().isEmpty()){
            return MyResponse.negative("password", "No password");
        }
        Optional<PersonBasicInfo> personByUsername = service.findByUsername(username);
        if (personByUsername.isPresent()){
            return MyResponse.negative("username", "Username already exists.");
        }
        Optional<PersonBasicInfo> personByEmail = service.findByEmail(email);
        if (personByEmail.isPresent()){
            return MyResponse.negative("email", "Email already exists.");
        }

        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        person.setRoles(new HashSet<>(){
            {
                add(new Roles(Role.ROLE_USER));
            }
        });
        service.save(person);
        return MyResponse.positive();
    }



    @CrossOrigin(originPatterns = {"*"})
    @PutMapping("/update")
    public MyResponse update(@RequestBody PersonBasicInfoOldPassword personAndPassword,
                       Authentication authentication){
        PersonBasicInfo updateInfo = personAndPassword.person();
        String oldPassword = personAndPassword.oldPassword();
        String username = authentication.getName();

        PersonBasicInfo personFromDatabase = service.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username " + username + ", not found"));

        String passwordFromDatabase = personFromDatabase.getPassword();
        boolean passwordsMatch = passwordEncoder.matches(oldPassword, passwordFromDatabase);

        if (!passwordsMatch){
            return MyResponse.negative("password", "password doesn't match");
        }

        if (service.usernameExistsInTheDatabase(updateInfo.getUsername())){
            return MyResponse.negative("username", "username already exists");
        }
        if (service.emailExistsInTheDatabase(updateInfo.getEmail())){
            return MyResponse.negative("email", "email already exists");
        }

        String newPassword = updateInfo.getPassword();

        if (oldPassword != null && newPassword != null){
            String newPasswordEncoded = passwordEncoder.encode(newPassword);
            updateInfo.setPassword(newPasswordEncoded);
        }

        personFromDatabase.updateYourselfWith(updateInfo);
        return MyResponse.positive();
    }

    @GetMapping("/get_my_info")
    @CrossOrigin(originPatterns = {"*"})
    public PersonBasicInfo getMyInfo(Authentication authentication){
        return service.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Your username wasn't found."));
    }

}
