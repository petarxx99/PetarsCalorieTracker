package com.PetarsCalorieTracker.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void setup() throws Exception{
        tomatoes = new Food("tomatoes",
                new BigDecimal(18),  //kcal
                new BigDecimal(0.88), //proteins
                new BigDecimal(3.92), //carbs
                new BigDecimal(0.2), //fats
                BigDecimal.ZERO, // saturated fats
                BigDecimal.ZERO, // trans fats
                new BigDecimal(1.2), // fiber
                (short) 50, //portion size in grams
                Optional.of(new Price(new BigDecimal(8)))
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
                Optional.of(new Price(new BigDecimal(8.5)))
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
                Optional.of(new Price(new BigDecimal(0.3)))
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
                Optional.of(new Price(new BigDecimal(2.2)))

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
                Optional.of(new Price(new BigDecimal(0.5)))
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
                Optional.of(new Price(new BigDecimal(0.6)))
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
                Optional.of(new Price(new BigDecimal(0.25)))
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
                Optional.of(new Price(new BigDecimal(0.3)))
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
                Optional.of(new Price(new BigDecimal(1)))
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
                Optional.of(new Price(new BigDecimal(0.4)))
        );



    }


    @Test
    public void testThatMixingNothingIs0grams(){
        var foodQuantity = new FoodQuantity();
        var listOfFood = new ArrayList<FoodQuantity>();
        foodQuantity.mixWithAListOfFood("no name", listOfFood );
        assertEquals(BigDecimal.ZERO, foodQuantity.getQuantityInGrams());
    }
}
