package com.PetarsCalorieTracker.rangesfordatabase;


import com.PetarsCalorieTracker.rangesfordatabase.exceptions.NoNameForDatabaseClauseException;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.BigDecimalFormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.LocalDateFormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.LocalDateTimeFormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.ObjectFormattedForDatabase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestFieldComparisonFirstMethod {



    @Test
    public void testLessThanOrEqualTo1000000AsBigDecimal(){
        final int ONE_MILLION = 1000000;
        var lessThanOrEqual = new BigDecimalFormattedForDatabase(new BigDecimal(ONE_MILLION));
        String tableName = "some_table";
        String columnName = "some_number";
        try{
            var comparison = new FieldComparisonFirstMethod();
            String clause = comparison.clause(
                    Optional.empty(),
                    Optional.of(lessThanOrEqual),
                    Optional.empty(),
                    Optional.of(columnName),
                    tableName,
                    ">=",
                    "<=",
                    "AND"
            ).orElse("NOT WORKING");

            String expected = tableName + "." + columnName + "<=" + ONE_MILLION;

            assertTrue(expected.equals(clause.substring(0, expected.length())));
        } catch(NoNameForDatabaseClauseException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testThatDateIsBefore2020_10_26_but_after_2019_8_30(){
        LocalDate before = LocalDate.of(2020, 10, 26);
        LocalDate after = LocalDate.of(2019, 8, 30);
        String tableName = "some_table";
        String columnName = "some_date";
        try {
            var comparison = new FieldComparisonFirstMethod();
            String clause = comparison.clause(
                    Optional.of(new LocalDateFormattedForDatabase(after)),
                    Optional.of(new LocalDateFormattedForDatabase(before)),
                    Optional.empty(),
                    Optional.of(columnName),
                    tableName,
                    ">",
                    "<",
                    "AND"
            ).orElse("NOT WORKING");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String expected = tableName + "." + columnName + "<'" + before.format(dtf) + "'" +
                    " AND " + tableName + "." + columnName + ">'" + after.format(dtf) + "'";

            assertTrue(expected.equals(clause));
        }  catch(NoNameForDatabaseClauseException e){
        e.printStackTrace();
        }
    }

    @Test
    public void testThatLocalDateTimeIsAfter2020_3_20_12_31_46(){
        LocalDateTime after = LocalDateTime.of(2020, 3, 20, 12, 31, 46);
        String tableName = "some_table";
        String columnName = "some_date";
        try {
            var comparison = new FieldComparisonFirstMethod();
            String clause = comparison.clause(
                    Optional.of(new LocalDateTimeFormattedForDatabase(after)),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(columnName),
                    tableName,
                    ">",
                    "<",
                    "AND"
            ).orElse("NOT WORKING");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String expected = tableName + "." + columnName + ">'" + after.format(dtf) + "'";

            assertTrue(expected.equals(clause));
        }  catch(NoNameForDatabaseClauseException e){
            e.printStackTrace();
        }
    }


    @Test
    public void testMakeAClauseForStringBeingEqualToJohn_Smith(){
        String equal = "John Smith";
        String tableName = "some_table";
        String columnName = "some_date";
        try {
            var comparison = new FieldComparisonFirstMethod();
            String clause = comparison.clause(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(new ObjectFormattedForDatabase(equal)),
                    Optional.of(columnName),
                    tableName,
                    ">",
                    "<",
                    "AND"
            ).orElse("NOT WORKING");

            String expected = tableName + "." + columnName + "='" + equal + "'";;

            assertTrue(expected.equals(clause));
        }  catch(NoNameForDatabaseClauseException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testObjectReturnsEmptyOptionalWhenNoBoundsPresented(){
        try {
            var comparison = new FieldComparisonFirstMethod();
            Optional<String> clause = comparison.clause(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of("something"),
                    "some_table",
                    ">",
                    "<",
                    "AND"
            );


            assertTrue(clause.isEmpty());
        }  catch(NoNameForDatabaseClauseException e){
            e.printStackTrace();
        }
    }


    @Test
    public void testObjectReturnsEmptyOptionalWhenNoBoundsNorColumnsPresented(){
        try {
            var comparison = new FieldComparisonFirstMethod();
            Optional<String> clause = comparison.clause(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    "some_table",
                    ">",
                    "<",
                    "AND"
            );


            assertTrue(clause.isEmpty());
        }  catch(NoNameForDatabaseClauseException e){
            e.printStackTrace();
        }
    }

}
