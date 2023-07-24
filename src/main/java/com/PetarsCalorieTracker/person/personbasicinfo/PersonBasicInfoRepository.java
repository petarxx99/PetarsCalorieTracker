package com.PetarsCalorieTracker.person.personbasicinfo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PersonBasicInfoRepository extends JpaRepository<PersonBasicInfo, Long> {

    @Query(value =
    "SELECT person FROM PersonBasicInfo person LEFT JOIN FETCH " +
            "person.authenticationToken " +
            "WHERE person.email = :email")
    public Optional<PersonBasicInfo> findByEmail(@Param("email") String email);

    @Query(value =
            "SELECT person FROM PersonBasicInfo person LEFT JOIN FETCH " +
                    "person.authenticationToken " +
                    "WHERE person.username = :username")
    public Optional<PersonBasicInfo> findByUsername(@Param("username") String username);


    @Transactional
    @Modifying
    @Query(value =
    "UPDATE PersonBasicInfo person SET person.username = :new_username WHERE " +
            "person.id = :id")
    public int updatePersonsUsernameFindHimById(@Param("id") long id, @Param("new_username") String newUsername);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE PersonBasicInfo person SET person.password = :new_password WHERE " +
                    "person.id = :id")
    public int updatePersonsPasswordFindHimById(@Param("id") long id, @Param("new_password") String newPassword);



}
