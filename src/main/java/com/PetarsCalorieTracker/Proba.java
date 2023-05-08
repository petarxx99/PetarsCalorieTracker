package com.PetarsCalorieTracker;


import com.PetarsCalorieTracker.food.ConsumedFoodQuantity;
import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.food.FoodQuantity;
import com.PetarsCalorieTracker.food.FoodRepository;
import com.PetarsCalorieTracker.food.consumedfoodquantity.ConsumedFoodQuantityRepository;
import com.PetarsCalorieTracker.person.DailyMass;
import com.PetarsCalorieTracker.person.PersonBasicInfo;
import com.PetarsCalorieTracker.person.PersonWeightLoss;
import com.PetarsCalorieTracker.person.dailymass.DailyMassRepository;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLossRepository;
import com.PetarsCalorieTracker.price.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class Proba {

    private static Food tomatoes = tomatoes = new Food("tomatoes",
            new BigDecimal(18),  //kcal
            new BigDecimal(0.88), //proteins
            new BigDecimal(3.92), //carbs
            new BigDecimal(0.2), //fats
            BigDecimal.ZERO, // saturated fats
            BigDecimal.ZERO, // trans fats
            new BigDecimal(1.2), // fiber
            (short) 50, //portion size in grams
            Optional.of(new Price(new BigDecimal(8)))
    );

    @Autowired
    private PersonWeightLossRepository personWeightLossRepository;

    @Autowired
    private DailyMassRepository dailyMassRepository;

    @Autowired
    private ConsumedFoodQuantityRepository consumedFoodQuantityRepository;

    @Autowired
    private FoodRepository foodRepository;

    @RequestMapping("/proba1")
    public void proba1(){
        var milorad = new PersonBasicInfo("Milorad", "Jovanovic", "Serbia", LocalDate.of(1999, 4, 3));
        var miloradVL = new PersonWeightLoss(milorad);
        personWeightLossRepository.save(miloradVL);

        var miloradsMass = new DailyMass(80.2f, LocalDate.now(), miloradVL);
        dailyMassRepository.save(miloradsMass);

        foodRepository.save(tomatoes);

        var tomatoes20grams = new ConsumedFoodQuantity(
                LocalDateTime.now(),
                new FoodQuantity(new BigDecimal(20), tomatoes),
                miloradVL
        );

        consumedFoodQuantityRepository.save(tomatoes20grams);
    }
}
