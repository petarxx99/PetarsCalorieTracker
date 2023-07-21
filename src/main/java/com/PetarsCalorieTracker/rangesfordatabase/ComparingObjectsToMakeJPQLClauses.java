package com.PetarsCalorieTracker.rangesfordatabase;


import com.PetarsCalorieTracker.rangesfordatabase.exceptions.NoNameForDatabaseClauseException;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.FormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.LocalDateFormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.LocalDateTimeFormattedForDatabase;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.ObjectFormattedForDatabase;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ComparingObjectsToMakeJPQLClauses<T> {

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

    private List<Field> fields = null;

    public ComparingObjectsToMakeJPQLClauses(@NonNull FieldComparisonForDatabase fieldComparisonForDatabase, @NonNull T lowest, @NonNull T biggest, @NonNull T equal, @Nullable String ... fieldsToDisregard) {
        this.fieldComparisonForDatabase = fieldComparisonForDatabase;
        this.lowest = lowest;
        this.biggest = biggest;
        this.equal = equal;

        Field[] fieldsOfTheClass = lowest.getClass().getDeclaredFields();
        initFields(fieldsOfTheClass, fieldsToDisregard);
        System.out.println("number of fields: " + fields.size());
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


    public void setNamesOfFieldsThatYouWantToCompare(@NonNull String ... fieldsToIncludeForComparison){
        Field[] fieldsOfTheClass = lowest.getClass().getDeclaredFields();
        var newFields = new ArrayList<Field>();

        for(String toInclude : fieldsToIncludeForComparison){
            for(Field field : fieldsOfTheClass){
                if (trimmedToLowerCaseStringsMatch(toInclude, field.getName())){
                    newFields.add(field);
                }
            }
        }

        fields = newFields;
        assert fields.size() == fieldsToIncludeForComparison.length;
    }

    private void initFields(Field[] fieldsOfTheClass, String[] fieldsToDiscard){
        fields = new ArrayList<>();

        if (fieldsToDiscard == null){
            fields.addAll(Arrays.asList(fieldsOfTheClass));
        } else {
            addFieldsThatArentDiscarded(fieldsOfTheClass, fieldsToDiscard);
        }
    }

    private void addFieldsThatArentDiscarded(Field[] fieldsOfTheClass, String[] fieldsToDisregard){
        for(Field field : fieldsOfTheClass) {
            boolean noMatchHasBeenFound = true;
            for (String toDisregard : fieldsToDisregard) {
                if (trimmedToLowerCaseStringsMatch(toDisregard, field.getName())) {
                    noMatchHasBeenFound = false;
                    break;
                }
            }
            if (noMatchHasBeenFound) {
                fields.add(field);
            }
        }

        assert fieldsOfTheClass.length - fields.size() == fieldsToDisregard.length;
    }

    private boolean trimmedToLowerCaseStringsMatch(@NonNull String s1, @NonNull String s2){
        return s1.toLowerCase().trim().equals(s2.toLowerCase().trim());
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
