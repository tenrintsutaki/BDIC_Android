package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

/**
 * This class is made for implementing the decoration or customize function in my application.
 */
public class DecorationActivity extends AppCompatActivity {
    private Button changeBG;
    private Button changeBG2;
    private ImageView backGround;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.background_decoration);
        changeBG = findViewById(R.id.change);
        changeBG2 = findViewById(R.id.change2);
        backGround = findViewById(R.id.backGround);
        changeBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backGround.setImageResource(R.drawable.ukio_e);
            }
        });

        changeBG2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backGround.setImageResource(R.drawable.spin);
            }
        });
    }
}
