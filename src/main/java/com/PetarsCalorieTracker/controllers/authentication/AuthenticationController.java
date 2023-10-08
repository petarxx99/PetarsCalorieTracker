package com.PetarsCalorieTracker.controllers.authentication;


import com.PetarsCalorieTracker.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(originPatterns = {"*"})
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
    private final TokenService tokenService;

    public AuthenticationController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication){
        LOG.debug("Token requested by " + authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted: " + token);

        return token;
    }
}
