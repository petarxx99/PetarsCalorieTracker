package com.PetarsCalorieTracker.food.consumedfoodquantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumedFoodQuantityService {

    private ConsumedFoodQuantityRepository repository;

    @Autowired
    public ConsumedFoodQuantityService(ConsumedFoodQuantityRepository repository){
        this.repository = repository;
    }

    public void save(ConsumedFoodQuantity consumedFoodQuantity){
        repository.save(consumedFoodQuantity);
    }

    public Optional<ConsumedFoodQuantity> findById(long id){
        return repository.findById(id);
    }
    public void deleteById(long id){
        repository.deleteById(id);
    }



}
