package com.PetarsCalorieTracker.food;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "consumed_food_quantity")
public class ConsumedFoodQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumed_food_quantity_id")
    private Long id;

    @NonNull
    @Column(name = "time_of_consumption", nullable = false)
    private LocalDateTime localDateTime;

    @NonNull
    @Column(name = "consumed_food_in_grams", nullable = false)
    private BigDecimal consumedFoodInGrams;

    @ManyToOne(fetch = FetchType.EAGER)
    /* I want all info to be fetched immediately, as time of eating
     without specifying what was eaten doesn't tell me much of anything. */
    @JoinColumn(name = "food_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Food consumedFood;



    public ConsumedFoodQuantity(){}

    public ConsumedFoodQuantity(@NonNull LocalDateTime localDateTime, @NonNull FoodQuantity food) {
        this.localDateTime = localDateTime;
        this.consumedFoodInGrams = food.getQuantityInGrams();
        this.consumedFood = food.getFood();
    }

    public ConsumedFoodQuantity(long id, @NonNull LocalDateTime localDateTime, @NonNull FoodQuantity food) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.consumedFoodInGrams = food.getQuantityInGrams();
        this.consumedFood = food.getFood();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(@NonNull LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @NonNull
    public BigDecimal getConsumedFoodInGrams() {
        return consumedFoodInGrams;
    }

    public void setConsumedFoodInGrams(@NonNull BigDecimal consumedFoodInGrams) {
        this.consumedFoodInGrams = consumedFoodInGrams;
    }

    public Food getConsumedFood() {
        return consumedFood;
    }

    public void setConsumedFood(Food consumedFood) {
        this.consumedFood = consumedFood;
    }

    @Override
    public String toString() {
        return "ConsumedFoodQuantity{" +
                "id=" + id +
                ", localDateTime=" + localDateTime +
                ", consumedFoodInGrams=" + consumedFoodInGrams +
                ", consumedFood=" + consumedFood +
                '}';
    }
}
