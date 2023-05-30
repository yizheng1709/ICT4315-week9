/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * 
 */

public final class ParkingPermit implements Serializable {
    private final String id;
    private final Car car;
    private final LocalDateTime expiration;

    //Fixme:this is a temporary method to test ParkingGUI
    public ParkingPermit(String liecensePlate, Car car) {
        this.id = generateId(liecensePlate);
        this.car = car;
        this.expiration = LocalDateTime.now().plusYears(1);
    }
 
    //Fixme:this is a temporary method to test ParkingGUI
    public String generateId(String licensePlate){
        return "PRM" + licensePlate;
    }

    public Car getCar() {
        return car;
    }
    
    public String getId() {
        return id;
    }
    
    public boolean isExpired(){
        if (LocalDateTime.now().isAfter(expiration)){
            return true;
        }
        return false;
    }
    
}

