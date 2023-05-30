/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import java.time.LocalDateTime;

/**
 *
 * 
 */
public interface DiscountStrategy {
    String getStrategyName();
    Double getDiscount(CarType carType, LocalDateTime dayOfTheWeek);
    void setDiscount(Double percentage);
    Money getParkingCharge(ParkingLot parkingLot, CarType carType, LocalDateTime date);
}
