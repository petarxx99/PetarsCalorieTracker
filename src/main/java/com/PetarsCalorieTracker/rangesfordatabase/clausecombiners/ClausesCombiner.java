package com.PetarsCalorieTracker.rangesfordatabase.clausecombiners;



import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ClausesCombiner {

    public StringBuilder addClausesTogether(Optional<String> ... clauses);
    public boolean addClauseAndReturnTrueIfClauseIsAdded(@NonNull StringBuilder allClauses,
                                                          @NonNull Optional<String> newClause);
}
