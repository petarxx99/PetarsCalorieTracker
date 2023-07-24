package com.PetarsCalorieTracker.querybuilders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestQueryBuilderConstructor {

    @Test
    public void testSelectAllFromPersonLeftJoinAuthenticationWherePersonFirstNameLikeJohAndLastNameLikeSmi(){
        QueryBuilder builder = QueryBuilderConstructor.getQueryBuilder();

        String actual = builder.addSelect("SELECT *")
                .addFrom("FROM Person p LEFT JOIN Authentication")
                .addClause("p.firstName LIKE 'Joh' AND p.lastName LIKE 'Smi'", "WHERE");
        String expected = "SELECT * FROM Person p LEFT JOIN Authentication WHERE p.firstName LIKE 'Joh' AND p.lastName LIKE 'Smi'";
        assertEquals(expected, actual);
    }
}
