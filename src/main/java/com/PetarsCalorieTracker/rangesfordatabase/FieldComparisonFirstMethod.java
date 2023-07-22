package com.PetarsCalorieTracker.rangesfordatabase;

import com.PetarsCalorieTracker.rangesfordatabase.exceptions.NoNameForDatabaseClauseException;
import com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase.FormattedForDatabase;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;



import java.util.Optional;

@Service
public class FieldComparisonFirstMethod implements FieldComparisonForDatabase{

    private static final String tableColumnResolverItIsProbablyADot = ".";



    public FieldComparisonFirstMethod() {}


    @NonNull
    public Optional<String> clause(
            @NonNull Optional<FormattedForDatabase> lowest,
            @NonNull Optional<FormattedForDatabase> biggest,
            @NonNull Optional<FormattedForDatabase> equal,
            @NonNull Optional<String> name,
            @NonNull String classTableName,
            @NonNull String biggerThanSign,
            @NonNull String lessThanSign,
            @NonNull String andOr) throws NoNameForDatabaseClauseException {
        if (name.isEmpty()
                        && (lowest.isPresent() || biggest.isPresent() || equal.isPresent())
        ){throw new NoNameForDatabaseClauseException("no name for a database clause.");}

        if (equal.isPresent()){
            return Optional.of(formSingleClause(classTableName, name.get(), "=", equal.get()));
        }

        if (biggest.isEmpty()){
            return lowest.map(lowestValue -> formSingleClause(
                    classTableName,
                    name.get(),
                    biggerThanSign,
                    lowestValue));
        }

        var result = new StringBuilder();
        result.append(formSingleClause(classTableName, name.get(), lessThanSign, biggest.get()));

        if (lowest.isPresent()){
            result.append(" ");
            result.append(andOr);
            result.append(" ");

            result.append(formSingleClause(classTableName, name.get(), biggerThanSign, lowest.get()));
        }

        return Optional.of(result.toString());
    }

    private String formSingleClause(
            @NonNull String classTableName,
            @NonNull String name,
            @NonNull String comparisonSign,
            @NonNull FormattedForDatabase value){
        return classTableName + tableColumnResolverItIsProbablyADot + name + comparisonSign + value.getStringFormattedForDatabase();
    }

    private boolean escapeCharExistsAndItIsNotInDoubles(char escapeChar, String name){
        if (name.isEmpty()){return false;}

        char[] characters = name.toCharArray();
        int numOfChars = characters.length;

        boolean previousCharWasAnOddEscapeChar = false;

        for(int i=0; i<numOfChars; i++){
            Character currentChar = characters[i];
            if (previousCharWasAnOddEscapeChar && !currentChar.equals(escapeChar)){
                return true;
            }

            previousCharWasAnOddEscapeChar = !previousCharWasAnOddEscapeChar &&
                    currentChar.equals(escapeChar);
        }

        return false;
    }

}
