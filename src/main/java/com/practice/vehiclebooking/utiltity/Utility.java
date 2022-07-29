package com.practice.vehiclebooking.utiltity;

public class Utility {

    public static long getRequiredSlotsMask(Integer startHr ,Integer endHr){
        long slot = 0l;
        for(int i=startHr-1;i<endHr;i++){
            slot|=(long)(Math.pow(2,i));
        }
        return slot;
    }

    public static boolean isCompatibleSlot(long reqSlot, long curSlot){
        return (reqSlot & curSlot) == 0;
    }
}
