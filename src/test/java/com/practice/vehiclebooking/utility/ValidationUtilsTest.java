package com.practice.vehiclebooking.utility;

import com.practice.vehiclebooking.utiltity.ValidationUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ValidationUtilsTest {

    static String INVALID_TIME = "Invalid time input";
    static String INVALID_PRICE = "Invalid price input";

    @Test
    public void testIsValidPrice(){
        try{
            double p1 = ValidationUtils.isValidPrice("-1");
        }
        catch (Exception ex){
            Assertions.assertEquals(INVALID_PRICE,ex.getMessage());
        }

        try{
            double p1 = ValidationUtils.isValidPrice(null);
        }
        catch (Exception ex){
            Assertions.assertEquals(INVALID_PRICE,ex.getMessage());
        }

        try{
            double p1 = ValidationUtils.isValidPrice("1");
            Assertions.assertEquals(1.0,p1);
        }
        catch (Exception ex){
            Assertions.assertEquals(INVALID_PRICE,ex.getMessage());
        }

    }

    @Test
    public void testIsValidTime(){

        try{
            Pair<Integer,Integer> bookingTime = ValidationUtils.isValidHr("-1","1");
        }
        catch (Exception ex){
            Assertions.assertEquals(INVALID_TIME,ex.getMessage());
        }

        try{
            Pair<Integer,Integer> bookingTime = ValidationUtils.isValidHr("3","1");
        }
        catch (Exception ex){
            Assertions.assertEquals(INVALID_TIME,ex.getMessage());
        }

        try{
            Pair<Integer,Integer> bookingTime = ValidationUtils.isValidHr("1","5");
            Assertions.assertEquals(Optional.of(1), Optional.ofNullable(bookingTime.getLeft()));
            Assertions.assertEquals(Optional.of(5), Optional.ofNullable(bookingTime.getRight()));
        }
        catch (Exception ex){
            Assertions.assertEquals(INVALID_TIME,ex.getMessage());
        }
    }
}
