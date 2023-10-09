package com.PetarsCalorieTracker.person.personweightloss;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class ControllerPersonWeightLoss {

    private final PersonWeightLossService personWeightLossService;

    public ControllerPersonWeightLoss(PersonWeightLossService personWeightLossService){
        this.personWeightLossService = personWeightLossService;
    }

    @CrossOrigin(originPatterns = {"*"})
    @PostMapping("/persons_weight_and_food")
    public PersonWeightLoss weightAndFoodByUsername(Authentication authentication,
                                                    @RequestBody LocalDateTime startTime,
                                                    @RequestBody LocalDateTime endTime){
        String username = authentication.getName();
        PersonWeightLoss person =
                personWeightLossService.getPersonByUsernameAndHisFoodAndWeightFromMomentAToMomentB(
                        username, startTime, endTime
                );

        System.out.println("person by username: " + person);

        System.out.println("\nNow consumed food quantities\n");
        System.out.println(person.getConsumedFoodQuantities());
        System.out.println("\nKRAJ\n");
        return person;
    }
}
