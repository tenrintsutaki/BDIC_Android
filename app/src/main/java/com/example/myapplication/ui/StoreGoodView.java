package com.example.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.myapplication.Model.Good;
import com.example.myapplication.Model.Repository;
import com.example.myapplication.Model.Running;
import com.example.myapplication.R;

import java.util.ArrayList;

public class StoreGoodView extends ArrayAdapter<Good> {
    private int resource_id;

    public StoreGoodView(Context context, int resource, ArrayList<Good> repositories) {
        super(context, resource, repositories);
        this.resource_id = resource;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Good goodCurrent = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource_id,parent,false);
        TextView goodName = view.findViewById(R.id.good_name);
        ImageView goodIcon = view.findViewById(R.id.good_icon);
        TextView goodPrice = view.findViewById(R.id.good_price);
        TextView goodDescribe = view.findViewById(R.id.good_describe);

        goodName.setText(goodCurrent.getName());
        goodDescribe.setText(goodCurrent.getDescribe());
        switch (goodCurrent.getId()){
            case 1:
                goodIcon.setImageResource(R.color.pink);
                break;
            case 2:
                goodIcon.setImageResource(R.color.black);
                break;
            case 3:
                goodIcon.setImageResource(R.color.gray);
                break;
            case 4:
                goodIcon.setImageResource(R.drawable.rainbow);
                break;
            case 5:
                goodIcon.setImageResource(R.drawable.green_background);
                break;
            case 6:
                goodIcon.setImageResource(R.drawable.galaxy);
                break;
            case 7:
                goodIcon.setImageResource(R.drawable.rainbow);
                break;
            case 8:
                goodIcon.setImageResource(R.color.orange);
                break;
            case 9:
                goodIcon.setImageResource(R.color.orange);
                break;
        }

        goodPrice.setText(String.valueOf(goodCurrent.getPrice()));
        return view;
    }

}
