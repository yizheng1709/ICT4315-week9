package ict4315.server;

import ict4315.client.RequestData;
import ict4315.client.ResponseData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private final Socket client;
    private final ParkingService parkingService;

    public ClientHandler(Socket client, ParkingService parkingService) {
        this.client = client;
        this.parkingService = parkingService;
    }

    @Override
    public void run() {
        try (
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream())
        ) {
            // Handle the client's request
            ResponseData responseData = parkingService.handleInput(in);
            out.writeObject(responseData);
            out.flush();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
