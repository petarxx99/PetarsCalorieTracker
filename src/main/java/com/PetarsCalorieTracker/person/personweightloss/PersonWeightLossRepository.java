package com.PetarsCalorieTracker.person.personweightloss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PersonWeightLossRepository extends JpaRepository<PersonWeightLoss, Long> {

/* FETCH is neccessary because I put the strategy for fetch to be eager, not lazy. I want all the data
* from the joined tables to be fetched immediatelly. */
    @Query(value = "SELECT person FROM PersonWeightLoss person LEFT JOIN FETCH " +
            "person.personBasicInfo basicPerson LEFT JOIN FETCH " +
            "person.dailyMassesInKilograms LEFT JOIN FETCH " +
            "person.consumedFoodQuantities WHERE " +
            "basicPerson.firstName LIKE CONCAT(:first_name, '%')")
    public List<PersonWeightLoss> getPersonByFirstNameAndHisFood(@Param("first_name") @NonNull String firstName);

    @Query(value = "SELECT person FROM PersonWeightLoss person LEFT JOIN FETCH " +
            "person.personBasicInfo basicPerson LEFT JOIN FETCH " +
            "person.dailyMassesInKilograms LEFT JOIN FETCH " +
            "person.consumedFoodQuantities WHERE " +
            "basicPerson.lastName LIKE CONCAT(:last_name, '%')")
    public List<PersonWeightLoss> getPersonByLastNameAndHisFood(@Param("last_name") @NonNull String lastName);

    @Query(value = "SELECT person FROM PersonWeightLoss person LEFT JOIN FETCH " +
            "person.personBasicInfo LEFT JOIN FETCH " +
            "person.dailyMassesInKilograms WHERE " +
            "person.personBasicInfo.id = :id")
    public PersonWeightLoss getPersonById(@Param("id") long id);


    @Query(value = "SELECT person FROM PersonWeightLoss person LEFT JOIN " +
            "person.personBasicInfo LEFT JOIN " +
            "person.dailyMassesInKilograms LEFT JOIN " +
            "person.consumedFoodQuantities WHERE " +
            "person.personBasicInfo.id = :id")
    public PersonWeightLoss getPersonByIdAndHisFood(@Param("id") long id);

}
