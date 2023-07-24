package com.PetarsCalorieTracker.rangesfordatabase;

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface QueryClauseMaker {

    public Optional<String> clause(@NonNull String classAlias, @NonNull String andOr);

}
