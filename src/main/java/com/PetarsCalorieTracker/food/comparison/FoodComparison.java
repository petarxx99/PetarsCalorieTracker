package com.PetarsCalorieTracker.food.comparison;

import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.rangesfordatabase.FieldComparisonFirstMethod;
import com.PetarsCalorieTracker.rangesfordatabase.exceptions.NoNameForDatabaseClauseException;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.FormattedForDatabase;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class FoodComparison {
    @NonNull
    Food equal;

    @NonNull
    Food smallest;

    @NonNull
    Food biggest;

    private static final String tableName = "food";

    @NonNull
    private FieldComparisonFirstMethod fieldComparisonFirstMethod;
    public FoodComparison(@NonNull Food equal, @NonNull Food smallest, @NonNull Food biggest,
                          @NonNull FieldComparisonFirstMethod fieldComparisonFirstMethod) {
        this.equal = equal;
        this.smallest = smallest;
        this.biggest = biggest;
        this.fieldComparisonFirstMethod = fieldComparisonFirstMethod;
    }

    public Optional<String> makeASingleClause(@NonNull String columnName,
                              @NonNull Optional<FormattedForDatabase> smallest,
                              @NonNull Optional<FormattedForDatabase> biggest,
                              @NonNull Optional<FormattedForDatabase> equal){
        try {
            return fieldComparisonFirstMethod.clause(smallest, biggest, equal,
                    Optional.of(columnName), tableName, ">", "<", "AND"
            );
        } catch(NoNameForDatabaseClauseException exception){
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<String> makeAClause(){
        var clause = new StringBuilder();

        return Optional.empty();
    }
}
