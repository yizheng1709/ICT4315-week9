/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.server;

import com.google.gson.Gson;
import ict4315.client.RequestData;
import ict4315.client.ResponseData;
import ict4315.parkingsystem.Address;
import ict4315.parkingsystem.CarType;
import ict4315.parkingsystem.ParkingEvent;
import ict4315.parkingsystem.ParkingLot;
import ict4315.parkingsystem.ParkingOffice;
import ict4315.parkingsystem.ParkingPermit;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *  
 */
public class ParkingService {

    protected final ParkingOffice parkingOffice;

    public ParkingService(ParkingOffice parkingOffice) {
        this.parkingOffice = parkingOffice;
    }

    protected ResponseData handleInput(InputStream in) throws Exception {
        @SuppressWarnings("resource")
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        RequestData requestData = (RequestData) objectInputStream.readObject();
        return performCommand(requestData);
    }

    private ResponseData performCommand(RequestData requestData) {
        Map<String, String> args = requestData.getData();
        ResponseData responseData = new ResponseData();
        
        switch (requestData.getCommandName()) {
            case "CUSTOMER":
                Address customerAddress = new Address(args.get("Address 1"), args.get("Address 2"), args.get("City"), args.get("State"), args.get("Zipcode"));
                try {
                    responseData.setResponse(parkingOffice.register(args.get("First Name"), args.get("Last Name"), args.get("Phone number"), customerAddress));
                } catch (Exception ex) {
                    Logger.getLogger(ParkingService.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "CAR":
                try {
                responseData.setResponse(parkingOffice.register(CarType.valueOf(args.get("COMPACT/SUV")), args.get("License"), args.get("Customer Id")));
            } catch (Exception ex) {
                Logger.getLogger(ParkingService.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            case "PARK": {
                try {
                    ParkingLot parkingLot = parkingOffice.getParkingLot(args.get("Parking lot Id"));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    
                    LocalDateTime dateTimeIn = LocalDateTime.parse(args.get("Time in"), formatter);
                    
                    LocalDateTime dateTimeOut = LocalDateTime.parse(args.get("Time out"), formatter);

                    ParkingPermit permit = parkingOffice.getParkingPermit(args.get("Permit Id"));
                    System.out.println("Nguyen permit " + permit.getId());

                    ParkingEvent event = new ParkingEvent(parkingLot, dateTimeIn, dateTimeOut, permit);
                    
                    
                    responseData.setResponse(parkingOffice.park(event));
                } catch (Exception ex) {
                    Logger.getLogger(ParkingService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "CHARGES":
            {
                try {
                    responseData.setResponse(parkingOffice.getParkingCharges(parkingOffice.getCustomer(args.get("Customer Id"))));
                } catch (Exception ex) {
                    Logger.getLogger(ParkingService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
                
            case "STOP":
                Server.stopServer();

            default:
                responseData.setSuccess(false);
                responseData.setError("Command is not found");
        }
        System.out.println("Response: " + responseData.getResponse());
        return responseData;
    }

}
