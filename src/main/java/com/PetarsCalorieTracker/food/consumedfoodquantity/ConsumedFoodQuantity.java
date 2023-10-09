package com.PetarsCalorieTracker.food.consumedfoodquantity;

import com.PetarsCalorieTracker.food.Food;
import com.PetarsCalorieTracker.food.FoodQuantity;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumed_food_quantity")
public class ConsumedFoodQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumed_food_quantity_id")
    private Long id;


    @Column(name = "time_of_consumption", nullable = false)
    private LocalDateTime timeOfConsumption;


    @Column(name = "consumed_food_in_grams", nullable = false)
    private BigDecimal consumedFoodInGrams;

    @ManyToOne(fetch = FetchType.EAGER)
    /* I want all info to be fetched immediately, as time of eating
     without specifying what was eaten doesn't tell me much of anything. */
    @JoinColumn(name = "fk_consumed_food_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Food consumedFood;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_person_that_eats_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonIgnore
    private PersonWeightLoss personWeightLoss;

    public ConsumedFoodQuantity(){}

    public ConsumedFoodQuantity( LocalDateTime timeOfConsumption,
                                 FoodQuantity food,
                                 PersonWeightLoss personWeightLoss) {
        this.timeOfConsumption = timeOfConsumption;
        this.consumedFoodInGrams = food.getQuantityInGrams();
        this.consumedFood = food.getFood();
        this.personWeightLoss = personWeightLoss;
    }

    public ConsumedFoodQuantity(long id,
                                 LocalDateTime timeOfConsumption,
                                 FoodQuantity food,
                                 PersonWeightLoss personWeightLoss) {
        this.id = id;
        this.timeOfConsumption = timeOfConsumption;
        this.consumedFoodInGrams = food.getQuantityInGrams();
        this.consumedFood = food.getFood();
        this.personWeightLoss = personWeightLoss;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDateTime getTimeOfConsumption() {
        return timeOfConsumption;
    }

    public void setTimeOfConsumption(LocalDateTime timeOfConsumption) {
        this.timeOfConsumption = timeOfConsumption;
    }

    public BigDecimal getConsumedFoodInGrams() {
        return consumedFoodInGrams;
    }

    public void setConsumedFoodInGrams( BigDecimal consumedFoodInGrams) {
        this.consumedFoodInGrams = consumedFoodInGrams;
    }

    public Food getConsumedFood() {
        return consumedFood;
    }

    public void setConsumedFood(Food consumedFood) {
        this.consumedFood = consumedFood;
    }

    public PersonWeightLoss getPersonWeightLoss() {
        return personWeightLoss;
    }

    public void setPersonWeightLoss(PersonWeightLoss personWeightLoss) {
        this.personWeightLoss = personWeightLoss;
    }

    @Override
    public String toString() {
        return "ConsumedFoodQuantity{" +
                "id=" + id +
                ", timeOfConsumption=" + timeOfConsumption +
                ", consumedFoodInGrams=" + consumedFoodInGrams +
                ", consumedFood=" + consumedFood +
                '}';
    }
}
