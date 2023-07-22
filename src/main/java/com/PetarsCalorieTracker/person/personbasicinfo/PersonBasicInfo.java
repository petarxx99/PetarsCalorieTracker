package com.PetarsCalorieTracker.person.personbasicinfo;

import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
@Table(name = "people_basic_info")
public class PersonBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;


    @Column(name = "first_name", nullable = false)
    private String firstName;


    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;


    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", nullable = false)
    private String email;


    @Column(name = "password", nullable = false)
    private byte[] password;


    @OneToOne(mappedBy = "personBasicInfo")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PersonWeightLoss personWeightLossInfo;

    public PersonBasicInfo(){}

    public PersonBasicInfo(String firstName, String lastName, String username, String countryOfOrigin, LocalDate dateOfBirth,  String email,  byte[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryOfOrigin = countryOfOrigin;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
    }

    public PersonBasicInfo(Long id,  String firstName,  String lastName, String username, String countryOfOrigin,  LocalDate dateOfBirth,  String email,  byte[] password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryOfOrigin = countryOfOrigin;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName) {
        this.firstName = firstName;
    }

    
    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth( LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PersonWeightLoss getPersonWeightLossInfo() {
        return personWeightLossInfo;
    }

    public void setPersonWeightLossInfo(PersonWeightLoss personWeightLossInfo) {
        this.personWeightLossInfo = personWeightLossInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "PersonBasicInfo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
