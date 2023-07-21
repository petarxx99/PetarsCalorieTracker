package com.PetarsCalorieTracker.rangesfordatabase.formatsfordatabase;

import org.springframework.lang.NonNull;

public class ObjectFormattedForDatabase implements FormattedForDatabase{

    @NonNull
    private final Object value;

    public ObjectFormattedForDatabase(Object value){
        this.value = value;
    }

    @Override
    public String getStringFormattedForDatabase(){
        if (value instanceof String){
            return "'" + value + "'";
        }
        return value.toString();
    }
}
