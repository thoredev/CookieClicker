package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

abstract class Task {

    protected TextView textView;
    protected ProgressBar progressBar;
    protected GameState state;

    protected String name;
    protected int progress;
    protected int goal;
    protected int boni;

    public boolean isCompleted(){
        if(progress >= goal){
            return true;
        } else {
            return false;
        }
    }
    public int getProgress(){
        return progress*100/goal;
    }
    public void save (SharedPreferences pref, int id){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Integer.toString(id)+"_name", name);
        edit.putInt(Integer.toString(id)+"_progress", progress);
        edit.putInt(Integer.toString(id)+"_goal", goal);
        edit.putInt(Integer.toString(id)+"_boni", boni);
        edit.apply();
    }

    public void load (SharedPreferences pref,int id){
        if(!Objects.equals(pref.getString(Integer.toString(id) + "_name", ""), "")) {
            name = pref.getString(Integer.toString(id) + "_name", "");
            goal = pref.getInt(Integer.toString(id) + "_goal", 0);
            boni = pref.getInt(Integer.toString(id) + "_boni", 0);
            progress = pref.getInt(Integer.toString(id) + "_progress", 0);
        }
    }

    public String getName(){
        return name;
    }
    abstract public void update();

    abstract public void finish();
}

class Task_Easy_Click extends Task{
    ProgressBar pb;
    public Task_Easy_Click(GameState s, TextView v, ProgressBar p){
        goal= s.getCcount() + 3*60* s.getClickspersecond()+500;
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = 100 + s.getMultiplier() * s.getClickspersecond() * 120;
        state = s;
        textView = v;
        progressBar = p;
        textView.setText(name);
    }

    @Override
    public void update() {
        progress = state.getCcount();
        textView.setText(name);
        progressBar.setProgress(getProgress());
    }

    @Override
    public void finish() {
        state.incCcount(boni);
    }
}

