package com.practice.vehiclebooking.orchestrator;

import com.practice.vehiclebooking.common.BranchList;
import com.practice.vehiclebooking.service.AssetManagementService;
import com.practice.vehiclebooking.entity.Branch;
import com.practice.vehiclebooking.entity.Vehicle;
import com.practice.vehiclebooking.utiltity.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;


import java.util.List;
import java.util.Map;


public class RequestDispatcher {


    private AssetManagementService assetManagement;

    public RequestDispatcher(){
        init();
    }


    public void init(){
        assetManagement = new AssetManagementService();
        Map<String, Branch> branchMap = BranchList.getInstance().getBranchMap();
    }

    public void identifyAndDispatchRequest(String input){
        String [] inputArgs = input.split(" ");
        if(inputArgs[0].equalsIgnoreCase("ADD_BRANCH")){

        }
        switch (inputArgs[0].toUpperCase()){
            case "ADD_BRANCH":
                addBranch(inputArgs);
                break;
            case "ADD_VEHICLE":
                addVehicle(inputArgs);
                break;
            case "DISPLAY_VEHICLES":
                displayVehicle(inputArgs);
                break;
            case "BOOK":
                book(inputArgs);
                break;
            default:
                System.out.println("Invalid querry");
                break;
        }
    }

    private void addBranch(String[] input){
        if(input.length!=3 || StringUtils.isEmpty(input[1]) || StringUtils.isEmpty(input[2])){
            System.out.println("Invalid Input");
            return;
        }

        String [] types = input[2].split(",");

        boolean result = assetManagement.addBranch(input[1],types);

        if(result)
            System.out.println("TRUE");
        else
            System.out.println("FALSE");
    }

    private void addVehicle(String [] input){
        if(input.length!=5 || StringUtils.isEmpty(input[4]) || StringUtils.isEmpty(input[1]) || StringUtils.isEmpty(input[2]) || StringUtils.isEmpty(input[3])){
            System.out.println("Invalid Input");
            return;
        }

        try{
            double price = ValidationUtils.isValidPrice(input[4]);
            boolean result = assetManagement.addVehicle(input[1],input[2],input[3], price);
            if(result)
                System.out.println("TRUE");
            else
                System.out.println("FALSE");
        } catch (Exception e){
            System.out.println("invalid price input");
            return;
        }

    }

    private void displayVehicle(String [] input){
        if(input.length!=4 || StringUtils.isEmpty(input[3]) || StringUtils.isEmpty(input[1]) || StringUtils.isEmpty(input[2])){
            System.out.println("Invalid Input");
            return;
        }

        try {
            Pair<Integer,Integer> bookingTime = ValidationUtils.isValidHr(input[2],input[3]);
            int stHr = bookingTime.getLeft();
            int enHr = bookingTime.getRight();
            List<Vehicle> vehicles = assetManagement.getAvailableVehicle( input[1], stHr, enHr-1);
            if(vehicles == null){
                System.out.println("No vehicle found for given branch and time slot");
                return;
            }
            for(int i=0;i<vehicles.size();i++){
                System.out.print(vehicles.get(i).getVehicleId());
                if(i!=vehicles.size()-1)
                    System.out.print(",");
            }
            System.out.println();
        }
        catch (Exception ex){
            System.out.println("Invalid input - "+ ex.getMessage());
        }
    }

    private void book(String [] input){

        if(input.length!=5 || StringUtils.isEmpty(input[3]) || StringUtils.isEmpty(input[1]) || StringUtils.isEmpty(input[2]) || StringUtils.isEmpty(input[4])){
            System.out.println("Invalid Input");
            return;
        }

        try{

            Pair<Integer,Integer> bookingTime = ValidationUtils.isValidHr(input[3],input[4]);
            int stHr = bookingTime.getLeft();
            int enHr = bookingTime.getRight();
            double price = assetManagement.bookVehicle(input[1], input[2], stHr, enHr-1);
            System.out.println(Math.round(price));

        } catch (Exception e){
            System.out.println("invalid input - "+ e.getMessage());
            return;
        }

    }


}
