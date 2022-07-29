package com.practice.vehiclebooking.service;

import com.practice.vehiclebooking.common.BranchList;
import com.practice.vehiclebooking.common.Constants;
import com.practice.vehiclebooking.custom.StrategyProcessor;
import com.practice.vehiclebooking.entity.Branch;
import com.practice.vehiclebooking.entity.Vehicle;
import com.practice.vehiclebooking.entity.VehicleType;
import com.practice.vehiclebooking.utiltity.Utility;
import lombok.Data;
import org.yaml.snakeyaml.util.EnumUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
public class AssetManagementService {

    public boolean addBranch(String branchId, String[] types){
        Map<String, Branch> branchMap = BranchList.getInstance().getBranchMap();

        // Check if already branch is registered.
        if(!branchMap.containsKey(branchId)){
            branchMap.put(branchId,new Branch(branchId));
        }
        else{
            return false;
        }

        for(String type : types){
            try{
                // Check if the vehicle type input is one of the available vehicle types.
                EnumUtils.findEnumInsensitiveCase(VehicleType.class,type);
                branchMap.get(branchId).getAvailableTypes().add(VehicleType.valueOf(type));
            }
            catch (Exception ex){
                branchMap.remove(branchId);
                return false;
            }
        }

        return true;
    }

    public boolean addVehicle(String branchId, String vehicleType, String vehicleId, Double price){
        Branch branch = BranchList.getInstance().getBranchMap().get(branchId);
        if(branch==null){
            return false;
        }

        String vehicleHash = vehicleType+"_"+vehicleId;

        // Fast check for whether that vehicle is already present or not
        if(branch.getVehicleHash().contains(vehicleHash)){
            return false;
        }

        boolean typeFound = false;

        for(VehicleType vType : branch.getAvailableTypes()){
            if(vehicleType.equalsIgnoreCase(vType.getType())){
                typeFound = true;
                break;
            }
        }

        // If this vehicle type is not available in the branch.
        if(!typeFound){
            return false;
        }

        if(!branch.getInventoryList().containsKey(vehicleType)){
            branch.getInventoryList().put(vehicleType.toUpperCase(),new ArrayList<>());
        }

        // Set the slot mask to 0.
        Vehicle curVehicle = new Vehicle(VehicleType.valueOf(vehicleType.toUpperCase()),price,vehicleId,0l);

        branch.getInventoryList().get(vehicleType.toUpperCase()).add(curVehicle);
        branch.getVehicleHash().add(vehicleHash);
        return true;
    }

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
