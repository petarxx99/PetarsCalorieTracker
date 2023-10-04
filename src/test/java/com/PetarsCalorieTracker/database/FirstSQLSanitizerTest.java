package com.PetarsCalorieTracker.database;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FirstSQLSanitizerTest {

    @Test
    public void testThatNormalStringShouldPass(){
        var sqlToSanitize = "This is some string. There is nothing to sanitize here.";
        var sqlSanitizer = new FirstSQLSanitizer();

        String sanitizedSQL = sqlSanitizer.sanitize(sqlToSanitize);
        assertEquals(sqlToSanitize, sanitizedSQL);
    }

    @Test
    public void testIAmQuotePetarQuoteTheGuyWhoProgrammedThisApp(){
        var sqlToSanitize = "I am 'Petar', the guy who programmed this app";
        var sqlSanitizer = new FirstSQLSanitizer();

        String sanitizedSQL = sqlSanitizer.sanitize(sqlToSanitize);
        String expected = "I am \\'Petar\\', the guy who programmed this app";

        assertEquals(sqlToSanitize.length() + 2,expected.length());
        assertEquals(expected, sanitizedSQL);
    }

    @Test
    public void testIAmDoubleQuotePetarDoubleQuoteTheGuyWhoProgrammedTheApp(){
        var sqlToSanitize = "I am \"Petar\", the guy who programmed this app.";
        var sqlSanitizer = new FirstSQLSanitizer();

        String sanitizedSQL = sqlSanitizer.sanitize(sqlToSanitize);
        String expected = "I am \\\"Petar\\\", the guy who programmed this app.";

        assertEquals(sqlToSanitize.length() + 2, expected.length());
        assertEquals(expected, sanitizedSQL);
    }

    @Test
    public void testThatSlashesGetPaired(){
        var sqlToSanitize = "This is \\ it should be paired. This is \\\\ the number of slashes should double.";
        var sqlSanitizer = new FirstSQLSanitizer();

        String sanitizedSQL = sqlSanitizer.sanitize(sqlToSanitize);
        String expected = "This is \\\\ it should be paired. This is \\\\\\\\ the number of slashes should double.";

        assertEquals(sqlToSanitize.length() + 3, expected.length());
        assertEquals(expected, sanitizedSQL);
    }

    @Test
    public void testThatSlashQuoteBecomesTripleSlashQuote(){
        var sqlToSanitize = "I am \\'Petar\\', the guy who wrote this app.";
        var sqlSanitizer = new FirstSQLSanitizer();

        String sanitizedSQL = sqlSanitizer.sanitize(sqlToSanitize);
        String expected = "I am \\\\\\'Petar\\\\\\', the guy who wrote this app.";

        assertEquals(sqlToSanitize.length() + 4, expected.length());
        assertEquals(expected, sanitizedSQL);
    }

    @Test
    public void testThatSlashDoubleQuoteBecomesTripleSlashDoubleQuote(){
        var sqlToSanitize = "I am \\\"Petar\\\", the guy who wrote this app.";
        var sqlSanitizer = new FirstSQLSanitizer();

        String sanitizedSQL = sqlSanitizer.sanitize(sqlToSanitize);
        String expected = "I am \\\\\\\"Petar\\\\\\\", the guy who wrote this app.";

        assertEquals(sqlToSanitize.length() + 4, expected.length());
        assertEquals(expected, sanitizedSQL);
    }

}
