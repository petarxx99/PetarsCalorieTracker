package com.PetarsCalorieTracker.person.roles;

import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    /* roles is the name of the field of the PersonBasicInfo class. */
    @ManyToMany(cascade=CascadeType.ALL, mappedBy="roles")
    private Set<PersonBasicInfo> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<PersonBasicInfo> getUsers() {
        return users;
    }

    public void setUsers(Set<PersonBasicInfo> users) {
        this.users = users;
    }

    public Roles(){}

    public Roles(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Roles(Role role){
        this.role = role;
    }
}
