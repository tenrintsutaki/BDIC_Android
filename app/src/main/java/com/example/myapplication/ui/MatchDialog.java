package com.example.myapplication.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.myapplication.Match.Client;
import com.example.myapplication.R;

public class MatchDialog extends Dialog {
    private dialogClickListener listener;
    private Button sendBtn;
    private EditText inputMessage;
    private TextView messageArea;
    private Client client;

    public MatchDialog(@NonNull Context context) {
        super(context);
    }

    public void setListener(dialogClickListener listener){
        this.listener = listener;
    }

    public interface dialogClickListener{//write an interface to listen the click from activity
        public void onClick(View view);
    }

    public void onClick(View v) {//the operation after the click.
        // TODO Auto-generated method stub
        listener.onClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.match_dialog);
        this.getWindow().setWindowAnimations(R.style.AnimTop);

        sendBtn = findViewById(R.id.sendBtn);
        inputMessage = findViewById(R.id.inputMessage);
        messageArea = findViewById(R.id.messageArea);

        sendBtn.setOnClickListener(this::onClick);
    }

    public String getMessage(){
        return inputMessage.getText()+"";
    }

    public void appendMessage(String message){
        messageArea.append("   Partner: "+message+"\n");
    }

    public void sendMessage(){
        messageArea.append("   ME: "+inputMessage.getText()+"\n");
        inputMessage.setText("");
    }
}
