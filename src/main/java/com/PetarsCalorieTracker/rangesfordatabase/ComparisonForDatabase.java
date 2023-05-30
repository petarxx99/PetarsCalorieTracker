package com.PetarsCalorieTracker.rangesfordatabase;

import org.springframework.lang.NonNull;

import java.util.Optional;

public class ComparisonForDatabase {
    @NonNull
    private Optional<Object> lowest;

    @NonNull
    private Optional<Object> biggest;


    @NonNull
    private Optional<String> name;

    public boolean hasAClause(){
        return lowest.isPresent() || biggest.isPresent();
    }


    public ComparisonForDatabase(){}
    public ComparisonForDatabase(@NonNull Optional<Object> lowest,
                                 @NonNull Optional<Object> biggest,
                                 @NonNull Optional<String> name)
            throws Exception
    {
        if (
                name.isEmpty()
                && (lowest.isPresent() || biggest.isPresent())
        ){
            throw new Exception("no name for a database clause.");
        }

        this.lowest = lowest;
        this.biggest = biggest;
        this.name = name;
    }


    @NonNull
    public Optional<String> clause(@NonNull String classTableName,
                         @NonNull String tableColumnResolverItIsProbablyADot,
                         @NonNull String biggerThan,
                         @NonNull String lessThan,
                         @NonNull String and){

        if (biggest.isEmpty()){
            if (lowest.isPresent()) {
                return Optional.of(formSingleClause(
                        classTableName,
                        tableColumnResolverItIsProbablyADot,
                        biggerThan,
                        lowest));
            } else {
                return Optional.empty();
            }
        }

        var result = new StringBuilder();
        result.append(formSingleClause(
                classTableName,
                tableColumnResolverItIsProbablyADot,
                lessThan,
                biggest));
        if (lowest.isPresent()){
            result.append(" ");
            result.append(and);
            result.append(" ");

            result.append(formSingleClause(
                    classTableName,
                    tableColumnResolverItIsProbablyADot,
                    biggerThan,
                    lowest));
        }

        return Optional.of(result.toString());
    }

    private String formSingleClause(
            @NonNull String classTableName,
            @NonNull String tableColumnResolverItIsProbablyADot,
            @NonNull String comparisonSign,
            @NonNull Object value){

        return classTableName + tableColumnResolverItIsProbablyADot + name + comparisonSign + value;
    }

    private boolean escapeCharExistsAndItIsNotInDoubles(char escapeChar){
        if (name.isEmpty()){return false;}

        char[] characters = name.get().toCharArray();
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
