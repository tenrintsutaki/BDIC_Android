package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Database.RepositoryStorage;
import com.example.myapplication.Database.RunningStorage;
import com.example.myapplication.Database.TaskStorage;
import com.example.myapplication.Model.Task;
import com.example.myapplication.ui.TaskView;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    private ArrayList<Task> tasks = new ArrayList<>();
    private RunningStorage runningStorage;
    private RepositoryStorage repositoryStorage;
    private TaskStorage taskStorage;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.hide();
        }

        setContentView(R.layout.task_list_page);
        currentUser = (CurrentUser) getApplication();
        initTaskList();

        ListView taskList = findViewById(R.id.task_list);
        TaskView taskView = new TaskView(TaskActivity.this,R.layout.task_list,tasks);
        taskList.setAdapter(taskView);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//Click to finish or not
                if(tasks.get(i).getFinished()==1){
                    //del this from the DB and toast
                    Toast.makeText(TaskActivity.this,"You finished this Task !",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TaskActivity.this,"You have not finish this Task",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initTaskList(){//init the task list and check the progress
        tasks = taskStorage.findAllTasks();
        for (Task task:tasks) {
            checkProgress(task);
        }
    }

    private void checkProgress(Task task){//check if the task is finished?
        switch (task.getName()){
            case "Daily Speed"://check if user could finish these tasks
                if(runningStorage.checkHighestSpeed(currentUser.getCurrentUser().getId())){
                    task.setFinished(1);
                }
                break;
            case "Daily Energy":
                if(runningStorage.checkHighestEnergy(currentUser.getCurrentUser().getId())){
                    task.setFinished(1);
                }
                break;
            case "Keep Running":
                if(runningStorage.checkHighestTime(currentUser.getCurrentUser().getId())){
                    task.setFinished(1);
                }
                break;
            case "Traveller":
                if(runningStorage.checkHighestMiles(currentUser.getCurrentUser().getId())){
                    task.setFinished(1);
                }
                break;
        }
    }

    public void initDB(){
        MainSQLHelper mainSQLHelper = new MainSQLHelper(TaskActivity.this,"SpinNew.db",null,1);
        SQLiteDatabase db =  mainSQLHelper.getWritableDatabase();
        runningStorage = new RunningStorage(db);
        taskStorage = new TaskStorage(db);
    }
}
