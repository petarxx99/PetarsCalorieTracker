package com.PetarsCalorieTracker.rangesfordatabase;

import com.PetarsCalorieTracker.rangesfordatabase.exceptions.NoNameForDatabaseClauseException;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.FormattedForDatabase;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface FieldComparisonForDatabase {

    public Optional<String> clause(
            @NonNull Optional<FormattedForDatabase> lowest,
            @NonNull Optional<FormattedForDatabase> biggest,
            @NonNull Optional<FormattedForDatabase> equal,
            @NonNull Optional<String> name,
            @NonNull String classTableName,
            @NonNull String biggerThanSign,
            @NonNull String lessThanSign,
            @NonNull String and) throws NoNameForDatabaseClauseException;

}
