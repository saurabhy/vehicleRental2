package com.practice.vehiclebooking.service;

import com.practice.vehiclebooking.common.BranchList;

import com.practice.vehiclebooking.entity.Branch;
import com.practice.vehiclebooking.entity.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;




public class AssetManagementTest {

    static Map<String,Branch> branchList;


    static AssetBookingService assetBookingService;
    static AssetOnboardingService assetOnboardingService;


    @BeforeAll
    public static void setup(){
        branchList = BranchList.getInstance().getBranchMap();
        assetBookingService = new AssetBookingService();
        assetOnboardingService = new AssetOnboardingService();
    }

    @Test
    public void addBranch(){
        String [] types = new String[]{"CAR","BIKE"};
        assetOnboardingService.addBranch("B1",types);
        Assertions.assertNotNull(branchList.get("B1"));
    }

    @Test
    public void addVehicle(){
        String [] types = new String[]{"CAR","BIKE"};
        assetOnboardingService.addBranch("B1",types);
        assetOnboardingService.addVehicle("B1","BIKE","v1",100.0);
        Assertions.assertNotNull(branchList.get("B1"));
        Assertions.assertNotNull(branchList.get("B1").getInventoryList().get("BIKE").get(0));
    }

    @Test
    public void availableVehicleTest(){
        String [] types = new String[]{"CAR","BIKE"};
        assetOnboardingService.addBranch("B1",types);
        assetOnboardingService.addVehicle("B1","BIKE","v1",100.0);
        assetOnboardingService.addVehicle("B1","BIKE","v2",200.0);
        List<Vehicle> vehicles = branchList.get("B1").getInventoryList().get("BIKE");
        //doNothing().when(strategyProcessor).createOrdering(vehicles, Constants.MIN_PRICE_ALLOCATION_STRATEGY);
        List<Vehicle> result = assetBookingService.getAvailableVehicle("B1",1,5);
        Assertions.assertNotNull(result);
    }

    @Test
    public void bookAvailableVehicle(){
        String [] types = new String[]{"CAR","BIKE"};
        assetOnboardingService.addBranch("B1",types);
        assetOnboardingService.addVehicle("B1","BIKE","v1",100.0);
        assetOnboardingService.addVehicle("B1","BIKE","v2",200.0);
        List<Vehicle> vehicles = branchList.get("B1").getInventoryList().get("BIKE");
        //doNothing().when(strategyProcessor).createOrdering(vehicles, Constants.MIN_PRICE_ALLOCATION_STRATEGY);
        double p1 = assetBookingService.bookVehicle("B1","BIKE",1,5);
        double p2 = assetBookingService.bookVehicle("B1","BIKE",1,4);
        double p3 = assetBookingService.bookVehicle("B1","BIKE",1,4);
        Assertions.assertNotEquals(-1.0,p1);
        Assertions.assertNotEquals(-1.0,p2);
        Assertions.assertEquals(-1.0,p3);
    }

}
