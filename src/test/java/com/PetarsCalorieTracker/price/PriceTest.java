package com.PetarsCalorieTracker.price;


import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class PriceTest {


    @Test
    public void testThatPrice0is0(){
        var price = new Price(BigDecimal.ZERO);
        assertEquals(
                BigDecimal.ZERO.setScale(0, BigDecimal.ROUND_HALF_UP),
                price.getPricePer100grams().setScale(0, BigDecimal.ROUND_HALF_UP)
        );
    }

    @Test
    public void testThatPrice151is151(){
        var price = new Price(new BigDecimal(51));
        assertEquals(
                new BigDecimal(51).setScale(0, BigDecimal.ROUND_HALF_UP),
                price.getPricePer100grams().setScale(0, BigDecimal.ROUND_HALF_UP)
        );
    }

    @Test
    public void testThatPrice50For2gramsIs2500per100grams(){
        var price = Price.zero();
        try{
            price = new Price(new BigDecimal(50), new BigDecimal(2));
        } catch(Exception exception){
            exception.printStackTrace();
        }

        assertEquals(
                new BigDecimal(2500).setScale(0, BigDecimal.ROUND_HALF_UP),
                price.getPricePer100grams().setScale(0, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testThatPrice10For25Is40Per100g(){
        var price = Price.zero();
        try{
            price = new Price(new BigDecimal(10), new BigDecimal(25));
        } catch(Exception exception){
            exception.printStackTrace();
        }

        assertEquals(
                new BigDecimal(40).setScale(0, BigDecimal.ROUND_HALF_UP),
                price.getPricePer100grams().setScale(0, BigDecimal.ROUND_HALF_UP)
        );
    }

    @Test
    public void testThatPrice100For300Is33Point33Per100grams(){
        var price = Price.zero();
        try{
            price = new Price(new BigDecimal(100), new BigDecimal(300));
        } catch(Exception exception){
            exception.printStackTrace();
        }

        assertEquals(
                new BigDecimal(33.33).setScale(2, BigDecimal.ROUND_HALF_UP),
                price.getPricePer100grams().setScale(2, BigDecimal.ROUND_HALF_UP)
        );
    }

    @Test
    public void testThatPrice20For50000gramsIs0Point4Per100grams(){
        var price = Price.zero();
        try{
            price = new Price(new BigDecimal(20), new BigDecimal(50000));
        } catch(Exception exception){
            exception.printStackTrace();
        }

        assertEquals(
                new BigDecimal(0.04).setScale(2, BigDecimal.ROUND_HALF_UP),
                price.getPricePer100grams().setScale(2, BigDecimal.ROUND_HALF_UP)
        );
    }
}
