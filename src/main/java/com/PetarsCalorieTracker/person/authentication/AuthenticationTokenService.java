package com.PetarsCalorieTracker.person.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationTokenService {

    private AuthenticationTokenRepository repository;

    @Autowired
    public AuthenticationTokenService(AuthenticationTokenRepository repository){
        this.repository = repository;
    }

    public Optional<AuthenticationToken> findById(long id){
        return repository.findById(id);
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }

    public int setValidTrue(long userId){
        return repository.setValidTrue(userId);
    }

    public int setValidFalse(long userId){
        return repository.setValidFalse(userId);
    }

    public int changeExpiration(LocalDateTime expiration, long userId){
        return repository.changeExpiration(expiration, userId);
    }

    public int changeToken(
            long userId,
            LocalDateTime expiration,
            LocalDateTime momentOfCreation,
            byte[] tokenValue,
            boolean isValid){
        return repository.changeToken(userId, expiration, momentOfCreation, tokenValue, isValid);
    }

    public int invalidateToken(long userId, byte[] resetValue){
        return repository.invalidateToken(userId, resetValue);
    }

}
