package de.thore_dev.cookieclicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import de.thore_dev.cookieclicker.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    static Timer t;

    static GameState gameState;

    public void initTimer(TextView tv){
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
                        }
                    });

                }
            }, 0, 1000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        t.cancel();
        t = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        TextView tv = findViewById(R.id.textView2);
        initTimer(tv);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                        button.setImageDrawable(getResources().getDrawable(R.drawable.cookie_dark));
                        gameState.incCcount(gameState.getMultiplier());
                        tv.setText(Integer.toString(gameState.getCcount()));
                    }

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
                Intent switchActivity = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(switchActivity);
              
            }
        });
    }
}