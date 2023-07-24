package com.PetarsCalorieTracker.rangesfordatabase.clausecombiners;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestClauseCombinersFirstImpl {

    @Test
    public void testThatClauseIsAppended(){
        var clauseCombiner = new ClauseCombinerFirstImpl();
        var sb = new StringBuilder("someText");
        boolean added = clauseCombiner.addClauseAndReturnTrueIfClauseIsAdded(sb, Optional.ofNullable("text"));
        assertTrue(added);
    }

    @Test
    public void testThatClauseIsAppendedEvenThoughClausesDontExistYet(){
        var clauseCombiner = new ClauseCombinerFirstImpl();
        var sb = new StringBuilder("");
        boolean added = clauseCombiner.addClauseAndReturnTrueIfClauseIsAdded(sb, Optional.ofNullable("text"));
        assertTrue(added);
    }

    @Test
    public void thatThatClauseIsNotAppended(){
        var clauseCombiner = new ClauseCombinerFirstImpl();
        var sb = new StringBuilder("sda");
        boolean added = clauseCombiner.addClauseAndReturnTrueIfClauseIsAdded(sb, Optional.empty());
        assertFalse(added);
    }

    @Test
    public void testThatThreeOptionalEmptiesGiveANothingClause(){
        var clauseCombiner = new ClauseCombinerFirstImpl();
        String actual = clauseCombiner.addClausesTogether(Optional.empty(), Optional.empty(), Optional.empty()).toString();
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    public void testThatThreeStringsThatOnlyHaveSpacesGiveANothingClause(){
        var clauseCombiner = new ClauseCombinerFirstImpl();
        String actual = clauseCombiner.addClausesTogether(Optional.of("  "), Optional.of("  "), Optional.of("  ")).toString();
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    public void testThatNameIsJohnAgeIs20_OptionalEmptyAndCountryIsAustraliaClauseCombine(){
        var clauseCombiner = new ClauseCombinerFirstImpl();
        String actual = clauseCombiner.addClausesTogether(
                Optional.of("name='John'"),
                Optional.of("age=20"),
                Optional.empty(),
                Optional.of("country='Australia'")).toString();
        String expected = "name='John' AND age=20 AND country='Australia'";
        assertEquals(expected, actual);
    }

    @Test
    public void testThatOptionalEmptyCountryIsCanadaNameIsJohnAgeIs20ColorIsBlueOptionalEmptyInstrumentIsPianoClauseCombine(){
        var clauseCombiner = new ClauseCombinerFirstImpl();
        String actual = clauseCombiner.addClausesTogether(
                Optional.empty(),
                Optional.of("country='Canada'"),
                Optional.of("name='John'"),
                Optional.of("age=20"),
                Optional.of("color='blue'"),
                Optional.empty(),
                Optional.of("instrument='piano'")).toString();
        String expected = "country='Canada' AND name='John' AND age=20 AND " +
                "color='blue' AND instrument='piano'";
        assertEquals(expected, actual);
    }
}
