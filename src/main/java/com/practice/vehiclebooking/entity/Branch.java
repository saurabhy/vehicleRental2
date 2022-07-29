package com.practice.vehiclebooking.entity;

import lombok.Data;

import java.util.*;

@Data
public class Branch {

    // List containing available Vehicle types for this class
    private List<VehicleType> availableTypes;

    // Map containing Vehicle Category as Key and List containing vehicles for that category as value.
    private Map<String,List<Vehicle>> inventoryList;

    private Set<String> vehicleHash;

    // Branch Name
    private String branchId;

    public Branch(String bid){
        branchId = bid;
        availableTypes = new ArrayList<>();
        inventoryList = new HashMap<>();
        vehicleHash = new HashSet<>();
    }
}
