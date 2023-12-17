package com.example.myapplication.Match;

import java.io.DataOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SendThread implements Runnable{
    DataOutputStream dataOutStream;
    private boolean needSend = false;
    private String currentMessage = null;
    private double[] locations;
    private String username;
    private boolean isStartedSend;
    private State state;
    private ArrayList<String> goodList;

    public SendThread(DataOutputStream dataOutStream,double[] locations,String username,State state){
        this.dataOutStream = dataOutStream;
        this.locations = locations;
        this.username = username;
        this.isStartedSend = true;
        this.state = state;
        this.goodList = new ArrayList<>();
    }

    public void sendPrivateMessage(String message_send){//Send the good list to another client
        PrintStream printStream = new PrintStream(dataOutStream,true);
        printStream.println("MESSAGE,"+message_send);
    }

    @Override
    public void run() {
        String msg = locations[0]+","+ locations[1] +","+username;
        Timer timer = new Timer();
        while(true){
            PrintStream printStream = null;
            if(!msg.equals("")&&!state.getMatched()) {//Send the information to the server in order to record the information
                printStream = new PrintStream(dataOutStream,true);
                printStream.println(msg);
                msg = "";
            }else if(state.getMatched()){//When the condition is "matching"
                msg = locations[0]+","+ locations[1] +","+username;
                SendTask sendTask = new SendTask(msg,dataOutStream);//A new thread, for sending new message and update.
                timer.schedule(sendTask,0,1000);
                state.setMatched(false);//Avoid sending so much messages
            }
        }
    }
}

class SendTask extends TimerTask{
    private String message;
    private DataOutputStream dataOutputStream;

    SendTask(String message,DataOutputStream dataOutputStream){
        this.message = message;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        PrintStream printStream = new PrintStream(dataOutputStream,true);
        printStream.println(message);
    }
}
