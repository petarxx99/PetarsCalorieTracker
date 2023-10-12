package com.PetarsCalorieTracker.querybuilders;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestQueryBuilderConstructor {

    @Test
    public void testSelectAllFromPersonLeftJoinAuthenticationWherePersonFirstNameLikeJohAndLastNameLikeSmi(){
        QueryBuilder builder = QueryBuilderConstructor.getQueryBuilder();

        String actual = builder.addSelect("SELECT *")
                .addFrom("FROM Person p LEFT JOIN Authentication")
                .addClause("WHERE", "p.firstName LIKE 'Joh' AND p.lastName LIKE 'Smi'");
        String expected = "SELECT * FROM Person p LEFT JOIN Authentication WHERE p.firstName LIKE 'Joh' AND p.lastName LIKE 'Smi'";
        assertEquals(expected, actual);
    }
}
