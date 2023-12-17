package com.example.myapplication.Match;

import com.example.myapplication.Circular_Button_Test;

import java.io.DataInputStream;
import java.util.Scanner;

public class ReceiveThread implements Runnable{
    private DataInputStream dataInputStream;
    private String ip;
    private State state;
    private Client client;
    private String currentMessage;

    public ReceiveThread(DataInputStream dataInputStream,State state,Client client) {
        super();
        this.client = client;
        this.dataInputStream = dataInputStream;
        this.currentMessage = "";
        this.state = state;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true){
            Scanner scanner = new Scanner(dataInputStream);
            scanner.useDelimiter("\n");
            while(scanner.hasNext()) {
                String message = scanner.nextLine();
                if(message.startsWith("MATCHED")){
                    state.setMatched(true);
                    System.out.println(message);
                    currentMessage = message;
                }else if(message.startsWith("MESSAGE")){
                    String[] buyInformation = message.split(",");
                    presentMessage(buyInformation[1]);
                    System.out.println(message);
                }else {
                    System.out.println(message);
                    currentMessage = message;
                }
            }
        }
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void presentMessage(String str) {//This prent the message on the screen
        Circular_Button_Test mainActivity = this.client.getMain_activity();
        if (this.client.getUI() != null) {//If there is a dialog running
            mainActivity.runOnUiThread(new Runnable() {//Create in a new thread
                @Override
                public void run() {
                    ReceiveThread.this.client.getUI().appendMessage(str);
                }
            });

        }
    }
}
