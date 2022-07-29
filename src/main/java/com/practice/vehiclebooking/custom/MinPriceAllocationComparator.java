package com.practice.vehiclebooking.custom;
import com.practice.vehiclebooking.entity.Vehicle;

import java.util.Comparator;

public class MinPriceAllocationComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        if(o1.getPricePerHr()<o2.getPricePerHr())
            return -1;
        else return 1;
    }
}
