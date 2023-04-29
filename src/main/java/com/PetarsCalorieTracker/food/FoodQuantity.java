package com.PetarsCalorieTracker.food;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "food_quantity")
public class FoodQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_quantity_id")
    private Long id;

    @NonNull
    @Column(name = "quantity_in_grams", nullable = false)
    private BigDecimal quantityInGrams;

    @ManyToOne
    @JoinColumn(name = "food_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Food food;

    @OneToMany(mappedBy = "consumedFood")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<ConsumedFoodQuantity> consumedFoodQuantities;


    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private final static BigDecimal ZERO = BigDecimal.ZERO;
    
    public FoodQuantity(){}

    public FoodQuantity(@NonNull BigDecimal quantityInGrams, Food food) {
        this.quantityInGrams = quantityInGrams;
        this.food = food;
    }

    public FoodQuantity(Long id, @NonNull BigDecimal quantityInGrams, Food food) {
        this.id = id;
        this.quantityInGrams = quantityInGrams;
        this.food = food;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public BigDecimal getQuantityInGrams() {
        return quantityInGrams;
    }

    public void setQuantityInGrams(@NonNull BigDecimal quantityInGrams) {
        this.quantityInGrams = quantityInGrams;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Set<ConsumedFoodQuantity> getConsumedFoodQuantities() {
        return consumedFoodQuantities;
    }

    public void setConsumedFoodQuantities(Set<ConsumedFoodQuantity> consumedFoodQuantities) {
        this.consumedFoodQuantities = consumedFoodQuantities;
    }

    @Override
    public String toString() {
        return "FoodQuantity{" +
                "id=" + id +
                ", quantityInGrams=" + quantityInGrams +
                ", food=" + food +
                '}';
    }

    

    public FoodQuantity createFoodFromIngredients(@NonNull List<FoodQuantity> ingredients,
                                          @NonNull String foodName,
                                          Optional<Short> portionSizeInGramsOptional){
        FoodQuantity resultingFoodQuantity = new FoodQuantity(ZERO, new Food(foodName, ZERO, ZERO, ZERO, ZERO, (short) 0, ZERO, ZERO));
        resultingFoodQuantity.mixWithAListOfFood(ingredients);

        Food resultingFood = resultingFoodQuantity.getFood();
        portionSizeInGramsOptional.ifPresent(resultingFood::setOnePortionSizeInGrams);
        return resultingFoodQuantity;
    }

    public void mixWithAListOfFood(@NonNull List<FoodQuantity> otherFoodQuantities){
        otherFoodQuantities.forEach(this::mixWithFood);
    }
    public void mixWithFood(@NonNull FoodQuantity otherFoodQuantity){
        var otherFood = otherFoodQuantity.food;
        var otherQuantity = otherFoodQuantity.quantityInGrams;
        
        food.setKcalPer100g(calculateIngredientPer100g(
                food.getKcalPer100g(), quantityInGrams, otherFood.getKcalPer100g(), otherQuantity
        ));
        
        food.setProteinsPer100g(calculateIngredientPer100g(
                food.getProteinsPer100g(), quantityInGrams, otherFood.getProteinsPer100g(), otherQuantity
        ));
        
        food.setFatsPer100g(calculateIngredientPer100g(
                food.getFatsPer100g(), quantityInGrams, otherFood.getFatsPer100g(), otherQuantity
        ));
        
        food.setCarbsPer100g(calculateIngredientPer100g(
                food.getCarbsPer100g(), quantityInGrams, otherFood.getCarbsPer100g(), otherQuantity
        ));
        
        food.setFiberPer100g(calculateIngredientPer100g(
                food.getFiberPer100g(), quantityInGrams, otherFood.getFiberPer100g(), otherQuantity
        ));
        
        food.setBadTransFatsPer100g(calculateIngredientPer100g(
                food.getBadTransFatsPer100g(), quantityInGrams, otherFood.getBadTransFatsPer100g(), otherQuantity
        ));
        
        food.setSaturatedFatsPer100g(calculateIngredientPer100g(
                food.getSaturatedFatsPer100g(), quantityInGrams, otherFood.getSaturatedFatsPer100g(), otherQuantity
        ));
        
        food.setPricePer100g(calculateIngredientPer100g(
                food.getPricePer100g(), quantityInGrams, otherFood.getPricePer100g(), otherQuantity
        ));
        
        quantityInGrams = quantityInGrams.add(otherQuantity);
    }
    
    
    private BigDecimal calculateIngredientPer100g(BigDecimal ingredientFromFirstFoodPer100g,
                                                   @NonNull BigDecimal firstFoodQuantity,
                                                   BigDecimal ingredientFromSecondFoodPer100g,
                                                   @NonNull BigDecimal secondFoodQuantity){

        ingredientFromFirstFoodPer100g = ifNullThenSetTo0(ingredientFromFirstFoodPer100g);
        ingredientFromSecondFoodPer100g = ifNullThenSetTo0(ingredientFromSecondFoodPer100g);

        var totalIngredientFromFirstFood = ingredientFromFirstFoodPer100g.
                multiply(firstFoodQuantity).
                divide(ONE_HUNDRED);
        var totalIngredientFromSecondFood = ingredientFromSecondFoodPer100g.
                multiply(secondFoodQuantity).
                divide(ONE_HUNDRED);
        
        var totalIngredient = totalIngredientFromFirstFood.add(totalIngredientFromSecondFood);
        var totalFoodQuantity = firstFoodQuantity.add(secondFoodQuantity);

        var ingredientPer100grams = totalIngredient.multiply(ONE_HUNDRED).
                                                divide(totalFoodQuantity);
        return ingredientPer100grams;
    }


    private BigDecimal ifNullThenSetTo0(BigDecimal number){
        if (number == null){
            number = BigDecimal.ZERO;
        }
        return number;
    }
    
}
