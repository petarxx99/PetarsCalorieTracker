package com.PetarsCalorieTracker.person.dailymass.controllers;

import com.PetarsCalorieTracker.person.dailymass.dailymasscalories.DailyMassAndCalories;
import com.PetarsCalorieTracker.person.dailymass.DailyMassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class DailyMassCaloriesController {

    private DailyMassService service;
    private final DateTimeFormatter dateTimeFormatter;

    @Autowired
    DailyMassCaloriesController(DailyMassService service, DateTimeFormatter dateTimeFormatter){
        this.service = service;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @CrossOrigin(originPatterns = {"*"})
    @GetMapping("/get_daily_mass_and_calories")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<DailyMassAndCalories> getDailyMassAndCalories(
            Authentication authentication,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate){

        String username = authentication.getName();

        return service.getDailyMassAndCaloriesByUsername(username, startDate, endDate);
    }

    @CrossOrigin(originPatterns = {"*"})
    @GetMapping("/get_daily_mass_and_calories_on_days_when_both_exist")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<DailyMassAndCalories> getDailyMassAndCaloriesOnDaysWhenBothExist(
            Authentication authentication,
            @RequestParam("start_date") String startDateString,
            @RequestParam("end_date") String endDateString){

        LocalDate startDate = LocalDate.parse(startDateString, dateTimeFormatter);
        LocalDate endDate = LocalDate.parse(endDateString, dateTimeFormatter);
        String username = authentication.getName();

        return service.getDailyMassAndCaloriesWhenBothExistByUsername(username, startDate, endDate);
    }



}
