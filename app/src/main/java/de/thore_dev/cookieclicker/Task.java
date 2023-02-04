package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;
import android.view.View;
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
    protected int type;


    public void display (){
        if (! isCompleted()){
        textView.setText(name);
            textView.setTextColor(0xffffffff);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            textView.setTextSize(10);
        } else {
            textView.setText("+ "+Integer.toString(boni));
            textView.setTextColor(0xff7AC525);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(20);
        }
        progressBar.setProgress(getProgress());

    }

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
        edit.putInt(Integer.toString(id)+"_type", type);
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

     public void onUserclick(){}

    abstract public void finish();
}

class Task_Easy_Click extends Task{
    ProgressBar pb;
    public Task_Easy_Click(GameState s, TextView v, ProgressBar p){
        type = 0;
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
        display();

    }

    @Override
    public void finish() {
        state.incCcount(boni);
    }
}

class Task_Easy_Click2 extends Task{
    ProgressBar pb;
    public Task_Easy_Click2(GameState s, TextView v, ProgressBar p){
        type = 1;
        if (s.getCcount()>=20000){
            goal = 1000;
        }else if(s.getCcount()>=100000){
            goal = 2000;
        }else if (s.getCcount()>=500000){
            goal = 4000;
        }else {goal = 500;}
        name = "Klicke " + goal + " mal auf den Keks";
        progress = 0;
        boni = (int) (s.getCcount()* 0.05);
        state = s;
        textView = v;
        progressBar = p;
        textView.setText(name);
    }

    @Override
    public void update() {
        display();

    }

    @Override
    public void onUserclick(){
        progress++;
    }

    @Override
    public void finish() {
        state.incCcount(boni);
    }
}

class Task_Medium_Click extends Task{
    ProgressBar pb;
    public Task_Medium_Click(GameState s, TextView v, ProgressBar p){
        type = 0;
        goal= s.getCcount() + 60*60* s.getClickspersecond()+500;
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = 2;
        state = s;
        textView = v;
        progressBar = p;
        textView.setText(name);
    }

    @Override
    public void update() {
        progress = state.getCcount();
        display();

    }

    @Override
    public void finish() {
        state.incRarenc(boni);
    }
}
class Task_Hard_Click extends Task{
    ProgressBar pb;
    public Task_Hard_Click(GameState s, TextView v, ProgressBar p){
        type = 0;
        goal= s.getCcount() + 250*60* s.getClickspersecond()+500;
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = 1;
        state = s;
        textView = v;
        progressBar = p;
        textView.setText(name);
    }

    @Override
    public void update() {
        progress = state.getCcount();
        display();

    }

    @Override
    public void finish() {
        state.incMilk(boni);
    }
}

