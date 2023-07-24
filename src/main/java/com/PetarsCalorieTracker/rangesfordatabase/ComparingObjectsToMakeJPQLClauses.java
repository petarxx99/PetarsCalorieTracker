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

public class ComparingObjectsToMakeJPQLClauses<T> implements QueryClauseMaker{

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
    }

    public Optional<String> clause(@NonNull String classAliasForQuery, @NonNull String andOr){
        String clause = constructClause(classAliasForQuery, andOr);

        if (clause.trim().equals("")){
            return Optional.empty();
        }
        return Optional.of(clause);
    }

    private String constructClause(String classAliasForQuery, String andOr){
        StringBuilder clauseToBuild = new StringBuilder();
        try{
            boolean noClausesHaveBeenAddedYet = true;

            for (Field field : fields) {
                field.setAccessible(true);
                var lowestValue = Optional.ofNullable(field.get(lowest));
                var biggestValue = Optional.ofNullable(field.get(biggest));
                var equalValue = Optional.ofNullable(field.get(equal));

                Optional<String> clauseForThisField = fieldComparisonForDatabase.clause(mapAnObjectToFormattedForDatabase(lowestValue), mapAnObjectToFormattedForDatabase(biggestValue), mapAnObjectToFormattedForDatabase(equalValue), Optional.of(field.getName()), classAliasForQuery, ">", "<", andOr);

                if (clauseForThisField.isPresent()){
                    insertANDIfThisIsntTheFirstClause(clauseToBuild, noClausesHaveBeenAddedYet, andOr);
                    clauseToBuild.append(clauseForThisField.get());
                    noClausesHaveBeenAddedYet = false;
                }
            }
        } catch(IllegalAccessException | NoNameForDatabaseClauseException exception){
            exception.printStackTrace();
        }

        return clauseToBuild.toString();
    }

    private void insertANDIfThisIsntTheFirstClause(StringBuilder clauseToBuild,
                                                     boolean noClausesHaveBeenAddedYet,
                                                     String andOr){
        if (!noClausesHaveBeenAddedYet){
            clauseToBuild.append(" ");
            clauseToBuild.append(andOr);
            clauseToBuild.append(" ");
        }
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
