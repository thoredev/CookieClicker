package de.thore_dev.cookieclicker;

import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;
import java.math.BigInteger;

public class GameState {
    final static String letters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private SharedPreferences pref;
    //TODO: Convert CCount and ClicksperSecond to BigInt; Methode zum Umrechnen: M, B, T, AA, AB, ...
    private BigInteger ccount;

    private BigInteger multiplier;

    private BigInteger clickspersecond;

    private BigInteger rarenc;

    private int milk;

    public String BigIntToSuffixString(BigInteger v){
        String result = v.toString();

        if(v.compareTo(new BigInteger("1000000")) >= 0){
            BigInteger a = v.divide(new BigInteger("1000000"));
            if(a.compareTo(new BigInteger("10")) >= 0) {
                result = a.toString() + "M";
            }
        }

        if(v.compareTo(new BigInteger("1000000000")) >= 0){
            BigInteger a = v.divide(new BigInteger("1000000000"));
            if(a.compareTo(new BigInteger("10")) >= 0) {
                result = a.toString() + "B";
            }
        }

        if(v.compareTo(new BigInteger("1000000000000")) >= 0){
            BigInteger a = v.divide(new BigInteger("1000000000000"));
            if(a.compareTo(new BigInteger("10")) >= 0) {
                result = a.toString() + "T";
            }
        }

        String cur_tens_potency = "1000000000000000";
        for(int l1 = 0; l1 < 26; l1++){
            for(int l2 = 0; l2 < 26; l2++){
                BigInteger a = v.divide(new BigInteger(cur_tens_potency));
                if(v.compareTo(new BigInteger(cur_tens_potency)) >= 0 && a.compareTo(new BigInteger("10")) >= 0){
                    result = a.toString() + letters[l1] + letters[l2];
                    cur_tens_potency += "000";
                } else {
                    break;
                }
            }
        }

        return result;
    }

    public GameState(SharedPreferences v) {
        pref = v;
        ccount = new BigInteger(pref.getString("ccount", "0"));
        multiplier = new BigInteger(pref.getString("multiplier", "1"));
        clickspersecond = new BigInteger(pref.getString("clickspersecond", "0"));
        rarenc =  new BigInteger(pref.getString("rarenc", "0"));
        milk = pref.getInt("milk", 0);
    }

    public void setOfflineTime(Date v) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("offlinedate", v.toString());
        edit.apply();
    }

    public Date getOfflineTime() {
        return new Date(pref.getString("offlinedate", Calendar.getInstance().getTime().toString()));
    }

    public BigInteger getCcount() {
        return ccount;
    }

    public void setCcount(BigInteger v) {
        ccount = v;
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("ccount", ccount.toString());
        edit.apply();
    }

    public void incCcount(BigInteger v) {
        ccount = ccount.add(v);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("ccount", ccount.toString());
        edit.apply();
    }


    public BigInteger getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigInteger v) {
        multiplier = v;
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("multiplier", multiplier.toString());
        edit.apply();
    }


    public BigInteger getClickspersecond() {
        return clickspersecond;
    }

    public void setClickspersecond(BigInteger v) {
        clickspersecond = v;
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("clickspersecond", clickspersecond.toString());
        edit.apply();
    }

    public BigInteger getRarenc() {
        return rarenc;
    }

    public void incRarenc(BigInteger v) {
        rarenc=rarenc.add(v);
        SharedPreferences.Editor edit = pref.edit();  // 3 zeilen speichern wert ab
        edit.putString("rarenc", rarenc.toString());
        edit.apply();
    }

    public int getMilk() {
        return milk;
    }

    public void incMilk(int v) {
        milk += v;
        SharedPreferences.Editor edit = pref.edit();  // 3 zeilen speichern wert ab
        edit.putInt("milk", milk);
        edit.apply();
    }


}