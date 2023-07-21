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
            value = "SELECT outer_cfq.* FROM \n" +
                    "(\n" +
                    " SELECT \n" +
                    " cfq.consumed_food_quantity_id,\n" +
                    " cfq.time_of_consumption,\n" +
                    " cfq.consumed_food_in_grams,\n" +
                    " cfq.fk_consumed_food_id,\n" +
                    " cfq.fk_person_that_eats_id,\n" +
                    " (SELECT SUM(inner_cfq.consumed_food_in_grams * food.kcal_per_100g)\n" +
                    "     FROM consumed_food_quantity inner_cfq LEFT JOIN food ON\n" +
                    "             food.food_id = inner_cfq.fk_consumed_food_id\n" +
                    "     WHERE inner_cfq.consumed_food_quantity_id = cfq.consumed_food_quantity_id\n" +
                    "     GROUP BY DATE(inner_cfq.time_of_consumption) \n" +
                    "     ORDER BY inner_cfq.time_of_consumption \n" +
                    " ) AS daily_kcal_times_100 \n" +
                    " \n" +
                    " FROM consumed_food_quantity cfq \n" +
                    "\n" +
                    ") outer_cfq \n" +
                    "\n" +
                    "LEFT JOIN food ON \n" +
                    "food.food_id = outer_cfq.fk_consumed_food_id LEFT JOIN\n" +
                    "people_basic_info people ON \n" +
                    "people.person_id = outer_cfq.fk_person_that_eats_id \n" +
                    "\n" +
                    "WHERE outer_cfq.daily_kcal_times_100 / 100.00 > :minCalories AND " +
                    "DATE(outer_cfq.time_of_consumption) >= :start_date AND " +
                    "DATE(outer_cfq.time_of_consumption) <= :end_date AND " +
                    "people.person_id = :id_of_the_person",
            nativeQuery = true
    )
    public List<ConsumedFoodQuantity> getFoodWhereApersonAteOverXnumberOfKcal(
            @Param("start_date") @NonNull LocalDate startDate,
            @Param("end_date") @NonNull LocalDate endDate,
            @Param("minCalories") double minCalories,
            @Param("id_of_the_person") long idOfThePerson
    );



}
