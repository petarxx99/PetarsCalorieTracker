package com.PetarsCalorieTracker.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT f FROM Food f WHERE " +
            "f.kcalPer100g <= :minKcalPer100g AND " +
            "f.kcalPer100g >= :maxKcalPer100g")
    public List<Food> getFoodByKcal(@Param("minKcalPer100g") double minKcalPer100g,
                                    @Param("maxKcalPer100g") double maxKcalPer100g);
}
