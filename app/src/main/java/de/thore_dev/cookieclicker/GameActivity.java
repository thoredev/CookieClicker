package de.thore_dev.cookieclicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import de.thore_dev.cookieclicker.databinding.ActivityGameBinding;

import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameBinding binding;

    static boolean activitySwitch;

    //Anzahl der geklickten Cookies in einer Sekunde
    static int userClicks;

    static Timer t;

    static GameState gameState;

    public void initTimer(TextView tv, TextView tv2){
        if(t == null) {
            t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    gameState.incCcount(gameState.getClickspersecond());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(Integer.toString(gameState.getCcount()));
                            tv2.setText(gameState.getClickspersecond()+userClicks + " CPS");
                            userClicks = 0;
                        }
                    });

                }
            }, 0, 1000);
        }
    }

   /* public void clickcounterrnc(TextView tv,TextView tv2){  // counter für rare nc
        if (userClicks % 1000 == 0){
            gameState.incRarenc(userClicks);
        }
        @Override
                public void rc(){
        TextView tv = findViewById(R.id.TextNormalerKeks);
        tv.setText(Integer.toString(gameState.getRarenc()));
            tv2.setText(gameState.getClickspersecond()+userClicks);
             = 0;
        }
    }*/

    @Override
    public void onStop() {
        super.onStop();
        t.cancel();
        t = null;

        gameState.setOfflineTime(Calendar.getInstance().getTime());

        if(!activitySwitch){
            finishAffinity(); //Close App and all activities
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        TextView tv = findViewById(R.id.textView2);
        TextView tv2 = findViewById(R.id.textView8);
        initTimer(tv, tv2);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activitySwitch = false;

        gameState = new GameState(getSharedPreferences("gameState", 0));

        TextView tv = findViewById(R.id.textView2);
        tv.setText(Integer.toString(gameState.getCcount()));

        TextView tv2 = findViewById(R.id.textView3);
        tv2.setText(Integer.toString(gameState.getMultiplier())+"x");

        ImageButton button = findViewById(R.id.button);

        button.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = event.getX();
                    float y = event.getY();
                    //Berechne Mittelpunkt des Kekses
                    float m_x = button.getWidth()/2;
                    float m_y = button.getHeight()/2;
                    //Berechne Entfernung zum Mittelpunkt
                    double d = Math.sqrt(Math.pow(Math.abs(m_x-x), 2)+Math.pow(Math.abs(m_y-y), 2));

                    //Prüfe ob Keks angeklickt
                    if(d<=m_x) {
                        userClicks++;
                        button.setImageDrawable(getResources().getDrawable(R.drawable.cookie_dark));// setzt bild aud cookie dark
                        gameState.incCcount(gameState.getMultiplier()); // ccount hoch zählen
                        tv.setText(Integer.toString(gameState.getCcount()));
                    }
                  /* if (event.getAction() == MotionEvent.ACTION_DOWN){
                        gameState.incCcount(gameState.getMultiplier());

                    }*/

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.cookie));
                }

                return true;
            }
        });


        ImageButton btnUpgrades = findViewById(R.id.button2);
        btnUpgrades.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                activitySwitch = true;
                Intent switchActivity = new Intent(GameActivity.this, ShopActivity.class);
                startActivity(switchActivity);
              
            }
        });
    }
}