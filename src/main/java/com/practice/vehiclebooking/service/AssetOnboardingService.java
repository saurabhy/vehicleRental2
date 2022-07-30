package com.practice.vehiclebooking.service;

import com.practice.vehiclebooking.common.BranchList;
import com.practice.vehiclebooking.entity.Branch;
import com.practice.vehiclebooking.entity.Vehicle;
import com.practice.vehiclebooking.entity.VehicleType;
import lombok.Data;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.ArrayList;
import java.util.Map;

@Data
public class AssetOnboardingService {

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
}
