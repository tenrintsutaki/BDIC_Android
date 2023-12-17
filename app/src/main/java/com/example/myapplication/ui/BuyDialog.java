package com.example.myapplication.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.myapplication.Model.Good;
import com.example.myapplication.R;

public class BuyDialog extends Dialog {
    private Good good;
    private dialogClickListener listener;

    public BuyDialog(@NonNull Context context, Good good) {
        super(context);
        this.good = good;
    }

    public void setListener(dialogClickListener listener) {
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
        this.setContentView(R.layout.buy_confirm_dialog);
        TextView confirm = findViewById(R.id.confirm_information);
        confirm.setText("Confirm: "+good.getName());
        TextView minus = findViewById(R.id.minus_money);
        minus.setText("-"+good.getPrice());
        Button confirmBtn = findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this::onClick);//bind the listener
    }
}
