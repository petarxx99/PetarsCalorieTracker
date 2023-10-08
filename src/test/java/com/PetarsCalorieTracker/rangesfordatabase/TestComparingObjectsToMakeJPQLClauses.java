package com.PetarsCalorieTracker.rangesfordatabase;

import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.food.FoodQuantity;
import com.PetarsCalorieTracker.food.consumedfoodquantity.ConsumedFoodQuantity;
import com.PetarsCalorieTracker.person.dailymass.DailyMass;
import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestComparingObjectsToMakeJPQLClauses {

    @Test
    public void testThatInitializationWentFine(){
        var person = new PersonBasicInfo("Jovan", "Petrovic", "jovan", "Serbia",
                LocalDate.of(2020, 2, 20), "jovan@gmail.com", "23");
        var c = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>( new FieldComparisonFirstMethod(),
                person, person, person,
                "firstName", "lastName", "dateOfBirth");
        var c2 = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>(new FieldComparisonFirstMethod(),
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

        var c = new ComparingObjectsToMakeJPQLClauses<Food>(new FieldComparisonFirstMethod(),
                lowerFood, biggerFood, equalFood);

        String expected = classAlias + ".foodName='tomatoes' AND " +
                classAlias + ".kcalPer100g>100 AND " +
                classAlias + ".proteinsPer100g<5";

        String actual = c.clause(classAlias, "AND").orElse("NOT WORKING");
        assertEquals(expected, actual);
    }

    @Test
    public void testThatHumansLastNameIsJamesBornAfter1991_10_22_bornBefore2002_8_20(){
        var personLowest = new PersonBasicInfo(null, null, null, null,
                LocalDate.of(1991, 10, 22), null, null);
        var personBiggest = new PersonBasicInfo(null, null, null,null,
                LocalDate.of(2002, 8, 20), null, null);
        var personEqual = new PersonBasicInfo(null, "James", null,null,
                null, null, null);

        var c = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>( new FieldComparisonFirstMethod(),
                personLowest, personBiggest, personEqual);

        String expected = "person.lastName='James' AND " +
                "person.dateOfBirth<'2002-08-20' AND " +
                "person.dateOfBirth>'1991-10-22'";

        String actual = c.clause("person", "AND").orElse("NOT WORKING");
        assertEquals(expected, actual);
    }

    @Test
    public void testFoodQuantityWasEatenAfter2021_3_21_11_34_56_before_2021_4_20_10_51_12(){
        var foodQuantity = new FoodQuantity(new BigDecimal(50), new Food());
        var lowestConsumedFoodQuantity = new ConsumedFoodQuantity(LocalDateTime.of(2021,3,21,11,34,56), foodQuantity, null);
        var biggestConsumedFoodQuantity = new ConsumedFoodQuantity(LocalDateTime.of(2021, 4,20,10,51,12), foodQuantity, null);
        var equalConsumedFoodQuantity = new ConsumedFoodQuantity(null, foodQuantity, new PersonWeightLoss());

        var c = new ComparingObjectsToMakeJPQLClauses<ConsumedFoodQuantity>( new FieldComparisonFirstMethod(),
                lowestConsumedFoodQuantity, biggestConsumedFoodQuantity, equalConsumedFoodQuantity);
        c.setNamesOfFieldsThatYouWantToCompare("timeOfConsumption");

        String expected = "cfq.timeOfConsumption<'2021-04-20 10:51:12' AND " +
                "cfq.timeOfConsumption>'2021-03-21 11:34:56'";

        String actual = c.clause("cfq", "AND").orElse("NOT WORKING");
        assertEquals(expected, actual);
    }

    @Test
    public void testFoodLessThan50point52kcalMoreThan5point34grams(){
        var lowerFood = new Food("lowerFood", new BigDecimal(50.52).setScale(2, BigDecimal.ROUND_HALF_UP),
                null);
        var biggerFood = new Food("biggerFood", null, new BigDecimal(5.34).setScale(2, BigDecimal.ROUND_HALF_UP));
        var equalFood = new Food("tomatoes", null, null);
        String classAlias = "food";

        var c = new ComparingObjectsToMakeJPQLClauses<Food>(new FieldComparisonFirstMethod(),
                lowerFood, biggerFood, equalFood);

        String expected = classAlias + ".foodName='tomatoes' AND " +
                classAlias + ".kcalPer100g>50.52 AND " +
                classAlias + ".proteinsPer100g<5.34";

        String actual = c.clause(classAlias, "AND").orElse("NOT WORKING");
        assertEquals(expected, actual);
    }

    @Test
    public void testDailyWeightOn2019_4_20andKilogramsAbove80point180point3(){
        var lowestDay = new DailyMass(80.1f, null, null);
        var biggestDay = new DailyMass(80.3f, null, null);
        var equalDay = new DailyMass(null, LocalDate.of(2019,4,20), null);

        var c = new ComparingObjectsToMakeJPQLClauses<DailyMass>( new FieldComparisonFirstMethod(),
                lowestDay, biggestDay, equalDay);

        String expected = "dm.massInKilograms<80.3 AND dm.massInKilograms>80.1 AND "
                + "dm.date='2019-04-20'";
        String actual = c.clause("dm", "AND").orElse("NOT WORKING");
        assertEquals(expected, actual);
    }

    @Test
    public void testMakeAClausePersonWeightLossHeightInCmEquals187(){
        var lowestPerson = new PersonWeightLoss(null, null);
        var biggestPerson = new PersonWeightLoss(null, null);
        var equalPerson = new PersonWeightLoss(null, (short)187);

        var c = new ComparingObjectsToMakeJPQLClauses<PersonWeightLoss>(new FieldComparisonFirstMethod(),
                lowestPerson, biggestPerson, equalPerson);

        String expected = "p.heightInCentimeters=187";
        String actual = c.clause("p", "AND").orElse("NOT WORKING");
        assertEquals(expected, actual);
    }

    @Test
    public void testThatWhenAllParametersAreNullEmptyOptionalIsReturned(){
        var person = new PersonWeightLoss(null, null);
        var c = new ComparingObjectsToMakeJPQLClauses<PersonWeightLoss>(new FieldComparisonFirstMethod(),
                person, person, person);

        Optional<String> actualOptional = c.clause("p", "AND");
        assertTrue(actualOptional.isEmpty());
    }

    @Test
    public void testThatWhenSetFieldsToCompareAreNothingThenTheClauseIsAnEmptyOptional(){
        var lowestPerson = new PersonWeightLoss(null, null);
        var biggestPerson = new PersonWeightLoss(null, null);
        var equalPerson = new PersonWeightLoss(null, (short)187);

        var c = new ComparingObjectsToMakeJPQLClauses<PersonWeightLoss>( new FieldComparisonFirstMethod(),
                lowestPerson, biggestPerson, equalPerson);
        c.setNamesOfFieldsThatYouWantToCompare(); // No fields on which clause is created.

        Optional<String> actualOptional = c.clause("p", "AND");
        assertTrue(actualOptional.isEmpty());
    }

}
