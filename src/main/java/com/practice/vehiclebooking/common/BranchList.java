package com.practice.vehiclebooking.common;

import com.practice.vehiclebooking.entity.Branch;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BranchList {

    // This is a Map with Branch Name as key and Branch class as Value
    private Map<String, Branch> branchMap;

    private static BranchList branchList = null;

    private BranchList(){
        branchMap = new HashMap<>();
    }

    public static BranchList getInstance(){
        if(branchList == null){
            branchList = new BranchList();
        }
        return branchList;
    }
}
