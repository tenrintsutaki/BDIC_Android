package com.example.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.Model.Menu;
import com.example.myapplication.R;

import java.util.ArrayList;

public class PersonalView extends ArrayAdapter<Menu> {
    private int resource_id;
    private ImageView menu_image;
    private TextView menu_name;

    public PersonalView(Context context, int textViewResourceId, ArrayList<Menu> object){
        super(context,textViewResourceId,object);
        this.resource_id = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//Customize our list view
        Menu menuCurrent = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource_id,parent,false);
        menu_image = view.findViewById(R.id.image_menu);
        menu_name = view.findViewById(R.id.text_menu);
        menu_name.setText(menuCurrent.get_name());
        menu_image.setImageResource(menuCurrent.getImg_id());
        return view;
    }
}
