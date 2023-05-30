/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 
 */
public class ParkingOffice {

    private final String parkingOfficeName;
    private List<Customer> listOfCustomers;
    private List<ParkingLot> listOfParkingLots;
    private final Address parkingOfficeAddress;
    private final TransactionManager transactionManager;
    private final PermitManager permitManager;

    public ParkingOffice(String parkingOfficeName, Address parkingOfficeAddress, TransactionManager transactionManager, PermitManager permitManager) {
        this.parkingOfficeName = parkingOfficeName;
        this.parkingOfficeAddress = parkingOfficeAddress;
        this.listOfCustomers = new ArrayList<>();
        this.listOfParkingLots = new ArrayList<>();
        this.transactionManager = transactionManager;
        this.permitManager = permitManager;
    }

    public List<Customer> getListOfCustomer() {
        List<Customer> copy = new ArrayList<>(listOfCustomers);
        return copy;
    }

    public List<ParkingLot> getListOfParkingLot() {
        List<ParkingLot> copy = new ArrayList<>(listOfParkingLots);
        return copy;
    }

    public Address getParkingOfficeAddress() {
        return parkingOfficeAddress;
    }

    public String getParkingOfficeName() {
        return parkingOfficeName;
    }

//    public String register(Car car) throws Exception {
//
//        return permitManager.register(car).getId();
//    }
    //Fixme:this is a temporary method to test ParkingGUI
    public ParkingPermit register(CarType carType, String licensePlate, String customerId) throws Exception {
        Customer owner = this.getCustomer(customerId);
        Car car = new Car(carType, licensePlate, owner);
        return permitManager.register(car);
    }

    //Fixme: this is a temporary method to test ParkingGUI
    public Customer register(String firstName, String lastName, String phoneNumber, Address address) throws Exception {
        for (int i = 0; i < listOfCustomers.size(); i++) {
            if (listOfCustomers.get(i).getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                return listOfCustomers.get(i);
            }
        }
        Customer customer = new Customer(firstName, lastName, phoneNumber, address);
        listOfCustomers.add(customer);
        return customer;
    }

//    public String register(Customer customer) throws Exception {
//
//        if (!listOfCustomers.contains(customer)) {
//            listOfCustomers.add(customer);
//            return String.valueOf(customer.getId());
//        }
//        return "";
//    }
    //Add new ParkingLot with Office as ParkingObserver
    public void addParkingLot(ParkingLot parkingLot) throws Exception {
        if (!listOfParkingLots.contains(parkingLot)) {
            listOfParkingLots.add(parkingLot);
            System.out.println(String.format("Parking lot %s added", parkingLot.getName()));
        } else {
            throw new Exception("Parking lot already added");
        }
    }

    public ParkingTransaction park(ParkingEvent event) {
        return transactionManager.park(event);
    }

    public ParkingTransaction park(LocalDateTime date, ParkingPermit parkingPermit, ParkingLot parkingLot) {
        return transactionManager.park(date, parkingPermit, parkingLot);
    }

    public Money getParkingCharges(ParkingPermit parkingPermit) {
        return transactionManager.getParkingCharges(parkingPermit);
    }

    public Money getParkingCharges(Customer customer) {
        return transactionManager.getParkingCharges(customer);
    }

    public Customer getCustomer(String id) throws Exception {
        for (int i = 0; i < listOfCustomers.size(); i++) {
            if (listOfCustomers.get(i).getId().equals(id)) {
                return listOfCustomers.get(i);
            } else {
                throw new Exception("Customer id is invalid");
            }
        }
        return null;
    }

    public Customer getCustomerWithPhoneNumber(String phoneNumber) throws Exception {
        for (int i = 0; i < listOfCustomers.size(); i++) {
            if (phoneNumber.equals(listOfCustomers.get(i).getId())) {
                return listOfCustomers.get(i);
            } else {
                throw new Exception("Customer id is invalid");
            }
        }
        return null;
    }

    public ParkingPermit getParkingPermit(String id) throws Exception {

        return permitManager.getParkingPermit(id);
    }

    public int getNumberOfParkingPermitIssued() {
        return permitManager.getNumberOfParkingPermitIssued();
    }

    public ParkingLot getParkingLot(String id) throws Exception {

        for (int i = 0; i < listOfParkingLots.size(); i++) {
            if (listOfParkingLots.get(i).getId().equals(id)) {
                return listOfParkingLots.get(i);
            }
        }
        return null;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public PermitManager getPermitManager() {
        return permitManager;
    }

}
