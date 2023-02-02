package de.thore_dev.cookieclicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ui.AppBarConfiguration;

import de.thore_dev.cookieclicker.databinding.ActivityGameBinding;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameBinding binding;

    static boolean activitySwitch;

    //Anzahl der geklickten Cookies in einer Sekunde
    static int userCps;

    static int userClicks = 0;

    static boolean holdButton = false;

    static Task task;

    static Timer t;

    static GameState gameState;

    public void initTimer(TextView tv, TextView tv2, TextView tv3, ProgressBar pbEasy){
        if(t == null) {
            t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    gameState.incCcount(gameState.getClickspersecond());
                    if(gameState.getClickspersecond()>0){
                        userClicks += gameState.getClickspersecond();
                    }
                    if (holdButton){
                        userClicks++;
                        gameState.incCcount(gameState.getMultiplier());
                        userCps += gameState.getMultiplier(); // anzahl zu cps hinzufügen
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(userClicks>=1000){
                                userClicks -= 1000;
                                gameState.incRarenc(1);
                                tv3.setText(Integer.toString(gameState.getRarenc()));//schreibt rarenc in das textfeld
                            }
                            tv.setText(Integer.toString(gameState.getCcount()));
                            tv2.setText(gameState.getClickspersecond()+userCps + " CPS");
                            userCps = 0;

                            task.update();
                            pbEasy.setProgress(task.getProgress());
                        }
                    });

                }
            }, 0, 1000);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        t.cancel();
        t = null;

        task.save(getSharedPreferences("tasks", 0), 0, 0);
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
        TextView tv3 = findViewById(R.id.TextNormalerKeks);
        ProgressBar pbEasy = findViewById(R.id.progressBarEasy);
        initTimer(tv, tv2, tv3, pbEasy);
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

        TextView tv3 = findViewById(R.id.TextNormalerKeks);
        tv3.setText(Integer.toString(gameState.getRarenc()));

        TextView tvEasy = findViewById(R.id.textEasy);
        task = new Task_Easy_Click(gameState);
        int type = getSharedPreferences("tasks", 0).getInt(Integer.toString(0)+"_type",-1);
        if(type!=-1){
            task.load(getSharedPreferences("tasks", 0), 0);
        }
        tvEasy.setText(task.getName());
        tvEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(task.isCompleted()){
                    task.finish();
                    task = new Task_Easy_Click(gameState);
                    tvEasy.setText(task.getName());
                }
            }
        });

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
                        holdButton = true;
                        userClicks++;
                        userCps += gameState.getMultiplier(); // cps hochzählen
                        button.setImageDrawable(getResources().getDrawable(R.drawable.normalerkeksdunkel));// setzt bild aud cookie dark
                        gameState.incCcount(gameState.getMultiplier()); // ccount hoch zählen
                        tv.setText(Integer.toString(gameState.getCcount()));
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    holdButton = false;
                    button.setImageDrawable(getResources().getDrawable(R.drawable.normalerkeks));
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

        Button taskButton = findViewById(R.id.taskButton);
        ConstraintLayout taskView = findViewById(R.id.taskView);
        taskView.setVisibility(View.INVISIBLE);
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskView.getVisibility() == View.VISIBLE){
                    taskView.setVisibility(View.INVISIBLE);
                } else {
                    taskView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}