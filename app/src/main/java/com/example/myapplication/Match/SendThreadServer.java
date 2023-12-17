package com.example.myapplication.Match;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendThreadServer implements Runnable{
    DataOutputStream dataOutStream;
    private boolean needSend = false;
    private String currentMessage = null;

    public SendThreadServer(DataOutputStream dataOutStream){
        this.dataOutStream = dataOutStream;
    }

    @Override
    public void run() {
        while(true){
            String msg;
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

                msg = bf.readLine();
                if(msg!=null && !msg.equals("")) {
                    dataOutStream.writeUTF(msg);
                }

                if(needSend){
                    dataOutStream.writeUTF(currentMessage);
                    needSend = false;
                }


            } catch (IOException e) {
                // TODO Auto-generated catch block
                break;
            }
        }
    }
}
