package com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase;

import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormattedForDatabase implements FormattedForDatabase{

    @NonNull
    private final LocalDate value;

    public LocalDateFormattedForDatabase(LocalDate value){
        this.value = value;
    }

    @Override
    public String getStringFormattedForDatabase(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "'" + value.format(dtf) + "'";
    }
}
