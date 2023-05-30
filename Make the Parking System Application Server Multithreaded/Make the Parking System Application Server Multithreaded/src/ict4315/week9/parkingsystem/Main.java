/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import ict4315.server.ParkingService;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class Main {

    public static String COMMAND_CUSTOMER = "CUSTOMER";
    public static String COMMAND_CAR = "CAR";
    public static String WEEKDAY_DISCOUNT = "WeekdayDiscount";
    public static String CARTYPE_DISCOUNT = "CarTypeDiscount";
    public static String WEEKDAY_CARTYPE_DISCOUNT = "WeekdayCarTypeDiscount";

    public static void main(String[] args) {

        try {

            //Week 6
            System.out.println("WEEK 6:");

            //Create parkingOffice
            Address parkingOfficeAddress = new Address("20 Joseph Street", "South Iris", "Bronx", "NY", "");
            TransactionManager transactionManager = new TransactionManager();
            PermitManager permitManager = new PermitManager();
            ParkingOffice office = new ParkingOffice("Main Office", parkingOfficeAddress, transactionManager, permitManager);

            ParkingChargeCalculatorFactory parkingChargeCalculatorFactory = new ParkingChargeCalculatorFactory();

            //Create parking Lot A with WeekdayDiscount
            Address parkingLotAddressA = new Address("214 CherryCreek", "Broomfield", "Bronx", "CO", "");
            ParkingLot parkingLotA = new ParkingLot("1", "Lot A", parkingLotAddressA, WEEKDAY_DISCOUNT, parkingChargeCalculatorFactory);
            System.out.println(String.format("Parking lot %s applies %s", parkingLotA.getName(), parkingLotA.getDiscountStrategy()));

            //Create parking Lot B with WeekdayCarTypeDiscount
            Address parkingLotAddressB = new Address("111 University Blvd", "Littelton", "Denver", "Co", "");
            ParkingLot parkingLotB = new ParkingLot("2", "Lot B", parkingLotAddressB, WEEKDAY_CARTYPE_DISCOUNT, parkingChargeCalculatorFactory);
            System.out.println(String.format("Parking lot %s applies %s", parkingLotB.getName(), parkingLotB.getDiscountStrategy()));

            //Create parking Lot C with No Discount
            Address parkingLotAddressC = new Address("121 University Blvd", "Littelton", "Denver", "Co", "");
            ParkingLot parkingLotC = new ParkingLot("3", "Lot C", parkingLotAddressC, parkingChargeCalculatorFactory);
            System.out.println("Parking lot Lot C does not apply discount strategy");

            //Add TransactionManager observes Parking Lot A, B, C
            parkingLotA.setParkingObserver(transactionManager);
            parkingLotB.setParkingObserver(transactionManager);
            parkingLotC.setParkingObserver(transactionManager);

            //Create Cars
            Address customerAddress = new Address("1 1st St", "Ste 101", "Fort Collins", "CO", "80321");
            Customer customer = new Customer("1", "Abra", "Simpson", "0111101100", customerAddress);
            Car carSuv = new Car(CarType.SUV, "ABC", customer);
            ParkingPermit permitSuv = new ParkingPermit("AS1", carSuv);

            Car carCompact = new Car(CarType.SUV, "ABC", customer);
            ParkingPermit permitCompact = new ParkingPermit("AS1", carCompact);
            
            
            LocalDateTime aMondayAt6 = LocalDateTime.now().with(LocalDateTime.of(2020, Month.MARCH, 16, 6, 0));
            LocalDateTime aMondayAt10 = LocalDateTime.now().with(LocalDateTime.of(2020, Month.MARCH, 16, 10, 0));

            ParkingEvent eventMondayLotA = new ParkingEvent(parkingLotA, aMondayAt6, aMondayAt10, permitSuv);
            parkingLotA.notify(eventMondayLotA);

            ParkingEvent eventMondayLotB = new ParkingEvent(parkingLotB, aMondayAt6, aMondayAt10, permitSuv);
            parkingLotB.notify(eventMondayLotB);

            ParkingEvent eventMondayLotC = new ParkingEvent(parkingLotC, aMondayAt6, permitSuv);
            parkingLotC.notify(eventMondayLotC);


        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
