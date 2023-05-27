package com.PetarsCalorieTracker.food.consumedfoodquantity;

import org.springframework.beans.factory.annotation.Autowired;

public class ConsumedFoodQuantityService {

    private ConsumedFoodQuantityRepository repository;

    @Autowired
    public ConsumedFoodQuantityService(ConsumedFoodQuantityRepository repository){
        this.repository = repository;
    }


}
