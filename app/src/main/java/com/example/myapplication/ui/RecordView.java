package com.example.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.myapplication.Model.Menu;
import com.example.myapplication.Model.Running;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RecordView extends ArrayAdapter<Running> {
    private int resource_id;
    private TextView menu_name;
    private TextView miles;
    private TextView speed;
    private TextView energy;
    private TextView time;

    public RecordView(Context context, int resource, ArrayList<Running> object) {
        super(context,resource,object);
        this.resource_id = resource;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Running runningCurrent = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource_id,parent,false);
        menu_name = view.findViewById(R.id.run_name);
        menu_name.setText("      Run Record "+runningCurrent.getId());
        miles = view.findViewById(R.id.kmNumber);
        miles.setText(runningCurrent.getMiles()+"");
        speed = view.findViewById(R.id.speedNum);
        speed.setText(runningCurrent.getSpeed()+"KM/H");
        energy = view.findViewById(R.id.energyNum);
        energy.setText(runningCurrent.getEnergy()+"CAL");
        time = view.findViewById(R.id.timeNum);
        time.setText(runningCurrent.getTime()+"S");
        return view;
    }
}
