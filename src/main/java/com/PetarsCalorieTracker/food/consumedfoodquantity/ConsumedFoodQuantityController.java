package com.PetarsCalorieTracker.food.consumedfoodquantity;

import com.PetarsCalorieTracker.controllers.MyResponse;
import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.food.FoodService;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLossService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/consumed_food_quantity")
public class ConsumedFoodQuantityController {

    private final ConsumedFoodQuantityService service;
    private final PersonWeightLossService personWeightLossService;
    private final FoodService foodService;

    public ConsumedFoodQuantityController(
            ConsumedFoodQuantityService service,
            PersonWeightLossService personWeightLossService,
            FoodService foodService){
        this.service = service;
        this.personWeightLossService = personWeightLossService;
        this.foodService = foodService;
    }

    @CrossOrigin(originPatterns = {"*"})
    @PostMapping("/save")
    public MyResponse saveNew(@RequestBody ConsumedFoodQuantity consumedFoodQuantity,
                        Authentication authentication){

        String username = authentication.getName();
        PersonWeightLoss person = personWeightLossService.getPersonByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
        consumedFoodQuantity.setPersonWeightLoss(person);

        Long foodId = consumedFoodQuantity.getConsumedFood().getFoodId();
        Optional<Food> foodOptional = foodService.findFoodById(foodId);
        if (foodOptional.isEmpty()){
            return MyResponse.negative("missing food", "No food with such id in the database.");
        }
        Food food = foodOptional.get();
        consumedFoodQuantity.setConsumedFood(food);

        service.save(consumedFoodQuantity);
        return MyResponse.positive();
    }

    @CrossOrigin(originPatterns = {"*"})
    @PutMapping("/update")
    public MyResponse update(@RequestBody ConsumedFoodQuantity consumedFoodQuantity,
                             Authentication authentication){

        String clientUsername = authentication.getName();
        Long consumedFoodQuantityId = consumedFoodQuantity.getId();

        Optional<ConsumedFoodQuantity> consumedFoodQFromDatabaseOptional = service.findById(consumedFoodQuantityId);
        if (consumedFoodQFromDatabaseOptional.isEmpty()){
            return MyResponse.negative("missing food", "No food with such id in the database.");
        }
        ConsumedFoodQuantity consumedFoodQuantityFromDatabase = consumedFoodQFromDatabaseOptional.get();
        String usernameFromDatabase = consumedFoodQuantityFromDatabase.getPersonWeightLoss().getPersonBasicInfo().getUsername();

        boolean userOwnsDataTheyWantToUpdate = usernameFromDatabase.equals(clientUsername);
        if (!userOwnsDataTheyWantToUpdate){
            return MyResponse.negative("authorization", "You don't have the permission to update this data.");
        }

        consumedFoodQuantityFromDatabase.update(consumedFoodQuantity);
        service.save(consumedFoodQuantityFromDatabase);
        return MyResponse.positive();
    }

    @CrossOrigin(originPatterns = {"*"})
    @DeleteMapping("/delete/{idToDelete}")
    public MyResponse delete(@PathVariable("idToDelete") String idToDeleteString,
                             Authentication authentication){
        String clientUsername = authentication.getName();
        try{
            long idToDelete = Long.parseLong(idToDeleteString);
            Optional<ConsumedFoodQuantity> foodFromDatabaseOptional = service.findById(idToDelete);
            if (foodFromDatabaseOptional.isEmpty()){
                return MyResponse.negative("missing food", "No food with such id in the database.");
            }
            ConsumedFoodQuantity foodFromDatabase = foodFromDatabaseOptional.get();

            Long personId = foodFromDatabase.getPersonWeightLoss().getId();
            Optional<PersonWeightLoss> personFromDatabaseOptional = personWeightLossService.findById(personId);
            if (personFromDatabaseOptional.isEmpty()){
                return MyResponse.negative("missing person", "Can't find the person with the id that food has.");
            }
            PersonWeightLoss personFromDatabase = personFromDatabaseOptional.get();
            String usernameFromDatabase = personFromDatabase.getPersonBasicInfo().getUsername();

            boolean userOwnsDataTheyWantToDelete = usernameFromDatabase.equals(clientUsername);
            if (!userOwnsDataTheyWantToDelete){
                return MyResponse.negative("authorization", "You don't have the permission to delete this data.");
            }

            service.deleteById(idToDelete);
            return MyResponse.positive();
        } catch(NumberFormatException e){
            return MyResponse.negative("number format", "Id can't be parsed into a number.");
        }
    }



}
