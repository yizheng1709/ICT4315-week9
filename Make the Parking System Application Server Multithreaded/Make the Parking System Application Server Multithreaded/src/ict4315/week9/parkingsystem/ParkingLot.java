/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 
 */
public final class ParkingLot implements Serializable {

    private final String id;
    private final String name;
    private final Address address;
    private String strategy;
    private long dailyRate = 10;
    private ParkingObserver parkingObserver;
    private ParkingChargeCalculatorFactory calculatorFactory;
    
    public ParkingLot(String id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ParkingLot(String id, String name, Address address, String strategy, ParkingChargeCalculatorFactory calculatorFactory) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.strategy = strategy;
        this.calculatorFactory = calculatorFactory;
    }

    public ParkingLot(String id, String name, Address address, ParkingChargeCalculatorFactory calculatorFactory) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.calculatorFactory = calculatorFactory;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getDiscountStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Money getFixedDailyRate() {
        return new Money(dailyRate);
    }

    public Money getParkingCharge(ParkingEvent event) {
            ParkingChargeCalculator calc = calculatorFactory.getParkingChargeCalculator(this.strategy);
            Double totalDiscountRate = calc.getDiscount(event);
            long parkingCharge = (long) (this.getFixedDailyRate().getAmount() * (1 - totalDiscountRate));
            return new Money(parkingCharge);
    }

    public void setParkingObserver(ParkingObserver parkingObserver) {
        this.parkingObserver = parkingObserver;
    }

    public void notify(ParkingEvent event) {
        System.out.println(String.format("Parking lot %s notifying", this.name));
        parkingObserver.onParkingEventReceived(event);
    }

}
