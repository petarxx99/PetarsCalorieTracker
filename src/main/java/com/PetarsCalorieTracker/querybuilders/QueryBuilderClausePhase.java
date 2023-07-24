package com.PetarsCalorieTracker.querybuilders;

public interface QueryBuilderClausePhase {
    public String addClause(String clause, String firstWordOfTheClauseProbablyWhere);
    public String buildQuery();
}
