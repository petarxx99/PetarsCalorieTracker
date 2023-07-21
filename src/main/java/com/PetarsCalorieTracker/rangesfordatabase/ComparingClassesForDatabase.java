package com.PetarsCalorieTracker.rangesfordatabase;


import com.PetarsCalorieTracker.rangesfordatabase.exceptions.NoNameForDatabaseClauseException;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.FormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.LocalDateFormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.LocalDateTimeFormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.ObjectFormattedForDatabase;
import org.springframework.cglib.core.Local;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class ComparingClassesForDatabase<T> {

    @NonNull
    private final FieldComparisonForDatabase fieldComparisonForDatabase;

    @NonNull
    private final T lowest;

    @NonNull
    private final T biggest;

    @NonNull
    private final T equal;

    private Optional<String> clause = Optional.empty();
    private boolean itWasCheckedAndThereIsNoClause = false;

    public ComparingClassesForDatabase(@NonNull FieldComparisonForDatabase fieldComparisonForDatabase, @NonNull T lowest, @NonNull T biggest, @NonNull T equal) {
        this.fieldComparisonForDatabase = fieldComparisonForDatabase;
        this.lowest = lowest;
        this.biggest = biggest;
        this.equal = equal;
    }

    public Optional<String> clause(){
        if (itWasCheckedAndThereIsNoClause){
            return Optional.empty();
        }

        if (clause.isPresent()){
            return clause;
        }

        makeTheClause();
        return clause;
    }

    private void makeTheClause(){
        try{
            var clauseToBuild = new StringBuilder();
            boolean noClausesHaveBeenAddedYet = true;
            Field[] fields = lowest.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                var lowestValue = Optional.of(field.get(lowest));
                var biggestValue = Optional.of(field.get(biggest));
                var equalValue = Optional.of(field.get(equal));

                Optional<String> clauseForThisField = fieldComparisonForDatabase.clause(mapAnObjectToFormattedForDatabase(lowestValue), mapAnObjectToFormattedForDatabase(biggestValue), mapAnObjectToFormattedForDatabase(equalValue), Optional.of(field.getName()), lowest.getClass().getSimpleName(), ">", "<", "AND");

                if (clauseForThisField.isPresent()){
                    if (!noClausesHaveBeenAddedYet){
                        clauseToBuild.append(" AND ");
                    }

                    clauseToBuild.append(clauseForThisField.get());
                    noClausesHaveBeenAddedYet = false;
                }
            } // end of for loop

            if (noClausesHaveBeenAddedYet){
                itWasCheckedAndThereIsNoClause = true;
            } else {
                clause = Optional.of(clauseToBuild.toString());
            }
        } catch(IllegalAccessException | NoNameForDatabaseClauseException exception){
            exception.printStackTrace();
        }
    }

    private Optional<FormattedForDatabase> mapAnObjectToFormattedForDatabase(Optional objectOptional){
        if (objectOptional.isEmpty()){
            return Optional.empty();
        }

        Object object = objectOptional.get();
        if (object instanceof LocalDateTime){
            return Optional.of(new LocalDateTimeFormattedForDatabase((LocalDateTime) object));
        }
        if (object instanceof LocalDate){
            return Optional.of(new LocalDateFormattedForDatabase((LocalDate) object));
        }
        return Optional.of(new ObjectFormattedForDatabase(object));
    }

}
