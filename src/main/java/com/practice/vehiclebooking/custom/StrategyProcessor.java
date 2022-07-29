package com.practice.vehiclebooking.custom;



import com.practice.vehiclebooking.common.Constants;
import com.practice.vehiclebooking.entity.Vehicle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StrategyProcessor {

    private static Comparator getStrategy(String allocationStrategy){
        switch (allocationStrategy){
            case "MIN_PRICE_ALLOCATION_STRATEGY":
                return new MinPriceAllocationComparator();
            case "MAX_PRICE_ALLOCATION_STRATEGY":
                return new MaxPriceAllocationComparator();
            default:
                return new MinPriceAllocationComparator();
        }
    }

    public static void createOrdering(List<Vehicle> vehicles, String allocationStrategy){
        if(allocationStrategy==null)
            allocationStrategy = Constants.MIN_PRICE_ALLOCATION_STRATEGY;
        Collections.sort(vehicles,getStrategy(allocationStrategy));
    }
}
