package com.PetarsCalorieTracker.controllers.authentication.passwordsmatcher;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPasswordsMatcher implements PasswordsMatcher {

    private final PasswordEncoder passwordEncoder;

    public MyPasswordsMatcher(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean passwordMatchesEncryptedOne(String unencryptedPassword, String encryptedPassword) {
        return passwordEncoder.matches(unencryptedPassword, encryptedPassword);
    }


}
