package com.PetarsCalorieTracker.person.personweightloss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PersonWeightLossRepository extends JpaRepository<PersonWeightLoss, Long> {



/* FETCH is neccessary because I put the strategy for fetch to be eager, not lazy. I want all the data
* from the joined tables to be fetched immediatelly. */
    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo basicPerson LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities WHERE " +
            "basicPerson.firstName LIKE CONCAT(:first_name, '%')")
    public List<PersonWeightLoss> getPersonByFirstNameAndHisFood(@Param("first_name") @NonNull String firstName);

    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo basicPerson LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities WHERE " +
            "basicPerson.lastName LIKE CONCAT(:last_name, '%')")
    public List<PersonWeightLoss> getPersonByLastNameAndHisFood(@Param("last_name") @NonNull String lastName);

    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms WHERE " +
            "personWL.personBasicInfo.id = :id")
    public PersonWeightLoss getPersonById(@Param("id") long id);


    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms WHERE " +
            "person.username = :username")
    public Optional<PersonWeightLoss> getPersonByUsername(@Param("username") String username);


    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms dm LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
            "cfq.consumedFood food " +
            "WHERE " +
            "person.username = :username")
    public Optional<PersonWeightLoss> getPersonAndFoodByUsername(@Param("username") String username);


    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms dm LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
            "cfq.consumedFood food " +
            "WHERE " +
            "person.id = :id AND " +
            "(cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment) AND " +
            "(dm.date BETWEEN DATE(:start_moment) AND DATE(:end_moment))")
    public PersonWeightLoss getPersonByIdAndHisFoodAndWeightFromMomentAToMomentB(
            @Param("id") long id,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment);

    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms dm LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
            "cfq.consumedFood food " +
            "WHERE " +
            "person.email = :email AND " +
            "(cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment) AND " +
            "(dm.date BETWEEN DATE(:start_moment) AND DATE(:end_moment))")
    public PersonWeightLoss getPersonByEmailAndHisFoodAndWeightFromMomentAToMomentB(
            @Param("email") String email,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment);

    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms dm LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
            "cfq.consumedFood food " +
            "WHERE " +
            "person.username = :username AND " +
            "(cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment) AND " +
            "(dm.date BETWEEN DATE(:start_moment) AND DATE(:end_moment))")
    public PersonWeightLoss getPersonByUsernameAndHisFoodAndWeightFromMomentAToMomentB(
            @Param("username") String username,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment);

    @Query(value =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
                    "personWL.personBasicInfo person LEFT JOIN FETCH " +
                    "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
                    "cfq.consumedFood food " +
                    "WHERE " +
                    "person.id = :id AND " +
                    "(cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment)")
    public PersonWeightLoss getPersonByIdAndHisFoodFromMomentAToMomentB(
            @Param("id") long id,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment
    );

    @Query(value =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
                    "personWL.personBasicInfo person LEFT JOIN FETCH " +
                    "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
                    "cfq.consumedFood food " +
                    "WHERE " +
                    "person.username = :username AND " +
                    "(cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment)")
    public PersonWeightLoss getPersonByUsernameAndHisFoodFromMomentAToMomentB(
            @Param("username") String username,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment
    );

    @Query(value =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
                    "personWL.personBasicInfo person LEFT JOIN FETCH " +
                    "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
                    "cfq.consumedFood food " +
                    "WHERE " +
                    "person.email = :email AND " +
                    "(cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment)")
    public PersonWeightLoss getPersonByEmailAndHisFoodFromMomentAToMomentB(
            @Param("email") String email,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment
    );

    @Query(value =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
                    "personWL.personBasicInfo person LEFT JOIN FETCH " +
                    "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
                    "cfq.consumedFood food " +
                    "WHERE person.id = :id AND " +
                    "cfq.consumedFoodInGrams * food.kcalPer100g > :minimum_kcal_times_100 AND " +
                    "cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment")
    public PersonWeightLoss getPersonByIdHisFoodWhenHeAteOverXNumberOfCalories(
            @Param("id") long id,
            @Param("minimum_kcal_times_100") BigDecimal minimumKcalTimes100,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment);

    @Query(value =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
                    "personWL.personBasicInfo person LEFT JOIN FETCH " +
                    "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
                    "cfq.consumedFood food " +
                    "WHERE person.email = :email AND " +
                    "cfq.consumedFoodInGrams * food.kcalPer100g > :minimum_kcal_times_100 AND " +
                    "cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment")
    public PersonWeightLoss getPersonByEmailHisFoodWhenHeAteOverXNumberOfCalories(
            @Param("email") String email,
            @Param("minimum_kcal_times_100") BigDecimal minimumKcalTimes100,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment);

    @Query(value =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
                    "personWL.personBasicInfo person LEFT JOIN FETCH " +
                    "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
                    "cfq.consumedFood food " +
                    "WHERE person.username = :username AND " +
                    "cfq.consumedFoodInGrams * food.kcalPer100g > :minimum_kcal_times_100 AND " +
                    "cfq.timeOfConsumption BETWEEN :start_moment AND :end_moment")
    public PersonWeightLoss getPersonByUsernameHisFoodWhenHeAteOverXNumberOfCalories(
            @Param("username") String username,
            @Param("minimum_kcal_times_100") BigDecimal minimumKcalTimes100,
            @Param("start_moment") LocalDateTime startMoment,
            @Param("end_moment") LocalDateTime endMoment);




}
