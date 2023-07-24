package com.PetarsCalorieTracker.querybuilders;

public class QueryBuildersConstructors implements QueryBuilder, QueryFromBuilder, QueryBuilderClausePhase{

    StringBuilder query = null;

    public QueryBuildersConstructors(){}

    @Override
    public QueryFromBuilder addSelect(String select) {
        query = new StringBuilder(select);
        query.append(" ");
        return this;
    }

    @Override
    public QueryBuilderClausePhase addFrom(String from){
        query.append(" ");
        query.append(from);
        query.append(" ");
        return this;
    }

    @Override
    public String addClause(String clause, String firstWordOfTheClauseProbablyWhere){
        if(clause == null) return query.toString();

        if (clause.trim().equals("")) return query.toString();

        query.append(" ");
        query.append(firstWordOfTheClauseProbablyWhere);
        query.append(" ");
        query.append(clause);
        return query.toString();
    }

    @Override
    public String buildQuery() {
        return query.toString();
    }
}
