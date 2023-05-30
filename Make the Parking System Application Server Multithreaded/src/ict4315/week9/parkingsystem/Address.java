package ict4315.parkingsystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Address implements java.io.Serializable {
    
    private final String streetAddress1;
    private final String streetAddress2;
    private final String city;
    private final String state;
    private final String zipcode;

    //Construct an Address with all line filled
    public Address(String streetAddress1, String streetAddress2, String city, String state, String zipcode) {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    //Construct an Address without streetAddress2
    public Address(String streetAddress1, String city, String state, String zipcode) {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = null;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }
    
    public String getAddressInfo() {
        String str;
        if (streetAddress2 == null) {
            str = String.format("%s\n%s, %s %s", streetAddress1, city, state, zipcode);
        } else {
            str = String.format("%s\n%s\n%s, %s %s", streetAddress1, streetAddress2, city, state, zipcode);
        }
        return str;
    }

}

