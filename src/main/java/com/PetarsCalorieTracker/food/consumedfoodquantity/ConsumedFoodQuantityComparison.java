package com.PetarsCalorieTracker.food.consumedfoodquantity;

import com.PetarsCalorieTracker.food.FoodComparassment;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLossComparassment;
import com.PetarsCalorieTracker.rangesfordatabase.ComparisonForDatabase;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

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
        boolean noClausesYet = true;
        for (int i=0; i<comparisons.length; i++){
            ComparisonForDatabase currentComparison = comparisons[i];
            if (currentComparison.hasAClause()){
                if (!noClausesYet){
                    clause.append(" ");
                    clause.append(and);
                }
                clause.append(currentComparison.clause(
                        tableClassName,
                        tableColumnResolverItIsProbablyADot,
                        lessThan,
                        biggerThan,
                        and).get());
                noClausesYet = false;
            }
        }

        return clause.toString();
    }
}
