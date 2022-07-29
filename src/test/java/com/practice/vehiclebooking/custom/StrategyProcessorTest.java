package com.practice.vehiclebooking.custom;

import com.practice.vehiclebooking.common.Constants;
import com.practice.vehiclebooking.entity.Vehicle;
import com.practice.vehiclebooking.entity.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StrategyProcessorTest {

    static List<Vehicle> vehicleList ;


    @BeforeAll
    public static void setup(){
        Vehicle vehicle1 = new Vehicle(VehicleType.BIKE,10.0,"v1",0l);
        Vehicle vehicle2 = new Vehicle(VehicleType.BIKE,20.0,"v2",0l);
        Vehicle vehicle3 = new Vehicle(VehicleType.BIKE,15.0,"v3",0l);
        vehicleList = new ArrayList<>();
        vehicleList.add(vehicle1);
        vehicleList.add(vehicle2);
        vehicleList.add(vehicle3);
    }

    @Test
    public void testMinPriceStrategy(){
        StrategyProcessor.createOrdering(vehicleList, Constants.MIN_PRICE_ALLOCATION_STRATEGY);
        Assertions.assertEquals("v1",vehicleList.get(0).getVehicleId());
        Assertions.assertEquals("v3",vehicleList.get(1).getVehicleId());
        Assertions.assertEquals("v2",vehicleList.get(2).getVehicleId());
    }

    @Test
    public void testMaxPriceStrategy(){
        StrategyProcessor.createOrdering(vehicleList, Constants.MAX_PRICE_ALLOCATION_STRATEGY);
        Assertions.assertEquals("v2",vehicleList.get(0).getVehicleId());
        Assertions.assertEquals("v3",vehicleList.get(1).getVehicleId());
        Assertions.assertEquals("v1",vehicleList.get(2).getVehicleId());
    }

    @Test
    public void testDefaultPriceStrategy(){
        StrategyProcessor.createOrdering(vehicleList, null);
        Assertions.assertEquals("v1",vehicleList.get(0).getVehicleId());
        Assertions.assertEquals("v3",vehicleList.get(1).getVehicleId());
        Assertions.assertEquals("v2",vehicleList.get(2).getVehicleId());
    }
}
