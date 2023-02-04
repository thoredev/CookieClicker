package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.Stack;

public class TaskManager {
    Task tasks [];

    GameState gameState;
    TextView tvEasy;
    TextView tvMedium;
    TextView tvHard;
    ProgressBar pbEasy;
    ProgressBar pbMedium;
    ProgressBar pbHard;

    private SharedPreferences pref;

    public TaskManager(SharedPreferences v, GameState s, Stack<View> uiElements){
        tasks = new Task[3];
        pref = v;
        gameState = s;

         tvEasy = (TextView) uiElements.pop();
         tvMedium = (TextView) uiElements.pop();
         tvHard = (TextView) uiElements.pop();
         pbEasy = (ProgressBar) uiElements.pop();
         pbMedium = (ProgressBar) uiElements.pop();
         pbHard = (ProgressBar) uiElements.pop();
        tvEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks[0].isCompleted()){
                    tasks[0].finish();
                    Random R = new Random();
                    int random = R.nextInt(1+1);
                    switch (random){case 0: tasks[0] = new Task_Easy_Click(s, tvEasy, pbEasy); break;
                                    case 1: tasks[0] = new Task_Easy_Click2(s, tvEasy, pbEasy); break;
                    }

                    tvEasy.setText(tasks[0].getName());
                }
            }
        });

        tvMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks[1].isCompleted()){
                    tasks[1].finish();
                    tasks[1] = new Task_Medium_Click(s, tvMedium, pbMedium);
                    tvMedium.setText(tasks[1].getName());
                }
            }
        });

        tvHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks[2].isCompleted()){
                    tasks[2].finish();
                    tasks[2] = new Task_Hard_Click(s, tvHard, pbHard);
                    tvHard.setText(tasks[2].getName());
                }
            }
        });


        //Load Tasks
       loadTasks();
    }

    public void updateTasks(){
        for(int i = 0; i < tasks.length; i++){
            tasks[i].update();
        }
        saveTasks();
    }

    public void processUserclick(){
        for(int i = 0; i < tasks.length; i++){
            tasks[i].onUserclick();
        }
    }

    public void loadTasks(){
        switch (pref.getInt("0_type",-1)){
            case 0: tasks [0] = new Task_Easy_Click(gameState, tvEasy, pbEasy); tasks[0].load(pref,0); break;
            case 1: tasks [0] = new Task_Easy_Click2(gameState, tvEasy, pbEasy); tasks[0].load(pref,0); break;
            default:tasks [0] = new Task_Easy_Click(gameState, tvEasy, pbEasy);
        }

        switch (pref.getInt("1_type",-1)){
            case 0: tasks [1] = new Task_Medium_Click(gameState, tvMedium, pbMedium); tasks[1].load(pref,1); break;
            //case 1: tasks [1] = new Task_Medium_Click2(gameState, tvMedium, pbMedium); tasks[1].load(pref,1); break; //für später
            default:tasks [1] = new Task_Medium_Click(gameState, tvMedium, pbMedium);
        }

        switch (pref.getInt("2_type",-1)){
            case 0: tasks [2] = new Task_Hard_Click(gameState, tvHard, pbHard); tasks[2].load(pref,2); break;
            //case 1: tasks [2] = new Task_Hard_Click2(gameState, tvHard, pbHard); tasks[2].load(pref,2); break;
            default:tasks [2] = new Task_Hard_Click(gameState, tvHard, pbHard);
        }

    }

    public void saveTasks(){
        for(int i = 0; i < tasks.length; i++){
            tasks[i].save(pref, i);
        }
    }
}
