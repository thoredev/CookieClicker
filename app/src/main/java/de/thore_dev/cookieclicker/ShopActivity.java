package de.thore_dev.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class ShopActivity extends AppCompatActivity {

    static boolean activitySwitch;

    @Override
    public void onStop() {
        super.onStop();
        if(!activitySwitch){
            finishAffinity(); //Close App
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        activitySwitch = false;

        GameState gameState = new GameState(getSharedPreferences("gameState", 0));

        Button btnMultUp = findViewById(R.id.button3);
        btnMultUp.setText("Multiclick: " + (int)(1000*Math.pow(1.5, gameState.getMultiplier()-1))
                            + "\nLevel: " + (int)(gameState.getMultiplier()));
        btnMultUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameState.getCcount()>= (int)(1000*Math.pow(1.5, gameState.getMultiplier()-1))){
                    gameState.incCcount(-1* (int)(1000*Math.pow(1.5, gameState.getMultiplier()-1)));
                    gameState.setMultiplier(gameState.getMultiplier()+1);

                    btnMultUp.setText("Multiclick: " + (int)(1000*Math.pow(1.5, gameState.getMultiplier()-1))
                                         + "\nLevel: " + (int)(gameState.getMultiplier()));
                }
            }
        });

        Button btnAutoclick = findViewById(R.id.button5);
        btnAutoclick.setText("Autoclick: " + (2500+1000* gameState.getClickspersecond())
                            + "\nLevel: " + (int)(gameState.getClickspersecond()) );
        btnAutoclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameState.getCcount()>= 2500+1000* gameState.getClickspersecond()){
                    gameState.incCcount(-1*(2500+1000* gameState.getClickspersecond()));
                    gameState.setClickspersecond(gameState.getClickspersecond()+1);

                    btnAutoclick.setText("Autoclick: " + (2500+1000* gameState.getClickspersecond())+
                            "\nLevel: " + (int)(gameState.getClickspersecond()));
                }
            }
        });

        Button btnBack = findViewById(R.id.button4);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySwitch = true;
                Intent switchActivity = new Intent(ShopActivity.this, GameActivity.class);
                startActivity(switchActivity);
            }
        });


        TextView textGift = findViewById(R.id.textGift);
        Button btnNormal = findViewById(R.id.buttonChestNormal);
        Button btnOP = findViewById(R.id.buttonChestOP);
        Button btnDanger = findViewById(R.id.buttonChestDanger);
        ConstraintLayout fortuneCookieLayout = findViewById(R.id.layoutFortuneCookie);
        ImageButton fortuneCookie = findViewById(R.id.fortuneCookie);

        fortuneCookieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fortuneCookieLayout.setVisibility(View.INVISIBLE);
            }
        });


        fortuneCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fortuneCookieLayout.setVisibility(View.INVISIBLE);
            }
        });
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNormal.setText("Normal \n Kosten: 1");
                gameState.incMilk(-1);
                fortuneCookieLayout.setVisibility(View.VISIBLE);
                Random R = new Random();
                int eventID = R.nextInt(20);
                if(eventID == 0){
                    int milk2 = 2;
                    textGift.setText("Du hast "+Integer.toString(milk2) + " Milchgläser gewonnen!");
                    gameState.incMilk(milk2);
                } else if(eventID == 1){
                    int milk1 = 1;
                    textGift.setText("Du hast " +Integer.toString(milk1) + " Milchglas gewonnen!");
                    gameState.incMilk(milk1);
                } else if(eventID >= 2 && eventID <= 8){
                    int rarenc = 5+R.nextInt(35);
                    textGift.setText("Du hast "+Integer.toString(rarenc) + " ganze Kekse gewonnen!");
                    gameState.incRarenc(rarenc);
                } else {
                    int ccount = 500+R.nextInt(1000);
                    textGift.setText("Du hast " + Integer.toString(ccount) + " Kekssplitter gewonnen!");
                    gameState.incCcount(ccount);

                }
            }
        });
        btnOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fortuneCookieLayout.setVisibility(View.VISIBLE);
            }
        });
        btnDanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fortuneCookieLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}