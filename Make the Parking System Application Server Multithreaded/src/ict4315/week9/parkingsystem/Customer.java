package ict4315.parkingsystem;

import java.io.Serializable;
import java.util.UUID;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * 
 */
public final class Customer implements Serializable {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final Address address;

    public Customer(String id, String firstName, String lastName, String phoneNumber, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    //Fixme:this is a temporary method to test ParkingGUI
    public Customer(String firstName, String lastName, String phoneNumber, Address address) {
        this.id = generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    //Fixme:this is a temporary method to test ParkingGUI
    private String generateId() {
        int max = 100;
        int min = 1;
        int idNumber = min + (int) (Math.random() * ((max - min) + 1));
        return "CUS" + idNumber;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public String getCustomerName() {
        String str = String.format("%s %s", firstName, lastName);
        return str;
    }

}
