package com.PetarsCalorieTracker.person.dailymass;

import com.PetarsCalorieTracker.person.dailymass.dailymasscalories.DailyCalories;
import com.PetarsCalorieTracker.person.dailymass.dailymasscalories.DailyMassAndCalories;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyMassRepository extends JpaRepository<DailyMass, Long> {


    @Query(value =
            "SELECT people.basic_persons_info_id, "+
                    "dm.mass_in_kilograms, "+
                    "dm.date, "+
                    "(SELECT SUM(innercfq.consumed_food_in_grams * innerfood.kcal_per_100g) " +
                    "FROM consumed_food_quantity innercfq LEFT JOIN food innerfood ON " +
                    "innercfq.fk_consumed_food_id = innerfood.food_id " +
                    "WHERE DATE(innercfq.time_of_consumption) = dm.date AND " +
                    "innercfq.fk_person_that_eats_id = people.basic_persons_info_id" +
                    ") as calories_consumed " +
                    "FROM people_weight_loss people LEFT JOIN daily_mass dm ON " +
                    "people.fk_id_of_persons_mass = dm.daily_mass_id LEFT JOIN people_basic_info pbi " +
                    "ON pbi.person_id = people.basic_persons_info_id " +
                    "WHERE pbi.username = :username",
            nativeQuery = true)
    public List<DailyMassAndCalories> getDailyMassAndCaloriesMultipliedBy100ByUsername(@Param("username") String username);

    @Query(value =
            "SELECT outer_query.calories_consumed_times_100 / 100.0 as caloriesConsumed " +
                    "outer_query.date as date " +
                    "FROM (" +
                    "SELECT DATE(cfq.time_of_consumption) as date, " +
                    "SUM(cfq.consumed_food_in_grams * food.kcal_per_100g) as calories_consumed_times_100 " +
                    "FROM consumed_food_quantity cfq LEFT JOIN food ON " +
                    "cfq.fk_consumed_food_id = food.food_id " +
                    "LEFT JOIN people_basic_info pbi ON " +
                    "cfq.fk_person_that_eats_id = pbi.person_id " +
                    "WHERE pbi.username = :username " +
                    "GROUP BY DATE(cfq.time_of_consumption) " +
                    ") outer_query",
            nativeQuery = true)
    public List<Tuple> getCaloriesByDayByUsername(@Param("username") String username);


    @Query(value =
            "SELECT outer_query.calories_consumed_times_100 / 100.0 as caloriesConsumed, " +
                    "outer_query.date as date " +
                    "FROM (" +
                    "SELECT DATE(cfq.time_of_consumption) as date, " +
                    "SUM(cfq.consumed_food_in_grams * food.kcal_per_100g) as calories_consumed_times_100 " +
                    "FROM consumed_food_quantity cfq LEFT JOIN food ON " +
                    "cfq.fk_consumed_food_id = food.food_id " +
                    "LEFT JOIN people_basic_info pbi ON " +
                    "cfq.fk_person_that_eats_id = pbi.person_id " +
                    "WHERE pbi.username = :username AND " +
                    "DATE(cfq.time_of_consumption) >= :start_date AND " +
                    "DATE(cfq.time_of_consumption) <= :end_date " +
                    "GROUP BY DATE(cfq.time_of_consumption) " +
                    ") outer_query",
            nativeQuery = true)
    public List<Tuple> getCaloriesByDayByUsernameFromDayAToDayB(
            @Param("username") String username,
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate);


    @Query(value = "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms dm LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
            "cfq.consumedFood food " +
            "WHERE " +
            "person.username = :username")
    public Optional<PersonWeightLoss> getPersonAndFoodByUsername(@Param("username") String username);
}
