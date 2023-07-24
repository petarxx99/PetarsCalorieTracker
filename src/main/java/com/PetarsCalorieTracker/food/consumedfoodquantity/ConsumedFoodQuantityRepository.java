package com.PetarsCalorieTracker.food.consumedfoodquantity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface ConsumedFoodQuantityRepository extends JpaRepository<ConsumedFoodQuantity, Long> {

    @Query(value = "SELECT cfood.* FROM consumed_food_quantity cfood WHERE " +
            "DATE(cfood.time_of_consumption) = :date", nativeQuery = true)
    public List<ConsumedFoodQuantity> getConsumedFoodInADay(@Param("date")LocalDate date);

    /* FETCH is neccessary because I put the strategy for fetch to be eager, not lazy. I want all the data
     * from the joined tables to be fetched immediatelly. */
    @Query(value = "SELECT cfood FROM ConsumedFoodQuantity cfood LEFT JOIN FETCH " +
            "cfood.consumedFood food WHERE " +
            "food.kcalPer100g >= :minCalories")
    public List<ConsumedFoodQuantity> getHighCalorieFood(
            @Param("minCalories") double minCalories
    );

    @Query(value = "SELECT foodquantity.* FROM consumed_food_quantity foodquantity LEFT JOIN " +
            "food ON " +
            "foodquantity.fk_consumed_food_id = food.food_id " +
            "LEFT JOIN people_basic_info people ON " +
            "people.person_id = foodquantity.fk_person_that_eats_id " +
            "WHERE " +
            "DATE(foodquantity.time_of_consumption) = :date AND " +
            "foodquantity.consumed_food_in_grams * food.kcal_per_100g / 100.00 >= :minCalories"
            ,nativeQuery = true)
    public List<ConsumedFoodQuantity> getHighCalorieFoodOfTheDay(
            @Param("date") LocalDate date,
            @Param("minCalories") double minCalories
            );


    @Query(value = "SELECT foodquantity.* FROM consumed_food_quantity foodquantity LEFT JOIN " +
            "food ON " +
            "foodquantity.fk_consumed_food_id = food.food_id WHERE " +
            "DATE(foodquantity.time_of_consumption) >= :start_date AND " +
            "foodquantity.consumed_food_in_grams * food.kcal_per_100g / 100.00 >= :minCalories"
            ,nativeQuery = true)
    public List<ConsumedFoodQuantity> getFoodAndDaysWhereCaloriesWhereAboveTheThreshold(
            @Param("start_date") LocalDate startDate,
            @Param("minCalories") double minCalories
    );


    @Query(
            value = "SELECT * FROM \n" +
                    " (SELECT \n" +
                    " cfq.*, food.*, people.*, pwl.*, \n" +
                    "DATE(cfq.time_of_consumption) as day_of_consumption, \n" +
                    " (SELECT SUM(inner_cfq.consumed_food_in_grams * food.kcal_per_100g)\n" +
                    "     FROM consumed_food_quantity inner_cfq \n" +
                    "     WHERE inner_cfq.fk_person_that_eats_id = people.person_id AND \n" +
                    " DATE(inner_cfq.time_of_consumption) = day_of_consumption \n" +
                    " ) AS daily_kcal_times_100 \n" +
                    " FROM consumed_food_quantity cfq \n" +
                    "LEFT JOIN food ON \n" +
                    "food.food_id = cfq.fk_consumed_food_id LEFT JOIN\n" +
                    "people_basic_info people ON \n" +
                    "people.person_id = cfq.fk_person_that_eats_id \n" +
                    "LEFT JOIN people_weight_loss pwl ON pwl.basic_persons_info_id = people.person_id   \n" +
                    "WHERE \n" +
                    "cfq.time_of_consumption >= :start_date AND \n" +
                    "cfq.time_of_consumption <= :end_date AND \n" +
                    "people.person_id = :id_of_the_person \n" +
                    ") inner_query \n" +
                    "WHERE inner_query.daily_kcal_times_100 / 100.00 > :minCalories",
            nativeQuery = true
    )
    public List<ConsumedFoodQuantity> getFoodWhereApersonAteOverXnumberOfKcalThatDay(
            @Param("start_date") @NonNull LocalDate startDate,
            @Param("end_date") @NonNull LocalDate endDate,
            @Param("minCalories") double minCalories,
            @Param("id_of_the_person") long idOfThePerson
    );



}
