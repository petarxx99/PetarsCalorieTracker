package com.PetarsCalorieTracker.controllers.authentication.passwordsmatcher;

public interface PasswordsMatcher {
    public boolean passwordMatchesEncryptedOne(String unencryptedPassword, String encryptedPassword);

}
