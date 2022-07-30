package com.practice.vehiclebooking.service;

import com.practice.vehiclebooking.common.BranchList;
import com.practice.vehiclebooking.common.Constants;
import com.practice.vehiclebooking.custom.StrategyProcessor;
import com.practice.vehiclebooking.entity.Branch;
import com.practice.vehiclebooking.entity.Vehicle;
import com.practice.vehiclebooking.entity.VehicleType;
import com.practice.vehiclebooking.utiltity.Utility;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;


@Data
public class AssetBookingService {

    public List<Vehicle> getAvailableVehicle(String branchId, Integer startHr, Integer endHr){
        long reqSlot = Utility.getRequiredSlotsMask(startHr,endHr);

        Branch branch = BranchList.getInstance().getBranchMap().get(branchId);

        if(branch == null){
            return  null;
        }

        List<Vehicle> availableVehicles = new ArrayList<>();

        for(VehicleType vType : branch.getAvailableTypes()){
            String stype = vType.getType();
            List<Vehicle> curVehicles = branch.getInventoryList().get(stype);
            if(curVehicles==null){
                continue;
            }
            for(Vehicle vehicle : curVehicles){
                if(Utility.isCompatibleSlot(reqSlot,vehicle.getSlots())){
                    availableVehicles.add(vehicle);
                }
            }
        }

        // Sorts the available vehicle according to the vehicle allocation strategy.
        StrategyProcessor.createOrdering(availableVehicles, Constants.MIN_PRICE_ALLOCATION_STRATEGY);

        return availableVehicles;

    }

    public Double bookVehicle(String branchId, String vehicleType, Integer startHr, Integer endHr){
        long reqSlot = Utility.getRequiredSlotsMask(startHr,endHr);

        Branch branch = BranchList.getInstance().getBranchMap().get(branchId);

        if(branch == null) {
            return Constants.ERROR_PRICE;
        }

        List<Vehicle> vehicles = branch.getInventoryList().get(vehicleType);

        if(vehicles==null)
            return Constants.ERROR_PRICE;

        // total size of vehicles of particular requested type
        int total = vehicles.size();

        List<Vehicle> availableVehicles = new ArrayList<>();

        for(Vehicle vehicle : vehicles){
            if(Utility.isCompatibleSlot(reqSlot,vehicle.getSlots())){
                availableVehicles.add(vehicle);
            }
        }

        // total size of available Vehicles according to slot
        int availableTotal = availableVehicles.size();

        // to check for dynamic pricing
        boolean toIncreasePrice = (availableTotal<=Constants.SURGE_PRICING_THRESHOLD*total)?true:false;

        if(availableVehicles.size()==0)
            return Constants.ERROR_PRICE;

       StrategyProcessor.createOrdering(availableVehicles,Constants.MIN_PRICE_ALLOCATION_STRATEGY);

        Vehicle bookedVehicle = availableVehicles.get(0);

        bookedVehicle.setSlots(bookedVehicle.getSlots()|reqSlot);

        if(toIncreasePrice){
            return (endHr-startHr+1)*bookedVehicle.getPricePerHr()*Constants.SURGE_PRICE_MULTIPLIER;
        }

        return (endHr-startHr+1)*bookedVehicle.getPricePerHr();

    }

}
