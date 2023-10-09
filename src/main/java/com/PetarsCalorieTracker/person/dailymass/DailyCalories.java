package com.PetarsCalorieTracker.person.dailymass;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyCalories {

    private BigDecimal caloriesConsumed;
    private LocalDate date;

    public DailyCalories(){}

    public DailyCalories(BigDecimal caloriesConsumed, LocalDate date) {
        this.caloriesConsumed = caloriesConsumed;
        this.date = date;
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
