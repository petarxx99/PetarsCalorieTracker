package com.PetarsCalorieTracker.price;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

public class Price {
    private static final int SCALE = 5;

    @NonNull
    private final BigDecimal price;

    @NonNull
    private final BigDecimal quantityInGrams;

    private Optional<BigDecimal> pricePer100gOptional = Optional.empty();

    public static Price zero(){
        try{
            return new Price(BigDecimal.ZERO, BigDecimal.ONE);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public Price(@NonNull BigDecimal price, @NonNull BigDecimal quantityInGrams) throws Exception{
        if (quantityInGrams.equals(BigDecimal.ZERO)){
            throw new Exception("The number of grams for calculating price must not be zero.");
        }
        this.price = price;
        this.quantityInGrams = quantityInGrams;
    }

    public Price(@NonNull BigDecimal pricePer100g){
        this.pricePer100gOptional = Optional.of(pricePer100g);
        price = pricePer100g;
        quantityInGrams = BigDecimal.ONE;
    }

    public BigDecimal getPricePer100grams(){
        if (pricePer100gOptional.isEmpty()){
            pricePer100gOptional = Optional.of(price.multiply(new BigDecimal(100)).divide(quantityInGrams, SCALE, BigDecimal.ROUND_HALF_UP));
        }
        return pricePer100gOptional.get();
    }

    @Override
    public boolean equals(Object otherPriceAsObject){
        try{
            assert (otherPriceAsObject != null);
            Price otherPrice = (Price) otherPriceAsObject;

            return getPricePer100grams().equals(otherPrice.getPricePer100grams());
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
