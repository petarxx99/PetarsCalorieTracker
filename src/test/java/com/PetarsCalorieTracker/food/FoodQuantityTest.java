package com.PetarsCalorieTracker.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FoodQuantityTest {

    private Food tomatoes;
    private Food chickenBrest;
    private Food ketchup;

    private Food oliveOil;
    private Food onion;
    private Food mushrooms;
    private Food breadCrumbs;
    private Food wholeEgg;
    private Food mackerel;
    private Food strawberries;

    @BeforeEach
    private void setup(){
        tomatoes = new Food("tomatoes",
                new BigDecimal(18),  //kcal
                new BigDecimal(0.88), //proteins
                new BigDecimal(3.92), //carbs
                new BigDecimal(0.2), //fats
                BigDecimal.ZERO, // saturated fats
                BigDecimal.ZERO, // trans fats
                new BigDecimal(1.2), // fiber
                (short) 50, //portion size in grams
                new BigDecimal(0.8), // price
                new BigDecimal(100) // mass in grams of the quantity with the price
        );

        chickenBrest = new Food(
                "chicken breast",
                new BigDecimal(120), //kcal
                new BigDecimal(22.5), //proteins
                BigDecimal.ZERO, // carbs
                new BigDecimal(2.62), // fats
                new BigDecimal(0.57), //saturated fats
                BigDecimal.ZERO, // trans fats
                BigDecimal.ZERO, // fiber
                (short) 200, // portion size in grams
                new BigDecimal(8.5), // price
                new BigDecimal(1000) // mass in grams in relation to the price
        );

        ketchup = new Food(
                "ketchup",
                new BigDecimal(77), //kcal
                new BigDecimal(1.1), // proteins
                new BigDecimal(18), // carbs
                new BigDecimal(0.1), // fats
                new BigDecimal(0), // saturated fats
                new BigDecimal(0), // bad trans fats
                new BigDecimal(0), // fiber
                (short) 15, // portion size in grams
                new BigDecimal(3), // price
                new BigDecimal(1000) // mass in grams in relation to price
        );

        oliveOil = new Food(
                "olive oil",
                new BigDecimal(900), //kcal
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                new BigDecimal(100), // fats
                new BigDecimal(13), // saturated fats
                BigDecimal.ZERO, // trans fats
                BigDecimal.ZERO,
                (short) 21, // portion size in grams
                new BigDecimal(22), // price
                new BigDecimal(1000) // mass in grams in relation to price

        );

        onion = new Food(
                "onion",
                new BigDecimal(41), // kcal
                new BigDecimal(1.3), //proteins
                new BigDecimal(9.5), //carbs
                new BigDecimal(0.2), // fats
                BigDecimal.ZERO, // saturated fats
                BigDecimal.ZERO, //trans fats
                new BigDecimal(1.3), //fiber
                (short) 15, // portion size in grams,
                new BigDecimal(5), //price
                new BigDecimal(1000) // mass in grams in relation to the price
        );

        mushrooms = new Food(
                "mushrooms",
                new BigDecimal(22), // kcal
                new BigDecimal(3.1), // proteins
                new BigDecimal(3.26), //carbs
                new BigDecimal(0.34), //fats
                new BigDecimal(0.05), // saturated fats
                BigDecimal.ZERO, // trans fats
                BigDecimal.ONE, // fiber
                (short) 100, // portion size in grams
                new BigDecimal(6), //price
                new BigDecimal(1000) // mass in grams in relation to the price
                );

        wholeEgg = new Food(
                "whole egg",
                new BigDecimal(150), //kcal
                new BigDecimal(12.58), //proteins
                new BigDecimal(0.78), //carbs
                new BigDecimal(10), // fats
                new BigDecimal(3.1), // saturated fats
                BigDecimal.ZERO, // trans fats
                BigDecimal.ZERO, // fiber
                (short) 45, // portion size in grams (1 egg)
                new BigDecimal(2.5), // price
                new BigDecimal(1000) // mass in grams in relation to the price
        );

        breadCrumbs = new Food(
                "breadcrumbs",
                new BigDecimal(400), //kcal
                new BigDecimal(9), // proteins
                new BigDecimal(76), // carbs
                new BigDecimal(5), //fats
                new BigDecimal(1.2), // saturated fats
                BigDecimal.ZERO, // trans fats
                new BigDecimal(4.5), // fiber
                (short) 40, // portion size in grams
                new BigDecimal(3), // price
                new BigDecimal(1000) // mass in grams in relation to the price
        );

        mackerel = new Food(
                "mackerel",
                new BigDecimal(265), //kcal
                new BigDecimal(24), // proteins
                BigDecimal.ZERO, // carbs
                new BigDecimal(18), // fats
                new BigDecimal(4.2), // saturated fats
                BigDecimal.ZERO, // trans fats
                BigDecimal.ZERO, // fiber
                (short) 180, // portion size in grams
                new BigDecimal(10), // price
                new BigDecimal(1000) // mass in grams in relation to the price
                );

        strawberries = new Food(
                "strawberries",
                new BigDecimal(32), //kcal
                new BigDecimal(0.5), // proteins
                new BigDecimal(7.7), // carbs
                new BigDecimal(0.3), // fats
                BigDecimal.ZERO, // saturated fats
                BigDecimal.ZERO, // bad trans fats
                new BigDecimal(2), // fiber
                (short) 200, // portion size in grams
                new BigDecimal(4), // price
                new BigDecimal(1000) // mass in grams in relation to the price
        );



    }


    @Test
    public void testNothingIs0grams(){
        var foodQuantity = new FoodQuantity();
        var consumedFood = new ConsumedFoodQuantity(LocalDateTime.now(), foodQuantity);
        assertEqual
    }
}
