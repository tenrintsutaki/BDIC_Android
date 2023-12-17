package com.example.myapplication.Match;

import com.example.myapplication.Circular_Button_Test;
import com.example.myapplication.ui.MatchDialog;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;

/**
 * @author ALiosha LouYiming
 * This is the basic class of the p2p matching system
 */

public class Client {

    private double[] locations;
    private String username;
    private volatile State state;
    private Socket socket;
    private SendThread sendThread;
    private ReceiveThread receiveThread;
    private MatchDialog ui;
    private Circular_Button_Test main_activity;

    public Client(double[] locations, String username){
        this.locations = locations;
        this.username = username;
    }

    public void startMatch(){
        try {
            socket = new Socket();
            socket.setSoTimeout(3000);
            socket.connect(new InetSocketAddress("10.0.2.2",8081),3000);
            System.out.println("Connected to"+socket.getInetAddress()+"Port: "+socket.getPort());

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            state = new State();

            receiveThread = new ReceiveThread(dataInputStream,state,this);
            sendThread = new SendThread(dataOutputStream,locations,username,state);

            new Thread(receiveThread).start();
            new Thread(sendThread).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage(){
        if(this.receiveThread!=null) {
            return this.receiveThread.getCurrentMessage();
        }else{
            return "No Connection";
        }
    }

    public String[] getLocation(){
        if(!Objects.equals(getMessage(), "No Connection")&& !Objects.equals(getMessage(), "")){
            String message = getMessage();
            String[] locations = message.split(",");
            return new String[]{locations[0],locations[1]};
        }else{
            return new String[]{"0","0"};
        }
    }

    public void stopClient(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SendThread getSendThread(){
        return this.sendThread;
    }

    public void setUI(MatchDialog matchDialog){
        this.ui = matchDialog;
    }

    public MatchDialog getUI() {
        return ui;
    }

    public Circular_Button_Test getMain_activity() {
        return main_activity;
    }

    public void setMain_activity(Circular_Button_Test main_activity) {
        this.main_activity = main_activity;
    }

    public State getState(){
        return state;
    }

    public static void main(String[] args) {
        Client client = new Client(new double[]{114.514,1919.810},"Tenrin");
        client.startMatch();
    }

}
