package com.PetarsCalorieTracker.person.personbasicinfo;


import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import com.PetarsCalorieTracker.person.roles.Role;
import com.PetarsCalorieTracker.person.roles.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "people_basic_info",
indexes = {
        @Index(name="email_idx", columnList = "email", unique = true),
        @Index(name="username_idx",columnList = "username", unique = true),
        @Index(name="last_name_first_name_idx", columnList = "last_name,first_name")
})
public class PersonBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;


    @Column(name = "first_name", nullable = false)
    private String firstName;


    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;


    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;


    @Column(name = "email", nullable = false, unique = true)
    private String email;


    /* not private String password and then columnDefition = "varbinary(512) NOT NULL" */
    @Column(name = "password", nullable = false, columnDefinition = "varchar(512) NOT NULL")
    private String password;


    @OneToOne(mappedBy = "personBasicInfo")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private PersonWeightLoss personWeightLossInfo;



    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns = {@JoinColumn(name="fk_user_id")},
    inverseJoinColumns = {@JoinColumn(name="fk_role_id")})
    private Set<Roles> roles;

    public PersonBasicInfo(){}

    public PersonBasicInfo(String firstName, String lastName, String username, String countryOfOrigin, LocalDate dateOfBirth,  String email,  String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryOfOrigin = countryOfOrigin;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public PersonBasicInfo(Long id,  String firstName,  String lastName, String username, String countryOfOrigin,  LocalDate dateOfBirth,  String email,  String password) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void addRole(Roles newRole){
        if (roles == null){
           roles = new HashSet<Roles>();
        }

        roles.add(newRole);
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

    public void updateYourselfWith(PersonBasicInfo updateInfo) {
        if (updateInfo == null){
            return;
        }

        if (updateInfo.getFirstName() != null){
            setFirstName(updateInfo.getFirstName());
        }
        if (updateInfo.getLastName() != null){
            setLastName(updateInfo.getLastName());
        }
        if (updateInfo.getUsername() != null){
            setUsername(updateInfo.getUsername());
        }
        if (updateInfo.getCountryOfOrigin() != null){
            setCountryOfOrigin(updateInfo.getCountryOfOrigin());
        }
        if (updateInfo.getDateOfBirth() != null){
            setDateOfBirth(updateInfo.getDateOfBirth());
        }
        if (updateInfo.getPassword() != null){
            setPassword(updateInfo.getPassword());
        }
        if (updateInfo.getEmail() != null){
            setEmail(updateInfo.getEmail());
        }

    }
}
