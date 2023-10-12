package com.PetarsCalorieTracker.food;

import com.PetarsCalorieTracker.database.SQLSanitizer;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import com.PetarsCalorieTracker.querybuilders.QueryBuilder;
import com.PetarsCalorieTracker.rangesfordatabase.QueryClauseMaker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FoodRepository repository;

    private final QueryBuilder queryBuilder;

    private final SQLSanitizer sqlSanitizer;

   private final static String FOOD_ALIAS = "f";

    @Autowired
    public FoodService(FoodRepository repository,
                       QueryBuilder queryBuilder,
                       SQLSanitizer sqlSanitizer){
        this.repository = repository;
        this.queryBuilder = queryBuilder;
        this.sqlSanitizer = sqlSanitizer;
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


    public List<Food> customFoodQuery(
            @NonNull QueryClauseMaker foodClauseMaker){


        String clause = foodClauseMaker.clause(FOOD_ALIAS, "AND").orElse(null);
        final String SELECT = "SELECT " + FOOD_ALIAS;
        final String FROM = "FROM Food " + FOOD_ALIAS;

        String unsafeQuery = queryBuilder.addSelect(SELECT).addFrom(FROM)
                .addClause("WHERE", clause);
        String query = sqlSanitizer.sanitize(unsafeQuery);
        System.out.println("query: " + query);
        return entityManager.createQuery(query, Food.class).getResultList();
    }


    public Optional<Food> findFoodByName(@NonNull String foodName) {
        return repository.findFoodByName(foodName.trim());
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }
}
