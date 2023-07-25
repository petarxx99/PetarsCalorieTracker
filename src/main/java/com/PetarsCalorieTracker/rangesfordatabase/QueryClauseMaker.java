package com.PetarsCalorieTracker.rangesfordatabase;

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface QueryClauseMaker {

    public Optional<String> clause(@NonNull String classAlias, @NonNull String andOr);

    public static Optional<String> clause(@NonNull Optional<QueryClauseMaker> clauseMakerOptional,
                                          @NonNull String classAlias, @NonNull String andOr){
        if (clauseMakerOptional.isPresent()){
            return clauseMakerOptional.get().clause(classAlias, andOr);
        }
        return Optional.empty();
    }
}
