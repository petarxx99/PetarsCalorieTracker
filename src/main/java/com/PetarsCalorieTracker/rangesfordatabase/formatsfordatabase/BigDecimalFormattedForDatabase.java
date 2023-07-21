package com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public class BigDecimalFormattedForDatabase implements FormattedForDatabase{
    @NonNull
    private final BigDecimal value;

    public BigDecimalFormattedForDatabase(BigDecimal value){
        this.value = value;
    }

    @Override
    public String getStringFormattedForDatabase(){
        return value.toString();
    }
}
