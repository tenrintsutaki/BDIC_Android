package com.example.myapplication.Match;

import java.io.DataInputStream;
import java.util.Scanner;

/**
 * This is a unique Thread only for server to receive CMDs.
 */
public class ReceiveThreadServer implements Runnable{
    private DataInputStream dataInputStream;
    private String state = "NO";
    private int count = 0;
    private int ID;
    private ClientHandler handler;
    private boolean ifFirst = true;

    public ReceiveThreadServer(DataInputStream dataInputStream, int ID, ClientHandler handler) {
        super();
        this.ID = ID;
        this.dataInputStream = dataInputStream;
        this.handler = handler;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true){
            Scanner scanner = new Scanner(dataInputStream);
            scanner.useDelimiter("\n");
            while(scanner.hasNext()) {
                String message = scanner.nextLine();//get the message.
                if(ifFirst){
                    addNew(message);
                    ifFirst = false;
                }else{
                    this.handler.sendMessageToPair(message);
                }
            }
        }
    }

    private void addNew(String message){//Send the new user's information to the main server.
        String[] infors = message.split(",");
        double latitude = Double.parseDouble(infors[0]);
        double longitude = Double.parseDouble(infors[1]);
        String name = infors[2];
        ClientModel clientModel = new ClientModel(new double[]{latitude,longitude},name);
        this.handler.getThisServer().addNewMather(clientModel);
    }

}
