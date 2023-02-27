package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

abstract class Task {

    protected TextView textView;
    protected ProgressBar progressBar;
    protected GameState state;

    protected String name;
    protected BigInteger progress;
    protected BigInteger goal;
    protected BigInteger boni;
    protected int type;


    public void display (){
        if (! isCompleted()){
        textView.setText(name);
            textView.setTextColor(0xffffffff);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            textView.setTextSize(10);
        } else {
            textView.setText("+ "+ state.BigIntToSuffixString(boni));
            textView.setTextColor(0xff7AC525);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(20);
        }
        progressBar.setProgress(getProgress());

    }

    public boolean isCompleted(){
        if(progress.compareTo(goal) >= 0){
            return true;
        } else {
            return false;
        }
    }
    public int getProgress(){
        return Integer.parseInt(progress.multiply(new BigInteger("100")).divide(goal).toString());
    }
    public void save (SharedPreferences pref, int id){
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Integer.toString(id)+"_name", name);
        edit.putString(Integer.toString(id)+"_progress", progress.toString());
        edit.putString(Integer.toString(id)+"_goal", goal.toString());
        edit.putString(Integer.toString(id)+"_boni", boni.toString());
        edit.putInt(Integer.toString(id)+"_type", type);
        edit.apply();
    }

    public void load (SharedPreferences pref,int id){
        if(!Objects.equals(pref.getString(Integer.toString(id) + "_name", ""), "")) {
            name = pref.getString(Integer.toString(id) + "_name", "");
            goal = new BigInteger(pref.getString(Integer.toString(id) + "_goal", "0"));
            boni = new BigInteger(pref.getString(Integer.toString(id) + "_boni", "0"));
            progress = new BigInteger(pref.getString(Integer.toString(id) + "_progress", "0"));
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
        goal= s.getCcount().add(s.getClickspersecond().multiply(new BigInteger(Integer.toString(3*60))).add(new BigInteger("500")));
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = s.getMultiplier().multiply(s.getClickspersecond()).multiply(new BigInteger("120")).add(new BigInteger("100"));
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
        if (s.getCcount().compareTo(new BigInteger("20000"))>=0){
            goal = new BigInteger("1000");
        }else if(s.getCcount().compareTo(new BigInteger("100000"))>=0){
            goal = new BigInteger("2000");
        }else if (s.getCcount().compareTo(new BigInteger("500000"))>=0){
            goal = new BigInteger("4000");
        }else {goal = new BigInteger("500");}
        name = "Klicke " + goal + " mal auf den Keks";
        progress = new BigInteger("0");
        boni = s.getCcount().divide(new BigInteger("20"));
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
        progress=progress.add(new BigInteger("1"));
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
        goal= s.getCcount().add(s.getClickspersecond().multiply(new BigInteger(Integer.toString(50*60)))).add(new BigInteger("500"));
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = new BigInteger("2");
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
        goal= s.getCcount().add(s.getClickspersecond().multiply(new BigInteger(Integer.toString(120*60)))).add(new BigInteger("500"));
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = new BigInteger("1");
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
        state.incMilk(Integer.parseInt(boni.toString()));
    }
}

