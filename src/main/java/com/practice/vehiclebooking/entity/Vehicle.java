package com.practice.vehiclebooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    // Vehicle Type - category
    private VehicleType vehicleType;

    private Double pricePerHr;

    // vehicle name
    private String vehicleId;

    /*
      Assumption given in qs includes to make the vehicle book for 24 hrs , in hourly slots for 1 day.
      Hence A Slot Mask is used with 24 Bits Integer. Each bit describes the hr. (Eg: 1st bit represents 1st hr , 24th bit represnts 24th hr
      If a bit is set that means the vehicle is booked for that hr already.
     */
    private Long slots;
}
