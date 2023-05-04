package com.PetarsCalorieTracker.food;

import com.PetarsCalorieTracker.price.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;

public class FoodQuantityTest {

    private Food tomatoes;
    private Food chickenBrest;
    private Food ketchup;

    private Food oliveOil;
    private Food onion;
    private Food mushrooms;
    private Food breadCrumbs;
    private Food wholeEgg;
    private Food skusaFish;
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
                new BigDecimal(80), //kcal
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

        skusaFish = new Food(
                "mackerel",
                new BigDecimal(184), //kcal
                new BigDecimal(19), // proteins
                BigDecimal.ZERO, // carbs
                new BigDecimal(12), // fats
                new BigDecimal(3), // saturated fats
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

    @Test
    public void testThatNothingIs0grams(){
        var foodQuantity = new FoodQuantity();
        var listOfFood = new ArrayList<FoodQuantity>();
        FoodQuantity result = foodQuantity.createFoodFromIngredients(listOfFood, "no name", Optional.empty());
        assertEquals(BigDecimal.ZERO, result.getQuantityInGrams());
    }

    @Test
    public void testThat50gramsOf1FoodIs50Grams(){
        var tomatoes50g = new FoodQuantity(new BigDecimal(50), tomatoes);
        var ingredients = new ArrayList<FoodQuantity>();
        ingredients.add(tomatoes50g);

        FoodQuantity result = new FoodQuantity().createFoodFromIngredients(ingredients, "tomatoes 50g", Optional.empty());
        assertEquals(new BigDecimal(50), result.getQuantityInGrams());
    }

    @Test
    public void testThat50gramsPlus20GramsIs70Grams(){
        var chickenBreast50g = new FoodQuantity(new BigDecimal(50), chickenBrest);
        var ketchup20g = new FoodQuantity(new BigDecimal(20), ketchup);
        var ingredients = new ArrayList<FoodQuantity>();
        ingredients.add(chickenBreast50g);
        ingredients.add(ketchup20g);

        FoodQuantity result = new FoodQuantity().createFoodFromIngredients(ingredients, "chicken breast with ketchup", Optional.empty());
        assertEquals(new BigDecimal(70), result.getQuantityInGrams());
    }

    @Test
    public void assertThat200gOfMushroomsIs44kcal(){
        var mushrooms200g = new FoodQuantity(new BigDecimal(200), mushrooms);
        var listOfFood = new ArrayList<FoodQuantity>();
        listOfFood.add(mushrooms200g);

        FoodQuantity result = new FoodQuantity().createFoodFromIngredients(listOfFood, "mushrooms 200g", Optional.empty());
        assertEquals(new BigDecimal(44), result.toKcal().setScale(0, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void assertThat150gOfChickenBreastAnd20gOfBreadcrumbsHas260kcal(){
        var chickenBreast50g = new FoodQuantity(new BigDecimal(150), chickenBrest);
        var breadCrumbs20g = new FoodQuantity(new BigDecimal(20), breadCrumbs);
        var ingredients = new ArrayList<FoodQuantity>();
        ingredients.add(chickenBreast50g);
        ingredients.add(breadCrumbs20g);

        FoodQuantity result = new FoodQuantity().createFoodFromIngredients(ingredients, "chicken breast with breadcrumbs", Optional.empty());
        assertEquals(new BigDecimal(260), result.toKcal().setScale(0, BigDecimal.ROUND_HALF_UP));
    }


    @Test
    public void assertThat175gOfSkusaFish10gOfKetchup20gOfOilHas413Kcal(){
        var skusa175g = new FoodQuantity(new BigDecimal(175), skusaFish);
        var ketchup10g = new FoodQuantity(new BigDecimal(10), ketchup);
        var oil20g = new FoodQuantity(new BigDecimal(20), oliveOil);
        var ingredients = new ArrayList<FoodQuantity>();
        ingredients.add(skusa175g);
        ingredients.add(ketchup10g);
        ingredients.add(oil20g);

        FoodQuantity result = new FoodQuantity().createFoodFromIngredients(ingredients, "n", Optional.empty());
        assertTrue(new BigDecimal(249).compareTo(result.getFood().getKcalPer100g()) > 0);
        assertTrue(new BigDecimal(248.5).compareTo(result.getFood().getKcalPer100g()) < 0);
        assertEquals(new BigDecimal(510), result.toKcal().setScale(0, BigDecimal.ROUND_HALF_UP));
    }


    @Test
    public void testThat137gOfChickenBreast39gOfWholeEgg217gOfMushroomsEquals42Point46Proteins(){
        var chickenBreast137g = new FoodQuantity(new BigDecimal(137), chickenBrest);
        var wholeEgg39g = new FoodQuantity(new BigDecimal(39), wholeEgg);
        var mushrooms217g = new FoodQuantity(new BigDecimal(217), mushrooms);
        var ingredients = new ArrayList<FoodQuantity>();
        ingredients.add(chickenBreast137g);
        ingredients.add(wholeEgg39g);
        ingredients.add(mushrooms217g);

        FoodQuantity result = new FoodQuantity().createFoodFromIngredients(ingredients, "", Optional.empty());
        assertEquals(new BigDecimal(42.46).setScale(2, BigDecimal.ROUND_HALF_UP), result.toProteins().setScale(2, BigDecimal.ROUND_HALF_UP));
    }

}
