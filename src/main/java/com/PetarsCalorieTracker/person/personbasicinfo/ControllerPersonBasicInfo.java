package com.PetarsCalorieTracker.person.personbasicinfo;

import com.PetarsCalorieTracker.controllers.MyResponse;
import com.PetarsCalorieTracker.person.roles.Role;
import com.PetarsCalorieTracker.person.roles.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
