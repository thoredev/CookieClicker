package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;

public class TaskManager {
    Task tasks [];

    private SharedPreferences pref;

    public TaskManager(SharedPreferences v, GameState s){
        tasks = new Task[3];
        pref = v;

        for (int i = 0; i< tasks.length ;i++){
            int type = pref.getInt(Integer.toString(i)+"_type",-1);
            switch (type){
                case -1:
                    tasks[i] = new Task_Easy_Click(s);
                    break;
                case 0:
                    tasks[i] = new Task_Easy_Click(s);
                    tasks[i].load(pref, i);
                    break;
            }
        }
    }
}
