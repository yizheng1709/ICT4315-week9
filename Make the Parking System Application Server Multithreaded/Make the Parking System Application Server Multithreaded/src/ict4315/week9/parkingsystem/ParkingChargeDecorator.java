/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import ict4315.parkingsystem.ParkingEvent;
import java.io.Serializable;

/**
 *
 * 
 */
public abstract class ParkingChargeDecorator implements ParkingChargeCalculator, Serializable {

    private final ParkingChargeCalculator calc;

    public ParkingChargeDecorator(ParkingChargeCalculator calc) {
        this.calc = calc;

    }
    
    @Override
     public Double getDiscount(ParkingEvent parkingEvent){
         
        return calc.getDiscount(parkingEvent);
         
     }

}
