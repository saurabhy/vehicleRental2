package com.practice.vehiclebooking.utility;

import com.practice.vehiclebooking.utiltity.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UtilityTest {

    @Test
    public void testGetMask(){
        int st = 5;
        int en = 10;

        long ret = Utility.getRequiredSlotsMask(st,en);
        Assertions.assertEquals(1008,ret);

    }

    @Test
    public void testCheckAvailable(){

        int st = 7;
        int en = 13;
        long val = 1049584;
        long slot = Utility.getRequiredSlotsMask(st,en);
        boolean check = Utility.isCompatibleSlot(slot,val);
        Assertions.assertEquals(false,check);
        st = 11;
        en = 15;
        slot = Utility.getRequiredSlotsMask(st,en);
        check = Utility.isCompatibleSlot(val,slot);
        Assertions.assertEquals(true,check);
    }

}
