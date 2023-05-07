package com.PetarsCalorieTracker.person;

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

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @NonNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;


    @OneToOne(mappedBy = "personBasicInfo")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PersonWeightLoss personWeightLossInfo;

    public PersonBasicInfo(){}

    public PersonBasicInfo(@NonNull String firstName, @NonNull String lastName, String countryOfOrigin, @NonNull LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryOfOrigin = countryOfOrigin;
        this.dateOfBirth = dateOfBirth;
    }

    public PersonBasicInfo(Long id, @NonNull String firstName, @NonNull String lastName, String countryOfOrigin, @NonNull LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryOfOrigin = countryOfOrigin;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @NonNull
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NonNull LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PersonWeightLoss getPersonWeightLossInfo() {
        return personWeightLossInfo;
    }

    public void setPersonWeightLossInfo(PersonWeightLoss personWeightLossInfo) {
        this.personWeightLossInfo = personWeightLossInfo;
    }

    @Override
    public String toString() {
        return "PersonBasicInfo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
