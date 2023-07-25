package com.PetarsCalorieTracker.querybuilders;

public interface QueryBuilderClausePhase {
    public String addClause(String firstWordOfTheClauseProbablyWhere, String clause);
    public String buildQuery();
}
