package com.example.myapplication.Match;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author ALiosha
 */

public class ClientHandler extends Thread{
    private Socket socket;
    private Server thisServer;
    private int ID;
    private Boolean isSend = false;
    private String currentMessage;
    private ReceiveThreadServer receiveThreadServer;
    private SendThreadServer sendThreadServer;

    ClientHandler(Socket socket, Server server, int ID){
        this.thisServer = server;
        this.ID = ID;
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            this.receiveThreadServer = new ReceiveThreadServer(dataInputStream,this.ID,this);
            this.sendThreadServer = new SendThreadServer(dataOutputStream);

            new Thread(sendThreadServer).start();
            new Thread(receiveThreadServer).start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessageToPair(String message){
        this.thisServer.sendMessage(ID,message);
    }

    public Socket getSocket() {
        return socket;
    }

    public Server getThisServer() {
        return thisServer;
    }
}
