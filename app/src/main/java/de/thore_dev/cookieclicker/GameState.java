package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;

public class GameState {
    private SharedPreferences pref;

    private int ccount;

    private int multiplier;

    private int clickspersecond;

    public GameState(SharedPreferences v){
        pref = v;
        ccount = pref.getInt("ccount",0);
        multiplier = pref.getInt("multiplier",1);
        clickspersecond = pref.getInt("clickspersecond",0);
    }


    public int getCcount(){
        return ccount;
    }

    public void setCcount(int v){
        ccount = v;
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("ccount",ccount);
        edit.apply();
    }

    public void incCcount(int v){
        ccount+=v;
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("ccount",ccount);
        edit.apply();
    }


    public int getMultiplier(){
        return multiplier;
    }

    public void setMultiplier(int v){
        multiplier = v;
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("multiplier",multiplier);
        edit.apply();
    }


    public int getClickspersecond(){
        return clickspersecond;
    }

    public void setClickspersecond(int v){
        clickspersecond = v;
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("clickspersecond",clickspersecond);
        edit.apply();
    }


}