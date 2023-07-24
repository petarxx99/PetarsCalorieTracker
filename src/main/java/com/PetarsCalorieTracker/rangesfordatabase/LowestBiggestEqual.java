package com.PetarsCalorieTracker.rangesfordatabase;

import java.util.Optional;

public record LowestBiggestEqual<T>(T lowest, T biggest, T equal, Optional<String> classAlias) {
}
