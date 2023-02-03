package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Stack;

public class TaskManager {
    Task tasks [];

    private SharedPreferences pref;

    public TaskManager(SharedPreferences v, GameState s, Stack<View> uiElements){
        tasks = new Task[3];
        pref = v;

        TextView tvEasy = (TextView) uiElements.pop();
        TextView tvMedium = (TextView) uiElements.pop();
        TextView tvHard = (TextView) uiElements.pop();
        ProgressBar pbEasy = (ProgressBar) uiElements.pop();
        ProgressBar pbMedium = (ProgressBar) uiElements.pop();
        ProgressBar pbHard = (ProgressBar) uiElements.pop();
        tvEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks[0].isCompleted()){
                    tasks[0].finish();
                    tasks[0] = new Task_Easy_Click(s, tvEasy, pbEasy);
                    tvEasy.setText(tasks[0].getName());
                }
            }
        });

        //Load Tasks
        tasks[0] = new Task_Easy_Click(s, tvEasy, pbEasy);
        tasks[0].load(pref, 0);
        tasks[1] = new Task_Easy_Click(s, tvMedium, pbMedium);
        tasks[1].load(pref, 1);
        tasks[2] = new Task_Easy_Click(s, tvHard, pbHard);
        tasks[2].load(pref, 2);
    }

    public void updateTasks(){
        for(int i = 0; i < tasks.length; i++){
            tasks[i].update();
        }
        saveTasks();
    }

    public void saveTasks(){
        for(int i = 0; i < tasks.length; i++){
            tasks[i].save(pref, i);
        }
    }
}
