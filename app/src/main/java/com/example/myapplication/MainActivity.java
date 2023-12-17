package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    //A test activity just for learning.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent  = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://space.bilibili.com/40178341"));
                startActivity(intent);
                break;
            case R.id.remove_item:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:110"));
                startActivity(intent);
                break;
            case R.id.select_1:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:110"));
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
}