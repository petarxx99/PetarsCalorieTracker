package com.PetarsCalorieTracker.person.personweightloss;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ControllerPersonWeightLoss {

    private final PersonWeightLossService personWeightLossService;
    private final DateTimeFormatter dateTimeFormatter;

    public ControllerPersonWeightLoss(PersonWeightLossService personWeightLossService, DateTimeFormatter dateTimeFormatter){
        this.personWeightLossService = personWeightLossService;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @CrossOrigin(originPatterns = {"*"})
    @GetMapping("/persons_weight_and_food")
    public PersonWeightLoss weightAndFoodByUsername(
            Authentication authentication,
            @RequestParam("startTime") String startTimeString,
            @RequestParam("endTime") String endTimeString){

        LocalDateTime startTime = LocalDateTime.parse(startTimeString, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeString, dateTimeFormatter);

        String username = authentication.getName();
        PersonWeightLoss person =
                personWeightLossService.getPersonByUsernameAndHisFoodAndWeightFromMomentAToMomentB(
                        username, startTime, endTime
                );

        System.out.println("person by username: " + person);
        System.out.println("username: " + username);

        System.out.println("\nNow consumed food quantities\n");
        System.out.println(person.getConsumedFoodQuantities());
        System.out.println("\nKRAJ\n");
        return person;
    }

    @CrossOrigin(originPatterns = {"*"})
    @GetMapping("/get_person_weight_loss")
    public PersonWeightLoss getPersonWeightLoss(Authentication authentication){
        String username = authentication.getName();
        return personWeightLossService.getPersonByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
    }

    @CrossOrigin(originPatterns={"*"})
    @GetMapping("/get_person_weight_loss_and_food")
    public PersonWeightLoss getPersonWeightLossAndFood(Authentication authentication){
        String username = authentication.getName();
        return personWeightLossService.getPersonAndFoodByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
    }


}
