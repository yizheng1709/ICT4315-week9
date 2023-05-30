/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import ict4315.parkingsystem.Customer;
import ict4315.parkingsystem.Money;
import ict4315.parkingsystem.ParkingLot;
import ict4315.parkingsystem.ParkingPermit;
import ict4315.parkingsystem.ParkingTransaction;
import ict4315.parkingsystem.DiscountStrategy;
import ict4315.parkingsystem.ParkingEvent;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import ict4315.parkingsystem.ParkingObserver;

/**
 *
 * 
 */
public class TransactionManager implements ParkingObserver {

    private List<ParkingTransaction> transactions = new ArrayList<>();

    public TransactionManager() {
    }

    @Override
    public void onParkingEventReceived(ParkingEvent event) {
        this.park(event);
    }

    public ParkingTransaction park(ParkingEvent event) {
        Money chargeAmount = event.getParkingLot().getParkingCharge(event);
        ParkingTransaction transaction;
        if (event.getTimeOut() == null) {
            transaction = new ParkingTransaction(event.getTimeIn(), event.getPermit(), event.getParkingLot(), chargeAmount);
        } else {
            transaction = new ParkingTransaction(event.getTimeOut(), event.getPermit(), event.getParkingLot(), chargeAmount);
        }
        System.out.println(String.format("Transaction %s for %s car at parkingLot %s created", transaction.getChargedAmount(), event.getPermit().getCar().getType(), event.getParkingLot().getName()));
        return transaction;

    }

    // Method calculate amount charged and create new parking transaction
    public ParkingTransaction park(LocalDateTime date, ParkingPermit parkingPermit, ParkingLot parkingLot) {
        Money chargedAmount;
        chargedAmount = parkingLot.getFixedDailyRate(); // 10 USD
        ParkingTransaction transaction = new ParkingTransaction(date, parkingPermit, parkingLot, chargedAmount);
        transactions.add(transaction);
        System.out.println("New transaction created");
        return transaction;
    }

    //calculate total parking charges for a parkingpermit
    public Money getParkingCharges(ParkingPermit parkingPermit) {
        //Get all parking transactions of which date is within the current month of current year
        List<ParkingTransaction> currentMonthlyTransactions = new ArrayList<>();
        Month currentMonth = LocalDateTime.now().getMonth();
        int currentYear = LocalDateTime.now().getYear();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getDate().getMonth().equals(currentMonth)
                    && transactions.get(i).getDate().getYear() == currentYear) {
                currentMonthlyTransactions.add(transactions.get(i));
            }
        }
        long sumCharges = 0;
        for (int i = 0; i < currentMonthlyTransactions.size(); i++) {
            if (currentMonthlyTransactions.get(i).getPermit().equals(parkingPermit)) {
                Money charge = currentMonthlyTransactions.get(i).getChargedAmount();
                sumCharges += charge.getAmount();
            }
        }

        Money total = new Money(sumCharges);
        return total;
    }

    public Money getParkingCharges(Customer customer) {
        //Get all parking transactions of which date is within the current month of current year
        List<ParkingTransaction> currentMonthlyTransactions = new ArrayList<>();
        Month currentMonth = LocalDateTime.now().getMonth();
        int currentYear = LocalDateTime.now().getYear();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getDate().getMonth().equals(currentMonth)
                    && transactions.get(i).getDate().getYear() == currentYear) {
                currentMonthlyTransactions.add(transactions.get(i));
            }
        }
        long sumCharges = 0;
        for (int i = 0; i < currentMonthlyTransactions.size(); i++) {
            if (currentMonthlyTransactions.get(i).getPermit().getCar().getOwner().equals(customer)) {
                Money charge = currentMonthlyTransactions.get(i).getChargedAmount();
                sumCharges += charge.getAmount();
            }
        }
        Money total = new Money(sumCharges);
        return total;
    }

}
