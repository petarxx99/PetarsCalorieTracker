package com.PetarsCalorieTracker.rangesfordatabase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.management.Query;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestQueryClauseMaker {

    @Mock
    private QueryClauseMaker queryClauseMakerMock;

    @BeforeEach
    public void setup(){
        queryClauseMakerMock = mock(QueryClauseMaker.class);
    }

    private static final String SOME_CLASS_NAME = "SomeClass";
    private static final String AND = "AND";
    private static final String SOME_CLAUSE = "SOME CLAUSE";

    @Test
    public void testThatIfEmptyOptionalIsPassedToClauseTheReturnValueIsAlsoEmptyOptional(){
        when(queryClauseMakerMock.clause(SOME_CLASS_NAME, AND)).thenReturn(Optional.of(SOME_CLAUSE));
        Optional<String> clause = QueryClauseMaker.clause(
                Optional.of(queryClauseMakerMock),
                SOME_CLASS_NAME,
                AND);
        assert clause.isPresent();

        String actual = clause.get();
        String expected = SOME_CLAUSE;
        assertEquals(expected, actual);
    }

    @Test
    public void testThatIfNonEmptyOptionalIsPassedToClauseTheReturnedValueIsTheSameAsIfClauseWereCalledOnAnObjectThatImplementsThisInterface(){
        Optional<String> actual = QueryClauseMaker.clause(
                Optional.empty(),
                SOME_CLASS_NAME,
                AND);
        assertFalse(actual.isPresent());
    }
}
