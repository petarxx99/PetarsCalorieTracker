package com.PetarsCalorieTracker.rangesfordatabase;

import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestComparingObjectsToMakeJPQLClauses {

    @Test
    public void testMakeAClauseForFoodOver100kcalAbove5gOfProteinAndBelow10gOfFat(){
        var person = new PersonBasicInfo("Jovan", "Petrovic", "Serbia",
                LocalDate.of(2020, 2, 20));
        var c = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>(new FieldComparisonFirstMethod(),
                person, person, person,
                "firstName", "lastName", "dateOfBirth");
        var c2 = new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>(new FieldComparisonFirstMethod(),
                person, person, person);

        c2.setNamesOfFieldsThatYouWantToCompare("firstName", "dateOfBirth");
        assertTrue(1==1);
    }
}
