package com.PetarsCalorieTracker.rangesfordatabase;

import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.food.FoodQuantity;
import com.PetarsCalorieTracker.food.consumedfoodquantity.ConsumedFoodQuantity;
import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestComparingObjectsToMakeJPQLClauses {

    @Test
    public void testThatInitializationWentFine(){
        var person = new PersonBasicInfo("Jovan", "Petrovic", "Serbia",
                LocalDate.of(2020, 2, 20), "jovan@gmail.com", new byte[]{2,3});
        var c = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>("person", "AND", new FieldComparisonFirstMethod(),
                person, person, person,
                "firstName", "lastName", "dateOfBirth");
        var c2 = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>("person", "AND", new FieldComparisonFirstMethod(),
                person, person, person);

        c2.setNamesOfFieldsThatYouWantToCompare("firstName", "dateOfBirth");
    }

    @Test
    public void testMakeAClauseForFoodOver100kcalAbove5gOfProteinAndBelow10gOfFatNameEqualsTomatoes(){
        var lowerFood = new Food("lowerFood", new BigDecimal(100),
                null);
        var biggerFood = new Food("biggerFood", null, new BigDecimal(5));
        var equalFood = new Food("tomatoes", null, null);
        String classAlias = "food";

        var c = new ComparingObjectsToMakeJPQLClauses<Food>(classAlias, "AND", new FieldComparisonFirstMethod(),
                lowerFood, biggerFood, equalFood);

        String expected = classAlias + ".foodName='tomatoes' AND " +
                classAlias + ".kcalPer100g>100 AND " +
                classAlias + ".proteinsPer100g<5";

        String actual = c.clause().orElse("NOT WORKING");
        assertTrue(expected.equals(actual));
    }

    @Test
    public void testThatHumansLastNameIsJamesBornAfter1991_10_22_bornBefore2002_8_20(){
        var personLowest = new PersonBasicInfo(null, null, null,
                LocalDate.of(1991, 10, 22), null, null);
        var personBiggest = new PersonBasicInfo(null, null, null,
                LocalDate.of(2002, 8, 20), null, null);
        var personEqual = new PersonBasicInfo(null, "James", null,
                null, null, null);

        var c = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>("person", "AND", new FieldComparisonFirstMethod(),
                personLowest, personBiggest, personEqual);

        String expected = "person.lastName='James' AND " +
                "person.dateOfBirth<'2002-08-20' AND " +
                "person.dateOfBirth>'1991-10-22'";

        String actual = c.clause().orElse("NOT WORKING");
        assertTrue(expected.equals(actual));
    }

    @Test
    public void testFoodQuantityWasEatenAfter2021_3_21_11_34_56_before_2021_4_20_10_51_12(){
        var foodQuantity = new FoodQuantity(new BigDecimal(50), null);
        var lowestConsumedFoodQuantity = new ConsumedFoodQuantity(LocalDateTime.of(2021,3,21,11,34,56), foodQuantity, null);
        var biggestConsumedFoodQuantity = new ConsumedFoodQuantity(LocalDateTime.of(2021, 4,20,10,51,12), foodQuantity, null);
        var equalConsumedFoodQuantity = new ConsumedFoodQuantity(null, foodQuantity, new PersonWeightLoss());

        var c = new ComparingObjectsToMakeJPQLClauses<ConsumedFoodQuantity>("cfq", "AND", new FieldComparisonFirstMethod(),
                lowestConsumedFoodQuantity, biggestConsumedFoodQuantity, equalConsumedFoodQuantity);
        c.setNamesOfFieldsThatYouWantToCompare("timeOfConsumption");

        String expected = "cfq.timeOfConsumption<'2021-04-20 10:51:12' AND " +
                "cfq.timeOfConsumption>'2021-03-21 11:34:56'";

        String actual = c.clause().orElse("NOT WORKING");
        assertTrue(expected.equals(actual));
    }

    @Test
    public void testFoodLessThan50point52kcalMoreThan5point34grams(){
        var lowerFood = new Food("lowerFood", new BigDecimal(50.52).setScale(2, BigDecimal.ROUND_HALF_UP),
                null);
        var biggerFood = new Food("biggerFood", null, new BigDecimal(5.34).setScale(2, BigDecimal.ROUND_HALF_UP));
        var equalFood = new Food("tomatoes", null, null);
        String classAlias = "food";

        var c = new ComparingObjectsToMakeJPQLClauses<Food>(classAlias, "AND", new FieldComparisonFirstMethod(),
                lowerFood, biggerFood, equalFood);

        String expected = classAlias + ".foodName='tomatoes' AND " +
                classAlias + ".kcalPer100g>50.52 AND " +
                classAlias + ".proteinsPer100g<5.34";

        String actual = c.clause().orElse("NOT WORKING");
        assertTrue(expected.equals(actual));
    }



}
