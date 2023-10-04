package com.PetarsCalorieTracker.database;

import org.springframework.lang.NonNull;

public interface SQLSanitizer {
    public String sanitize(@NonNull String sql);
}
