package com.PetarsCalorieTracker.person.dailymass;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyMassAndCalories {

    private Float massInKilograms;
    private BigDecimal caloriesConsumed;
    private LocalDate date;

    public DailyMassAndCalories(){}

    public DailyMassAndCalories(Float massInKilograms, BigDecimal caloriesConsumed, LocalDate date) {
        this.massInKilograms = massInKilograms;
        this.caloriesConsumed = caloriesConsumed;
        this.date = date;
    }

    public Float getMassInKilograms() {
        return massInKilograms;
    }

    public void setMassInKilograms(Float massInKilograms) {
        this.massInKilograms = massInKilograms;
    }

    public BigDecimal getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(BigDecimal caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
