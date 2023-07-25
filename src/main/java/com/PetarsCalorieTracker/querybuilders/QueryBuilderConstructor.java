package com.PetarsCalorieTracker.querybuilders;

import org.springframework.stereotype.Service;

@Service
public class QueryBuilderConstructor implements QueryBuilder, QueryFromBuilder, QueryBuilderClausePhase{

    private StringBuilder query = null;

    private QueryBuilderConstructor(){}

    @Override
    public QueryFromBuilder addSelect(String select) {
        query = new StringBuilder(select);
        query.append(" ");
        return this;
    }

    @Override
    public QueryBuilderClausePhase addFrom(String from){
        query.append(from);
        query.append(" ");
        return this;
    }

    @Override
    public String addClause(String firstWordOfTheClauseProbablyWhere, String clause){
        if(clause == null) return query.toString();

        if (clause.trim().equals("")) return query.toString();

        query.append(firstWordOfTheClauseProbablyWhere);
        query.append(" ");
        query.append(clause);
        return query.toString();
    }

    @Override
    public String buildQuery() {
        return query.toString();
    }

    public static QueryBuilder getQueryBuilder(){
        return new QueryBuilderConstructor();
    }
}
