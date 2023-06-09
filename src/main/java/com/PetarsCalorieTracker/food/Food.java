package com.PetarsCalorieTracker.food;

import com.PetarsCalorieTracker.price.Price;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name="food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long foodId;

    @NonNull
    @Column(name="name", nullable = false)
    private String foodName;

    @NonNull
    @Column(name = "kcal_per_100g", nullable = false)
    private BigDecimal kcalPer100g;

    @NonNull
    @Column(name = "proteins_per_100g", nullable = false)
    private BigDecimal proteinsPer100g;

    @Column(name = "carbs_per_100g", nullable = true)
    private BigDecimal carbsPer100g;

    @Column(name = "fats_per_100g", nullable = true)
    private BigDecimal fatsPer100g;

    @Column(name = "saturated_fats_per_100g", nullable = true)
    private BigDecimal saturatedFatsPer100g;

    @Column(name = "bad_trans_fats_per_100g", nullable = true)
    private BigDecimal badTransFatsPer100g;

    @Column(name = "fiber_per_100g", nullable=true)
    private BigDecimal fiberPer100g;

    @Column(name = "one_portion_size_in_grams", nullable = true)
    private short onePortionSizeInGrams;

    @Column(name = "price_per_100g", nullable = true)
    private BigDecimal pricePer100g;

    @OneToMany(mappedBy = "consumedFood", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<ConsumedFoodQuantity> consumedFoodQuantities;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public Food(){}

    public Food(@NonNull String foodName, @NonNull BigDecimal kcalPer100g, @NonNull BigDecimal proteinsPer100g, BigDecimal carbsPer100g, BigDecimal fatsPer100g, BigDecimal saturatedFatsPer100g, BigDecimal badTransFatsPer100g, BigDecimal fiberPer100g, short onePortionSizeInGrams, Optional<Price> price) {
        this.foodName = foodName;
        this.kcalPer100g = kcalPer100g;
        this.proteinsPer100g = proteinsPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatsPer100g = fatsPer100g;
        this.saturatedFatsPer100g = saturatedFatsPer100g;
        this.badTransFatsPer100g = badTransFatsPer100g;
        this.fiberPer100g = fiberPer100g;
        this.onePortionSizeInGrams = onePortionSizeInGrams;
        price.ifPresent(pricePer100grams -> this.pricePer100g = pricePer100grams.getPricePer100grams());
    }

    public Food(Long foodId, @NonNull String foodName, @NonNull BigDecimal kcalPer100g, @NonNull BigDecimal proteinsPer100g, BigDecimal carbsPer100g, BigDecimal fatsPer100g, BigDecimal saturatedFatsPer100g, BigDecimal badTransFatsPer100g, BigDecimal fiberPer100g, short onePortionSizeInGrams, Optional<Price> price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.kcalPer100g = kcalPer100g;
        this.proteinsPer100g = proteinsPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatsPer100g = fatsPer100g;
        this.saturatedFatsPer100g = saturatedFatsPer100g;
        this.badTransFatsPer100g = badTransFatsPer100g;
        this.fiberPer100g = fiberPer100g;
        this.onePortionSizeInGrams = onePortionSizeInGrams;
        price.ifPresent(pricePer100grams -> this.pricePer100g = pricePer100grams.getPricePer100grams());
    }

    public Food(Long foodId, @NonNull String foodName, @NonNull BigDecimal kcalPer100g, @NonNull BigDecimal proteinsPer100g, BigDecimal carbsPer100g, BigDecimal fatsPer100g, short onePortionSizeInGrams, Optional<Price> price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.kcalPer100g = kcalPer100g;
        this.proteinsPer100g = proteinsPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatsPer100g = fatsPer100g;
        this.onePortionSizeInGrams = onePortionSizeInGrams;
        price.ifPresent(pricePer100grams -> this.pricePer100g = pricePer100grams.getPricePer100grams());
    }

    public Food(@NonNull String foodName, @NonNull BigDecimal kcalPer100g, @NonNull BigDecimal proteinsPer100g, BigDecimal carbsPer100g, BigDecimal fatsPer100g, short onePortionSizeInGrams, Optional<Price> price) {
        this.foodName = foodName;
        this.kcalPer100g = kcalPer100g;
        this.proteinsPer100g = proteinsPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatsPer100g = fatsPer100g;
        this.onePortionSizeInGrams = onePortionSizeInGrams;
        price.ifPresent(pricePer100grams -> this.pricePer100g = pricePer100grams.getPricePer100grams());
    }

    public Food(Long foodId, @NonNull String foodName, @NonNull BigDecimal kcalPer100g, @NonNull BigDecimal proteinsPer100g) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.kcalPer100g = kcalPer100g;
        this.proteinsPer100g = proteinsPer100g;
    }

    public Food(@NonNull String foodName, @NonNull BigDecimal kcalPer100g, @NonNull BigDecimal proteinsPer100g) {
        this.foodName = foodName;
        this.kcalPer100g = kcalPer100g;
        this.proteinsPer100g = proteinsPer100g;
    }

    @NonNull
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(@NonNull String foodName) {
        this.foodName = foodName;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    @NonNull
    public BigDecimal getKcalPer100g() {
        return kcalPer100g;
    }

    public void setKcalPer100g(@NonNull BigDecimal kcalPer100g) {
        this.kcalPer100g = kcalPer100g;
    }

    @NonNull
    public BigDecimal getProteinsPer100g() {
        return proteinsPer100g;
    }

    public void setProteinsPer100g(@NonNull BigDecimal proteinsPer100g) {
        this.proteinsPer100g = proteinsPer100g;
    }

    public BigDecimal getCarbsPer100g() {
        return carbsPer100g;
    }

    public void setCarbsPer100g(BigDecimal carbsPer100g) {
        this.carbsPer100g = carbsPer100g;
    }

    public BigDecimal getFatsPer100g() {
        return fatsPer100g;
    }

    public void setFatsPer100g(BigDecimal fatsPer100g) {
        this.fatsPer100g = fatsPer100g;
    }

    public BigDecimal getSaturatedFatsPer100g() {
        return saturatedFatsPer100g;
    }

    public void setSaturatedFatsPer100g(BigDecimal saturatedFatsPer100g) {
        this.saturatedFatsPer100g = saturatedFatsPer100g;
    }

    public BigDecimal getBadTransFatsPer100g() {
        return badTransFatsPer100g;
    }

    public void setBadTransFatsPer100g(BigDecimal badTransFatsPer100g) {
        this.badTransFatsPer100g = badTransFatsPer100g;
    }


    public BigDecimal getFiberPer100g() {
        return fiberPer100g;
    }

    public void setFiberPer100g(BigDecimal fiberPer100g) {
        this.fiberPer100g = fiberPer100g;
    }

    public short getOnePortionSizeInGrams() {
        return onePortionSizeInGrams;
    }

    public void setOnePortionSizeInGrams(short onePortionSizeInGrams) {
        this.onePortionSizeInGrams = onePortionSizeInGrams;
    }


    public BigDecimal getPricePer100g() {
        return pricePer100g;
    }

    public void setPricePer100g(BigDecimal pricePer100g) {
        this.pricePer100g = pricePer100g;
    }

    public Set<ConsumedFoodQuantity> getConsumedFoodQuantities() {
        return consumedFoodQuantities;
    }

    public void setConsumedFoodQuantities(Set<ConsumedFoodQuantity> consumedFoodQuantities) {
        this.consumedFoodQuantities = consumedFoodQuantities;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", kcalPer100g=" + kcalPer100g +
                ", proteinsPer100g=" + proteinsPer100g +
                ", carbsPer100g=" + carbsPer100g +
                ", fatsPer100g=" + fatsPer100g +
                ", saturatedFatsPer100g=" + saturatedFatsPer100g +
                ", badTransFatsPer100g=" + badTransFatsPer100g +
                ", fiberPer100g=" + fiberPer100g +
                ", onePortionSizeInGrams=" + onePortionSizeInGrams +
                ", pricePer100g=" + pricePer100g +
                '}';
    }



    public Food copyAllButNameAndId(@NonNull String newName){
        try {
            return new Food(
                    newName,
                    getKcalPer100g(),
                    getProteinsPer100g(),
                    getCarbsPer100g(),
                    getFatsPer100g(),
                    getSaturatedFatsPer100g(),
                    getBadTransFatsPer100g(),
                    getFiberPer100g(),
                    getOnePortionSizeInGrams(),
                    Optional.of(new Price(getPricePer100g(), ONE_HUNDRED)));
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
