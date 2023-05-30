/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import ict4315.parkingsystem.Money;
import ict4315.parkingsystem.ParkingLot;
import ict4315.parkingsystem.ParkingPermit;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * 
 */
public final class ParkingTransaction implements Serializable {

    private final LocalDateTime date;
    private final ParkingPermit permit;
    private final ParkingLot parkingLot;
    private final Money chargedAmount;

    public ParkingTransaction(LocalDateTime date, ParkingPermit permit, ParkingLot parkingLot, Money chargedAmount) {
        this.date = date;
        this.permit = permit;
        this.parkingLot = parkingLot;
        this.chargedAmount = chargedAmount;
    }
    
    public ParkingPermit getPermit() {
        return permit;
    }

    public Money getChargedAmount() {
        return chargedAmount;
    }
    
    public LocalDateTime getDate(){
        return date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParkingTransaction other = (ParkingTransaction) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.permit, other.permit)) {
            return false;
        }
        if (!Objects.equals(this.parkingLot, other.parkingLot)) {
            return false;
        }
        if (!Objects.equals(this.chargedAmount, other.chargedAmount)) {
            return false;
        }
        return true;
    }

}
