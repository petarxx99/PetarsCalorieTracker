package com.PetarsCalorieTracker.controllers.authentication;


import com.PetarsCalorieTracker.controllers.MyResponse;
import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfoService;
import com.PetarsCalorieTracker.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(originPatterns = {"*"})
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
    private final TokenService tokenService;

    private final PersonBasicInfoService personBasicInfoService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationController(TokenService tokenService, PersonBasicInfoService personBasicInfoService, PasswordEncoder passwordEncoder){
        this.tokenService = tokenService;
        this.personBasicInfoService = personBasicInfoService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    @CrossOrigin(originPatterns = {"*"})
    public MyResponse login(@RequestBody UsernamePassword usernamePassword){
        Optional<PersonBasicInfo> personOptional = personBasicInfoService.findByUsername(usernamePassword.username());
        if (personOptional.isEmpty()){
            return MyResponse.negative("username/password", "Username or password is wrong.");
        }
        PersonBasicInfo personFromDatabase = personOptional.get();

        boolean passwordsMatch = passwordEncoder.matches(usernamePassword.password(), personFromDatabase.getPassword());
        if (!passwordsMatch){
            return MyResponse.negative("username/password", "Username or password is wrong.");
        }

        List<SimpleGrantedAuthority> authorities = personFromDatabase.getRoles().stream()
                .map(oneRole -> new SimpleGrantedAuthority(oneRole.getRole().name()))
                .collect(Collectors.toList());

        String token = tokenService.generateToken(usernamePassword.username(), authorities);
        return MyResponse.positive(token);
    }
}
