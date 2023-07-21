package com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFormattedForDatabase implements FormattedForDatabase{
    @NonNull
    private final LocalDateTime value;

    public LocalDateTimeFormattedForDatabase(LocalDateTime value){
        this.value = value;
    }

    @Override
    public String getStringFormattedForDatabase(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return "'" + value.format(dtf) + "'";
    }
}
