/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import ict4315.parkingsystem.Car;
import ict4315.parkingsystem.ParkingPermit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 */
public class PermitManager {

    private List<ParkingPermit> parkingPermits;

    public PermitManager() {
        this.parkingPermits = new ArrayList<>();
    }

    public PermitManager(List<ParkingPermit> parkingpermits) {
        this.parkingPermits = parkingpermits;
    }

    public ParkingPermit getParkingPermit(String id) throws Exception {

        System.out.println("eGuyen input" + id);

        for (int i = 0; i < parkingPermits.size(); i++) {
            System.out.println("Nguyen compare " + parkingPermits.get(i).getId());
            if (parkingPermits.get(i).getId().equals(id)) {
                return parkingPermits.get(i);
            }
        }
        System.out.println("Nguyen id not exist");
        return null;
    }

    public int getNumberOfParkingPermitIssued() {
        return parkingPermits.size();
    }

    public ParkingPermit register(Car car) {
        ParkingPermit parkingPermit = new ParkingPermit(car.getLicensePlate(), car);
        parkingPermits.add(parkingPermit);
        return parkingPermit;
    }

}
