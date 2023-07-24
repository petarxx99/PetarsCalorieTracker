package com.PetarsCalorieTracker.rangesfordatabase.clausecombiners;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClauseCombinerFirstImpl implements ClausesCombiner{


    @Override
    @SafeVarargs
    public final StringBuilder addClausesTogether(Optional<String>... clauses){
        StringBuilder result = new StringBuilder();
        if (clauses.length == 0){
            return result;
        }

        clauses[0].ifPresent(result::append);

        for(int i=1; i<clauses.length; i++){
            addClauseAndReturnTrueIfClauseIsAdded(result, clauses[i]);
        }

        return result;
    }

    @Override
    public boolean addClauseAndReturnTrueIfClauseIsAdded(StringBuilder allClauses,
                                                          Optional<String> newClause){
        if (newClause.isPresent()){
            if (allClauses.length() > 0){
                allClauses.append(" AND ");
            }
            allClauses.append(newClause.get());
            return true;
        }
        return false;
    }
}
