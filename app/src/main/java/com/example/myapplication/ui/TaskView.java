package com.example.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.myapplication.Model.Running;
import com.example.myapplication.Model.Task;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TaskView extends ArrayAdapter<Task> {
    private int resource_id;
    private TextView taskName;
    private TextView taskDescribe;
    private TextView taskAward;
    private ImageView taskCondition;

    public TaskView(Context context, int resource, ArrayList<Task> object) {
        super(context,resource,object);
        this.resource_id = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task currentTask = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource_id,parent,false);
        taskName = view.findViewById(R.id.task_name);
        taskAward = view.findViewById(R.id.task_award);
        taskDescribe = view.findViewById(R.id.task_describe);
        taskCondition = view.findViewById(R.id.taskConditionIcon);

        taskName.setText(currentTask.getName());
        taskAward.setText(currentTask.getAward()+"");
        taskDescribe.setText(currentTask.getContent());
        if(currentTask.getFinished()==1){//set the resource of each icon.
            taskCondition.setImageResource(R.drawable.correct);
        }else{
            taskCondition.setImageResource(R.drawable.close);
        }
        return view;
    }
}
