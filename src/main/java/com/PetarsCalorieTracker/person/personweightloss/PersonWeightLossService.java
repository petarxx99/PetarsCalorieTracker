package com.PetarsCalorieTracker.person.personweightloss;

import com.PetarsCalorieTracker.constants.Constants;
import com.PetarsCalorieTracker.rangesfordatabase.QueryClauseMaker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class PersonWeightLossService {

    public static final String PEOPLE_WEIGHT_LOSS_ALIAS = "personWL";
    public static final String PEOPLE_BASIC_INFO_ALIAS = "person";
    public static final String FOOD_ALIAS = "food";
    public static final String CONSUMED_FOOD_QUANTITY_ALIAS = "cfq";
    public static final String DAILY_MASSES_ALIAS = "dm";


    private static final String PERSON_WITHOUT_DAILY_MASS =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
            "cfq.consumedFood food ";

    private static final String PERSON_WITH_DAILY_MASS =
            "SELECT personWL FROM PersonWeightLoss personWL LEFT JOIN FETCH " +
            "personWL.personBasicInfo person LEFT JOIN FETCH " +
            "personWL.dailyMassesInKilograms dm LEFT JOIN FETCH " +
            "personWL.consumedFoodQuantities cfq LEFT JOIN FETCH " +
            "cfq.consumedFood food ";


    @PersistenceContext
    private EntityManager entityManager;
    private PersonWeightLossRepository repository;

    @Autowired
    public PersonWeightLossService(PersonWeightLossRepository repository){
        this.repository = repository;
    }

    public List<PersonWeightLoss> queryBasedOnPersonFoodConsumedFoodDailyMass(
            @NonNull QueryClauseMaker personBasicInfo,
            @NonNull QueryClauseMaker food,
            @NonNull QueryClauseMaker consumedFoodQuantity,
            @NonNull QueryClauseMaker dailyMass){

        StringBuilder clause = makeAClauseForPersonBasicInfoFoodAndConsumedFoodQuantity(personBasicInfo, food, consumedFoodQuantity);
        Optional<String> dailyMassClause = dailyMass.clause(DAILY_MASSES_ALIAS, "AND");
        addClauseAndReturnTrueIfClauseIsAdded(clause, dailyMassClause);

        String query = PERSON_WITH_DAILY_MASS + " WHERE " + clause.toString();
        return entityManager.createQuery(query, PersonWeightLoss.class).getResultList();
    }

    public List<PersonWeightLoss> queryBasedOnPersonFoodAndConsumedFood(
            @NonNull QueryClauseMaker personBasicInfo,
            @NonNull QueryClauseMaker food,
            @NonNull QueryClauseMaker consumedFoodQuantity){

        String whereClause = makeAClauseForPersonBasicInfoFoodAndConsumedFoodQuantity(personBasicInfo, food, consumedFoodQuantity).toString();
        String query = PERSON_WITHOUT_DAILY_MASS + " WHERE " + whereClause;

        return entityManager.createQuery(query, PersonWeightLoss.class).getResultList();
    }



    public List<PersonWeightLoss> getPersonByFirstNameAndHisFood(@NonNull String firstName){
        return repository.getPersonByFirstNameAndHisFood(firstName);
    }

    public List<PersonWeightLoss> getPersonByLastNameAndHisFood(@NonNull String lastName){
        return repository.getPersonByLastNameAndHisFood(lastName);
    }

    public PersonWeightLoss getPersonById(long id){
        return repository.getPersonById(id);
    }

    public PersonWeightLoss getPersonByIdAndHisFoodAndWeightFromMomentAToMomentB(
            long id,
            @NonNull LocalDateTime startMoment,
            @NonNull LocalDateTime endMoment
    ){
        return repository.getPersonByIdAndHisFoodAndWeightFromMomentAToMomentB(id, startMoment, endMoment);
    }

    public PersonWeightLoss getPersonByIdAndHisFoodFromMomentAToMomentB(
            long id,
            @NonNull LocalDateTime startMoment,
            @NonNull LocalDateTime endMoment
    ){
        return repository.getPersonByIdAndHisFoodFromMomentAToMomentB(id, startMoment, endMoment);
    }

    public PersonWeightLoss getPersonByEmailAndHisFoodAndWeightFromMomentAToMomentB(@NonNull String email,
                                                                                    @NonNull LocalDateTime startMoment,
                                                                                    @NonNull LocalDateTime endMoment){
        return repository.getPersonByEmailAndHisFoodAndWeightFromMomentAToMomentB(email, startMoment, endMoment);
    }

    public PersonWeightLoss getPersonByEmailAndHisFoodFromMomentAToMomentB(@NonNull String email,
                                                                           @NonNull LocalDateTime startMoment,
                                                                           @NonNull LocalDateTime endMoment){
        return repository.getPersonByEmailAndHisFoodFromMomentAToMomentB(email, startMoment, endMoment);
    }

    public PersonWeightLoss getPersonByIdHisFoodWhenHeAteOverXNumberOfCalories(long id,
                                                                               @NonNull BigDecimal minimumKcal,
                                                                               @NonNull LocalDateTime startMoment,
                                                                               @NonNull LocalDateTime endMoment){
        return repository.getPersonByIdHisFoodWhenHeAteOverXNumberOfCalories(id, minimumKcal.multiply(Constants.ONE_HUNDRED), startMoment, endMoment);
    }

    public PersonWeightLoss getPersonByEmailHisFoodWhenHeAteOverXNumberOfCalories(@NonNull String email,
                                                                                  @NonNull BigDecimal minimumKcal,
                                                                                  @NonNull LocalDateTime startMoment,
                                                                                  @NonNull LocalDateTime endMoment){
        return repository.getPersonByEmailHisFoodWhenHeAteOverXNumberOfCalories(email, minimumKcal.multiply(Constants.ONE_HUNDRED), startMoment, endMoment);
    }



    private StringBuilder makeAClauseForPersonBasicInfoFoodAndConsumedFoodQuantity(@NonNull QueryClauseMaker personBasicInfo,
                                                                                   @NonNull QueryClauseMaker food,
                                                                                   @NonNull QueryClauseMaker consumedFoodQuantity){
        Optional<String> personClause = personBasicInfo.clause(PEOPLE_BASIC_INFO_ALIAS, "AND");
        Optional<String> foodClause = food.clause(FOOD_ALIAS, "AND");
        Optional<String> consumedFoodQuantityClause = consumedFoodQuantity.clause(CONSUMED_FOOD_QUANTITY_ALIAS, "AND");

        return addClausesTogether(personClause, foodClause, consumedFoodQuantityClause);
    }

    private boolean addClauseAndReturnTrueIfClauseIsAdded(StringBuilder allClauses,
                                                          Optional<String> newClause){
        if (newClause.isPresent()){
            if (allClauses.length() > 0){
                allClauses.append(" AND ");
            }
            allClauses.append(newClause.get());
            return true;
        }
        return false;
    }


    @SafeVarargs
    private StringBuilder addClausesTogether(Optional<String> ... clauses){
        StringBuilder result = new StringBuilder();
        if (clauses.length == 0){
            return result;
        }

        clauses[0].ifPresent(result::append);

        for(int i=1; i<clauses.length; i++){
            addClauseAndReturnTrueIfClauseIsAdded(result, clauses[i]);
        }

        return result;
    }


}
