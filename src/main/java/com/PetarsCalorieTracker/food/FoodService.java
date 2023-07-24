package com.PetarsCalorieTracker.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {


    private FoodRepository repository;

    @Autowired
    public FoodService(FoodRepository repository){
        this.repository = repository;
    }

    public Optional<Food> findFoodById(long id){
        return repository.findById(id);
    }

    public void deleteFoodById(long id){
        repository.deleteById(id);
    }

    public void save(Food food){
        repository.save(food);
    }

    public List<Food> getFoodByKcal(@NonNull double minKcalPer100g,
                                    @NonNull double maxKcalPer100g){
        return repository.getFoodByKcal(minKcalPer100g, maxKcalPer100g);
    }

    public List<Food> getFoodByName(@NonNull String name){
        return repository.getFoodByName(name);
    }

    public List<Food> getFoodByMinimumAmountOfProtein(@NonNull BigDecimal minProteins){
        return repository.getFoodByMinimumAmountOfProtein(minProteins);
    }


    public List<Food> getFoodByMaximumKcalToProteinsRatio(@NonNull BigDecimal maxKcalToProteinsRatio){
        return repository.getFoodByMaximumKcalToProteinsRatio(maxKcalToProteinsRatio);
    }


}
