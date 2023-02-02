package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;

abstract class Task {
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
    public void save (SharedPreferences pref, int id, int type){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Integer.toString(id)+"_name", name);
        edit.putInt(Integer.toString(id)+"_progress", progress);
        edit.putInt(Integer.toString(id)+"_goal", goal);
        edit.putInt(Integer.toString(id)+"_boni", boni);
        edit.putInt(Integer.toString(id)+"_type", type);
        edit.apply();
    }

    public void load (SharedPreferences pref,int id){
        name = pref.getString(Integer.toString(id)+"_name", "");
        goal = pref.getInt(Integer.toString(id)+"_goal", 0);
        boni = pref.getInt(Integer.toString(id)+"_boni", 0);
        progress = pref.getInt(Integer.toString(id)+"_progress", 0);
    }

    public String getName(){
        return name;
    }
    abstract public void update();
    abstract public void finish();
}

class Task_Easy_Click extends Task{
    public Task_Easy_Click(GameState s){
        goal= s.getCcount() + 3*60* s.getClickspersecond()+500;
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = 100 + s.getMultiplier() * s.getClickspersecond() * 120;
        state = s;
    }

    @Override
    public void update() {
        progress = state.getCcount();
    }

    @Override
    public void finish() {
        state.incCcount(boni);
    }
}

