package com.PetarsCalorieTracker.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT f FROM Food f WHERE " +
            "f.kcalPer100g <= :minKcalPer100g AND " +
            "f.kcalPer100g >= :maxKcalPer100g")
    public List<Food> getFoodByKcal(@Param("minKcalPer100g") double minKcalPer100g,
                                    @Param("maxKcalPer100g") double maxKcalPer100g);

    @Query(value = "SELECT f FROM Food f WHERE " +
    "f.foodName LIKE :name")
    public List<Food> getFoodByName(@Param("name") String name);

    @Query(value = "SELECT f FROM Food f WHERE " +
            "f.proteinsPer100g > :min_proteins")
    public List<Food> getFoodByMinimumAmountOfProtein(@Param("min_proteins") BigDecimal minProteins);

    @Query(value = "SELECT f FROM Food f WHERE " +
            "f.kcalPer100g / f.proteinsPer100g < :maxKcalToProteinsRatio")
    public List<Food> getFoodByMaximumKcalToProteinsRatio(@Param("maxKcalToProteinsRatio") BigDecimal maxKcalToProteinsRatio);


}
