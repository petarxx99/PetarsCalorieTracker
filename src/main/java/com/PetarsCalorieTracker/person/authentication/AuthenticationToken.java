package com.PetarsCalorieTracker.person.authentication;

import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name="authentication_token")
public class AuthenticationToken {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name="user_id", referencedColumnName = "person_id")
    private PersonBasicInfo personBasicInfo;

    @Column(name="token_value", columnDefinition = "varbinary(256) NOT NULL")
    private byte[] tokenValue;

    @Column(name="expiration", nullable = false)
    private LocalDateTime expiration;

    @Column(name="moment_of_creation", nullable = false)
    private LocalDateTime momentOfCreation;

    @Column(name="is_valid", nullable = false)
    private Boolean isValid;



    public AuthenticationToken(){}

    public AuthenticationToken(byte[] tokenValue, LocalDateTime expiration, LocalDateTime momentOfCreation, Boolean isValid) {
        this.tokenValue = tokenValue;
        this.expiration = expiration;
        this.momentOfCreation = momentOfCreation;
        this.isValid = isValid;
    }

    public AuthenticationToken(Long userId, byte[] tokenValue, LocalDateTime expiration, LocalDateTime momentOfCreation, Boolean isValid) {
        this.userId = userId;
        this.tokenValue = tokenValue;
        this.expiration = expiration;
        this.momentOfCreation = momentOfCreation;
        this.isValid = isValid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(byte[] tokenValue) {
        this.tokenValue = tokenValue;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public LocalDateTime getMomentOfCreation() {
        return momentOfCreation;
    }

    public void setMomentOfCreation(LocalDateTime momentOfCreation) {
        this.momentOfCreation = momentOfCreation;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public PersonBasicInfo getPersonBasicInfo() {
        return personBasicInfo;
    }

    public void setPersonBasicInfo(PersonBasicInfo personBasicInfo) {
        this.personBasicInfo = personBasicInfo;
    }

    @Override
    public String toString() {
        return "AuthenticationToken{" +
                "userId=" + userId +
                ", tokenValue=" + Arrays.toString(tokenValue) +
                ", expiration=" + expiration +
                ", momentOfCreation=" + momentOfCreation +
                ", isValid=" + isValid +
                '}';
    }
}
