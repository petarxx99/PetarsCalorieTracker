package com.PetarsCalorieTracker;

import com.PetarsCalorieTracker.food.consumedfoodquantity.ConsumedFoodQuantity;
import com.PetarsCalorieTracker.food.consumedfoodquantity.ConsumedFoodQuantityRepository;
import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLossRepository;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLossService;
import com.PetarsCalorieTracker.rangesfordatabase.ComparingObjectsToMakeJPQLClauses;
import com.PetarsCalorieTracker.rangesfordatabase.FieldComparisonFirstMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ConsumedFQRepoProba {

    @Autowired
    private ConsumedFoodQuantityRepository consumedFoodQuantityRepository;

    @Autowired
    private PersonWeightLossRepository personWeightLossRepository;

    @Autowired
    private PersonWeightLossService personWeightLossService;

    @CrossOrigin(originPatterns = {"*"})
    @RequestMapping("/consumed_food_q_repo_proba_first_name")
    public List<PersonWeightLoss> probaFirstName(){
        List<PersonWeightLoss> people =
                personWeightLossRepository.getPersonByFirstNameAndHisFood("Mil");

        people.forEach(System.out::println);

        System.out.println("\nNow consumed food quantities\n");
        people.stream().
                map(PersonWeightLoss::getConsumedFoodQuantities).
                forEach(System.out::println);
        System.out.println("\nKRAJ\n");
        return people;
    }


    @CrossOrigin(originPatterns = {"*"})
    @RequestMapping("/consumed_food_q_repo_proba_last_name")
    public List<PersonWeightLoss> probaLastName(){
        List<PersonWeightLoss> people =
                personWeightLossRepository.getPersonByLastNameAndHisFood("Jova");

        people.forEach(System.out::println);

        System.out.println("\nNow consumed food quantities\n");
        people.stream().
                map(PersonWeightLoss::getConsumedFoodQuantities).
                forEach(System.out::println);
        System.out.println("\nKRAJ\n");
        return people;
    }


    @CrossOrigin(originPatterns = {"*"})
    @RequestMapping("/consumed_food_q_repo_proba_by_email")
    public PersonWeightLoss probaByEmail(){
        PersonWeightLoss person =
                personWeightLossRepository.getPersonByEmailAndHisFoodFromMomentAToMomentB(
                        "ivanpetrovic@gmail.com", LocalDateTime.of(2010,10,20,17,35,37),LocalDateTime.now()
                );

        System.out.println("person by email: " + person);

        System.out.println("\nNow consumed food quantities\n");
        System.out.println(person.getConsumedFoodQuantities());
        System.out.println("\nKRAJ\n");
        return person;
    }

    @CrossOrigin(originPatterns = {"*"})
    @RequestMapping("/consumed_food_q_repo_proba_by_email_vise_kalorija")
    public PersonWeightLoss probaByEmailViseKalorija(){
        PersonWeightLoss person =
                personWeightLossRepository.getPersonByEmailHisFoodWhenHeAteOverXNumberOfCalories(
                        "ivanpetrovic@gmail.com", new BigDecimal(200), LocalDateTime.of(2010,10,20,17,35,37),LocalDateTime.now()
                );

        System.out.println("person by email: " + person);

        System.out.println("\nNow consumed food quantities\n");
        System.out.println(person.getConsumedFoodQuantities());
        System.out.println("\nKRAJ\n");
        return person;
    }

    @CrossOrigin(originPatterns = {"*"})
    @RequestMapping("/consumed_food_q_repo_proba_by_email_weight_and_food")
    public PersonWeightLoss probaByEmailWeightAndFood(){
        PersonWeightLoss person =
                personWeightLossRepository.getPersonByEmailAndHisFoodAndWeightFromMomentAToMomentB(
                        "ivanpetrovic@gmail.com", LocalDateTime.of(2010,10,20,17,35,37),LocalDateTime.now()
                );

        System.out.println("person by email: " + person);

        System.out.println("\nNow consumed food quantities\n");
        System.out.println(person.getConsumedFoodQuantities());
        System.out.println("\nKRAJ\n");
        return person;
    }

    @CrossOrigin(originPatterns = {"*"})
    @RequestMapping("/consumed_food_q_repo_proba_person_weight_loss_custom")
    public List<PersonWeightLoss> probaWeightLossCustom(){
        var personNull = new PersonBasicInfo(null, null, null, null, null, null, null);
        var personEqual = new PersonBasicInfo(null, "Petrovic", null, null, null, null, null);

        List<PersonWeightLoss> people =
                personWeightLossService.queryBasedOnPersonFoodAndConsumedFood(
                        Optional.of(new ComparingObjectsToMakeJPQLClauses<PersonBasicInfo>(new FieldComparisonFirstMethod(),personNull, personNull, personEqual)),
                        Optional.empty(),
                        Optional.empty());

        System.out.println("custom people: " + people);

        System.out.println("\nNow consumed food quantities\n");
        people.stream().map(PersonWeightLoss::getConsumedFoodQuantities)
                        .forEach(System.out::println);
        System.out.println("\nKRAJ\n");
        return people;
    }

    @CrossOrigin(originPatterns = {"*"})
    @RequestMapping("/consumed_food_q_repo_proba_1")
    public List<ConsumedFoodQuantity> mapping1(){
        List<ConsumedFoodQuantity> cfq =
                consumedFoodQuantityRepository.getFoodWhereApersonAteOverXnumberOfKcalThatDay(
                LocalDate.now().minusYears(150),
                LocalDate.now(),
                59,
                1
        );

        cfq.forEach(System.out::println);

        System.out.println("\nNow the person\n");
        cfq.stream()
                .map(ConsumedFoodQuantity::getPersonWeightLoss)
                .forEach(System.out::println);

        System.out.println("\nNow the food\n");
        cfq.stream()
                .map(ConsumedFoodQuantity::getConsumedFood)
                .forEach(System.out::println);

        System.out.println("\nKRAJ\n");
        return cfq;
    }
}
