package com.PetarsCalorieTracker.rangesfordatabase.clausecombiners;



import java.util.Optional;

public interface ClausesCombiner {

    public StringBuilder addClausesTogether(Optional<String> ... clauses);
}
