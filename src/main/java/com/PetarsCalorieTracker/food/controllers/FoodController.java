package com.PetarsCalorieTracker.food.controllers;

import com.PetarsCalorieTracker.controllers.MyResponse;
import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.food.FoodService;
import com.PetarsCalorieTracker.rangesfordatabase.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodService service;


    public FoodController(FoodService service){
        this.service = service;
    }

    @CrossOrigin(originPatterns={"*"})
    @PostMapping("/query_by_custom_parameters")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<Food> custom(@RequestBody LowestBiggestEqualData<Food> customParams){
        QueryClauseMaker clauseMaker = new ComparingObjectsToMakeJPQLClauses<Food>(new FieldComparisonFirstMethod(), customParams.lowest(), customParams.biggest(), customParams.equal());
        return service.customFoodQuery(clauseMaker);
    }

    @CrossOrigin(originPatterns={"*"})
    @PostMapping("/save_new_food")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public MyResponse saveNewFood(@RequestBody Food food){
        Optional<Food> foodInDatabase = service.findFoodByName(food.getFoodName());
        if (foodInDatabase.isPresent()){
            return MyResponse.negative("duplicate", "Food with the same name already exists.");
        }

        service.save(food);
        return MyResponse.positive();
    }

    @CrossOrigin(originPatterns={"*"})
    @PostMapping("/update")
    @Secured({"ROLE_ADMIN"})
    public MyResponse updateFood(@RequestBody Food food) throws IllegalAccessException {
        Optional<Food> foodFromDatabaseOptional = service.findFoodById(food.getFoodId());
        if (foodFromDatabaseOptional.isEmpty()) {
            return MyResponse.negative("missing", "There is no food in the database with that id.");
        }
        Food foodFromDatabase = foodFromDatabaseOptional.get();
        foodFromDatabase.update(food);
        service.save(foodFromDatabase);
        return MyResponse.positive();
    }


    @CrossOrigin(originPatterns={"*"})
    @DeleteMapping("/delete/{idToDelete}")
    @Secured({"ROLE_ADMIN"})
    public MyResponse deleteFood(@PathVariable("idToDelete") String idToDeleteString){
        try{
            long idToDelete = Long.parseLong(idToDeleteString);
            service.deleteById(idToDelete);
            return MyResponse.positive();
        } catch(NumberFormatException e){
            return MyResponse.negative("number format", "Id can't be parsed into a number.");
        }
    }


}
