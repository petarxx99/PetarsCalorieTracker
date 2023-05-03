package com.PetarsCalorieTracker.food;

import com.PetarsCalorieTracker.price.Price;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public class FoodQuantity {

    private static int NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT = 5;

    @NonNull
    private BigDecimal quantityInGrams;

    @NonNull
    private Food food;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private final static BigDecimal ZERO = BigDecimal.ZERO;
    private final static String NO_NAME_FOOD = "no name food";

    public FoodQuantity(){
        try {
            this.quantityInGrams = BigDecimal.ZERO;
            this.food = new Food(NO_NAME_FOOD, ZERO, ZERO, ZERO, ZERO, (short) 0, Optional.of(new Price(ZERO)));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public FoodQuantity(@NonNull BigDecimal quantityInGrams, @NonNull Food food) {
        this.quantityInGrams = quantityInGrams;
        this.food = food;
    }

    @NonNull
    public Food getFood(){
        return food;
    }

    @NonNull
    public BigDecimal getQuantityInGrams(){
        return quantityInGrams;
    }

    public FoodQuantity createFoodFromIngredients(@NonNull List<FoodQuantity> ingredients,
                                          @NonNull String foodName,
                                          Optional<Short> portionSizeInGramsOptional){
        FoodQuantity resultingFoodQuantity = new FoodQuantity();
        resultingFoodQuantity.mixWithAListOfFood(foodName, ingredients);

        Food resultingFood = resultingFoodQuantity.food;
        portionSizeInGramsOptional.ifPresent(resultingFood::setOnePortionSizeInGrams);
        return resultingFoodQuantity;
    }

    public void mixWithAListOfFood(@NonNull String newName, @NonNull List<FoodQuantity> otherFoodQuantities){
        otherFoodQuantities.forEach(ingredient -> this.mixWithFood(newName, ingredient));
    }
    public void mixWithFood(@NonNull String newName, @NonNull FoodQuantity otherFoodQuantity){
        var otherFood = otherFoodQuantity.food;
        var otherQuantity = otherFoodQuantity.quantityInGrams;
        Food newFood = food.copyAllButNameAndId(newName);
        
        newFood.setKcalPer100g(calculateIngredientPer100g(
                food.getKcalPer100g(), quantityInGrams, otherFood.getKcalPer100g(), otherQuantity
        ));
        
        newFood.setProteinsPer100g(calculateIngredientPer100g(
                food.getProteinsPer100g(), quantityInGrams, otherFood.getProteinsPer100g(), otherQuantity
        ));
        
        newFood.setFatsPer100g(calculateIngredientPer100g(
                food.getFatsPer100g(), quantityInGrams, otherFood.getFatsPer100g(), otherQuantity
        ));
        
        newFood.setCarbsPer100g(calculateIngredientPer100g(
                food.getCarbsPer100g(), quantityInGrams, otherFood.getCarbsPer100g(), otherQuantity
        ));
        
        newFood.setFiberPer100g(calculateIngredientPer100g(
                food.getFiberPer100g(), quantityInGrams, otherFood.getFiberPer100g(), otherQuantity
        ));
        
        newFood.setBadTransFatsPer100g(calculateIngredientPer100g(
                food.getBadTransFatsPer100g(), quantityInGrams, otherFood.getBadTransFatsPer100g(), otherQuantity
        ));
        
        newFood.setSaturatedFatsPer100g(calculateIngredientPer100g(
                food.getSaturatedFatsPer100g(), quantityInGrams, otherFood.getSaturatedFatsPer100g(), otherQuantity
        ));
        
        newFood.setPricePer100g(calculateIngredientPer100g(
                food.getPricePer100g(), quantityInGrams, otherFood.getPricePer100g(), otherQuantity
        ));

        food = newFood;
        quantityInGrams = quantityInGrams.add(otherQuantity);
    }
    
    
    private BigDecimal calculateIngredientPer100g(BigDecimal ingredientFromFirstFoodPer100g,
                                                   @NonNull BigDecimal firstFoodQuantity,
                                                   BigDecimal ingredientFromSecondFoodPer100g,
                                                   @NonNull BigDecimal secondFoodQuantity){

        ingredientFromFirstFoodPer100g = ifNullThenSetTo0(ingredientFromFirstFoodPer100g);
        ingredientFromSecondFoodPer100g = ifNullThenSetTo0(ingredientFromSecondFoodPer100g);

        var totalIngredientFromFirstFood = ingredientFromFirstFoodPer100g.
                multiply(firstFoodQuantity).setScale(NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP);
        var totalIngredientFromSecondFood = ingredientFromSecondFoodPer100g.
                multiply(secondFoodQuantity).setScale(NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP);
        
        var totalIngredient = totalIngredientFromFirstFood.add(totalIngredientFromSecondFood).setScale(NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP);
        var totalFoodQuantity = firstFoodQuantity.add(secondFoodQuantity).setScale(NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP);

        var ingredientPer100grams = totalIngredient.divide(totalFoodQuantity, NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP);
        return ingredientPer100grams;
    }


    private BigDecimal ifNullThenSetTo0(BigDecimal number){
        if (number == null){
            number = BigDecimal.ZERO;
        }
        return number;
    }


    public BigDecimal toKcal(){
        return quantityInGrams.multiply(getFood().getKcalPer100g()).divide(ONE_HUNDRED, NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal toProteins(){
        return quantityInGrams.multiply(getFood().getProteinsPer100g()).divide(ONE_HUNDRED, NUMBER_OF_DIGITS_RIGHT_TO_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP);
    }


    @Override
    public String toString() {
        return "FoodQuantity{" +
                "quantityInGrams=" + quantityInGrams +
                ", food=" + food +
                '}';
    }
}
