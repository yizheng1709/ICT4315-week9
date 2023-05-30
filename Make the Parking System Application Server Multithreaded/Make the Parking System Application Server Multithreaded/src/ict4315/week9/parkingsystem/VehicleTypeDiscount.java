/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import ict4315.parkingsystem.CarType;
import ict4315.parkingsystem.ParkingEvent;
import java.io.Serializable;

/**
 *
 * 
 */
public class VehicleTypeDiscount extends ParkingChargeDecorator{
    
    private Double discountRate = 0.1;

    public VehicleTypeDiscount(ParkingChargeCalculator calc) {
        super(calc);
    }

    @Override
     public Double getDiscount(ParkingEvent parkingEvent){
         if (isCompactCar(parkingEvent.getPermit().getCar().getType())){
             System.out.println("Compact car discount rate applied");
             return super.getDiscount(parkingEvent) + discountRate;
         }
         System.out.println("Vehicle type discount cannot be applied to this vehicle");
        return super.getDiscount(parkingEvent);
     }

    private boolean isCompactCar(CarType carType){
        return carType == CarType.COMPACT;
    }
    
}
