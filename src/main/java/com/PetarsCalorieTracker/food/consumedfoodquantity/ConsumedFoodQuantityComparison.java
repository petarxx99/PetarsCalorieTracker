package com.PetarsCalorieTracker.food.consumedfoodquantity;

import com.PetarsCalorieTracker.rangesfordatabase.ComparisonForDatabase;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumedFoodQuantityComparison {


    @NonNull
    private final ComparisonForDatabase idComparison;

    @NonNull
    private final ComparisonForDatabase timeOfConsumptionComparison;

    @NonNull
    private final ComparisonForDatabase quantityInGramsComparison;


    public ConsumedFoodQuantityComparison(@NonNull ComparisonForDatabase idComparison,
                                          @NonNull ComparisonForDatabase timeOfConsumptionComparison,
                                          @NonNull ComparisonForDatabase quantityInGramsComparison) {
        this.idComparison = idComparison;
        this.timeOfConsumptionComparison = timeOfConsumptionComparison;
        this.quantityInGramsComparison = quantityInGramsComparison;
    }

    public String makeQueryClause(
            @NonNull String tableClassName,
            @NonNull String tableColumnResolverItIsProbablyADot,
            @NonNull String lessThan,
            @NonNull String biggerThan,
            @NonNull String and){
        ComparisonForDatabase[] comparisons = {idComparison, timeOfConsumptionComparison, quantityInGramsComparison};

        var clause = new StringBuilder();
        AtomicBoolean noClausesYet = new AtomicBoolean(true);
        for (int i=0; i<comparisons.length; i++){
            ComparisonForDatabase currentComparison = comparisons[i];
            Optional<String> currentClauseOptional = currentComparison.clause(
                    tableClassName,
                    tableColumnResolverItIsProbablyADot,
                    lessThan,
                    biggerThan,
                    and);
            currentClauseOptional.ifPresent(currentClause -> {
                    if (!noClausesYet.get()){
                        clause.append(" ");
                        clause.append(and);
                    }
                    clause.append(currentClause);
                    noClausesYet.set(false);
            });
        }

        return clause.toString();
    }
}
