package com.PetarsCalorieTracker.food;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

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

    @ManyToOne(fetch = FetchType.EAGER)
    /* I want all info to be fetched immediately, as time of eating
     without specifying what was eaten doesn't tell me much of anything. */
    @JoinColumn(name = "food_quantity_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private FoodQuantity consumedFood;

    public ConsumedFoodQuantity(){}

    public ConsumedFoodQuantity(@NonNull LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ConsumedFoodQuantity(@NonNull LocalDateTime localDateTime, FoodQuantity consumedFood) {
        this.localDateTime = localDateTime;
        this.consumedFood = consumedFood;
    }

    public ConsumedFoodQuantity(Long id, @NonNull LocalDateTime localDateTime, FoodQuantity consumedFood) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.consumedFood = consumedFood;
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

    public FoodQuantity getConsumedFood() {
        return consumedFood;
    }

    public void setConsumedFood(FoodQuantity consumedFood) {
        this.consumedFood = consumedFood;
    }

    @Override
    public String toString() {
        return "ConsumedFoodQuantity{" +
                "id=" + id +
                ", localDateTime=" + localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm")) +
                ", consumedFood=" + consumedFood +
                '}';
    }
}
