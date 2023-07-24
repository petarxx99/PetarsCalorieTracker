package com.PetarsCalorieTracker.person.authentication;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE AuthenticationToken a SET a.isValid=true WHERE " +
            "a.userId = :user_id")
    public int setValidTrue(@Param("user_id") long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE AuthenticationToken a SET a.isValid=false WHERE " +
            "a.userId = :user_id")
    public int setValidFalse(@Param("user_id") long userId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE AuthenticationToken a " +
            "SET a.expiration = :expiration WHERE " +
            "a.userId = :user_id")
    public int changeExpiration(@Param("expiration")LocalDateTime expiration,
                                @Param("user_id") long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE AuthenticationToken a " +
            "SET a.expiration = :expiration, " +
            "a.momentOfCreation = :moment_of_creation, " +
            "a.tokenValue = :token_value, " +
            "a.isValid = :is_valid WHERE " +
            "a.userId = :user_id")
    public int changeToken(
            @Param("user_id") long userId,
            @Param("expiration")LocalDateTime expiration,
            @Param("moment_of_creation") LocalDateTime momentOfCreation,
            @Param("token_value") byte[] tokenValue,
            @Param("is_valid") boolean isValid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE AuthenticationToken a SET a.tokenValue = :reset_value, " +
            "a.isValid=false WHERE " +
            "a.userId = :user_id")
    public int invalidateToken(@Param("user_id") long userId, @Param("reset_value") byte[] resetValue);
}
