package com.practice.vehiclebooking.utiltity;

import com.practice.vehiclebooking.common.Constants;
import com.practice.vehiclebooking.exception.CustomException;
import org.apache.commons.lang3.tuple.Pair;

public class ValidationUtils {

    public static double isValidPrice(String s){
        double price = Constants.ERROR_PRICE;

        try{
            price = Double.valueOf(s);
        } catch (Exception e){
            throw new CustomException("Invalid price input");
        }

        if(price == Constants.ERROR_PRICE)
            throw new CustomException("Invalid price input");
        return price;
    }

    public static Pair<Integer,Integer> isValidHr(String st, String en){

        int stHr = -1;
        int enHr = -1;

        try{
            stHr = Integer.parseInt(st);
            enHr = Integer.parseInt(en);
        } catch (Exception e){
            throw new CustomException("Invalid time input");

        }

        if(stHr>=enHr || stHr < 1){
            throw new CustomException("Invalid time input");
        }

        return Pair.of(stHr,enHr);

    }
}
