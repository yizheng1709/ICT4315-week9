/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.server;

import ict4315.client.ResponseData;
import ict4315.parkingsystem.Address;
import static ict4315.parkingsystem.Main.WEEKDAY_CARTYPE_DISCOUNT;
import static ict4315.parkingsystem.Main.WEEKDAY_DISCOUNT;
import ict4315.parkingsystem.ParkingChargeCalculatorFactory;
import ict4315.parkingsystem.ParkingLot;
import ict4315.parkingsystem.ParkingOffice;
import ict4315.parkingsystem.PermitManager;
import ict4315.parkingsystem.TransactionManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *  
 */
public class Server {

    static {
        System.setProperty(
                "java.util.logging.SimpleFormatter.format", "%1$tc %4$-7s (%2$s) %5$s %6$s%n");
    }

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final int PORT = 7777;

    private final ParkingService service;

    private Duration cumulativeDuration = Duration.ZERO;

    private int connectionCount = 0;

    private static volatile boolean doContinue = true;

    public static void stopServer() {
        doContinue = false;
        Thread.currentThread().interrupt();
    }

    public Server(ParkingService service) {
        this.service = service;
    }

    public void startServer() throws IOException {
        logger.info("Starting server: " + InetAddress.getLocalHost().getHostAddress());
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);
            ExecutorService executorService = Executors.newCachedThreadPool();

            while (doContinue) {
                // Accept connection from client
                Socket client = serverSocket.accept();

                // Measure performance
                Instant start = Instant.now();

                Runnable clientHandler = new ClientHandler(client, getService());
                executorService.execute(clientHandler);

                Instant done = Instant.now();

                cumulativeDuration = cumulativeDuration.plus(Duration.between(start, done));
                connectionCount++;
            }

            executorService.shutdown();

            System.out.println("Handled " + connectionCount + " connections in " + cumulativeDuration);
            if (connectionCount > 0) {
                System.out.println("    "
                        + (cumulativeDuration.toNanos() / connectionCount)
                        + " ns. per connection");
            }
        }
    }

    public ParkingService getService() {
        return service;
    }
    
    /**
     * Run this as: $ java ict4300.week8.server.Server
     */
    

    private void handleClient(InputStream inputStream, OutputStream outputStream) {
        try (ObjectInputStream is = new ObjectInputStream(inputStream);
             ObjectOutputStream out = new ObjectOutputStream(outputStream)) {

            Instant start = Instant.now();

            RequestData requestData = (RequestData) is.readObject();
            // Handle the client's request

            ResponseData responseData = new ResponseData();
            // Set the response data

            out.writeObject(responseData);
            out.flush();

            Instant done = Instant.now();
            Duration clientHandlingTime = Duration.between(start, done);
            cumulativeDuration = cumulativeDuration.plus(clientHandlingTime);
            connectionCount++;

            System.out.println("Client handling time: " + clientHandlingTime);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getLocalizedMessage());
        }
    }
    
    public static void main(String[] args) throws Exception {

        Address parkingOfficeAddress = new Address("20 Joseph Street", "South Iris", "Bronx", "NY", "");
        TransactionManager transactionManager = new TransactionManager();
        PermitManager permitManager = new PermitManager();
        ParkingOffice parkingOffice = new ParkingOffice("Main Office", parkingOfficeAddress, transactionManager, permitManager);
        ParkingService service = new ParkingService(parkingOffice);

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

        parkingOffice.addParkingLot(parkingLotA);
        parkingOffice.addParkingLot(parkingLotB);
        parkingOffice.addParkingLot(parkingLotC);
        
        Server server = new Server(service);
        server.startServer();
    }
}
