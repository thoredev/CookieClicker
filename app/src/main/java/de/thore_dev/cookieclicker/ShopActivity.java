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
        btnNormal.setText("Normal \n Kosten: 1");
        Button btnOP = findViewById(R.id.buttonChestOP);
        btnOP.setText("Over Power \n Kosten: 10");
        Button btnDanger = findViewById(R.id.buttonChestDanger);
        btnDanger.setText("Risiko \n Kosten: 5");
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
                if(gameState.getRarenc() > 0) {
                    gameState.incRarenc(-1);
                    fortuneCookieLayout.setVisibility(View.VISIBLE);
                    Random R = new Random();
                    int eventID = R.nextInt(20);
                    if (eventID == 0) {
                        int milk2 = 2;
                        textGift.setText("Du hast " + Integer.toString(milk2) + " Milchgläser gewonnen!");
                        gameState.incMilk(milk2);
                    } else if (eventID == 1) {
                        int milk1 = 1;
                        textGift.setText("Du hast " + Integer.toString(milk1) + " Milchglas gewonnen!");
                        gameState.incMilk(milk1);
                    } else if (eventID >= 2 && eventID <= 8) {
                        int rarenc = 5 + R.nextInt(35);
                        textGift.setText("Du hast " + Integer.toString(rarenc) + " ganze Kekse gewonnen!");
                        gameState.incRarenc(rarenc);
                    } else {
                        int ccount = 500 + R.nextInt(1000);
                        textGift.setText("Du hast " + Integer.toString(ccount) + " Kekssplitter gewonnen!");
                        gameState.incCcount(ccount);

                    }
                }
            }
        });






        btnOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fortuneCookieLayout.setVisibility(View.VISIBLE);

                        btnOP.setText("Over Power \n Kosten: 10");
                        if(gameState.getMilk() >= 10) {
                            gameState.incMilk(-10);
                            fortuneCookieLayout.setVisibility(View.VISIBLE);
                            Random R = new Random();
                            int eventID = R.nextInt(20);
                            if (eventID == 0) {
                                int milk2 = 15;
                                textGift.setText("Du hast " + Integer.toString(milk2) + " Milchgläser gewonnen!");
                                gameState.incMilk(milk2);
                            } else if (eventID == 1) {
                                int milk1 = 20;
                                textGift.setText("Du hast " + Integer.toString(milk1) + " Milchglas gewonnen!");
                                gameState.incMilk(milk1);
                            } else if (eventID >= 2 && eventID <= 8) {
                                int rarenc = 50 + R.nextInt(100);
                                textGift.setText("Du hast " + Integer.toString(rarenc) + " ganze Kekse gewonnen!");
                                gameState.incRarenc(rarenc);
                            } else {
                                int ccount = 5000 + R.nextInt(10000);
                                textGift.setText("Du hast " + Integer.toString(ccount) + " Kekssplitter gewonnen!");
                                gameState.incCcount(ccount);

                    }
                };
            }
        });
        btnDanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fortuneCookieLayout.setVisibility(View.VISIBLE);

                        btnDanger.setText("Risiko \n Kosten: 5");
                        if(gameState.getMilk() >= 5) {
                            gameState.incMilk(-5);
                            fortuneCookieLayout.setVisibility(View.VISIBLE);
                            Random R = new Random();
                            int eventID = R.nextInt(40);
                            if (eventID == 0) {
                                int milk2 = 20;
                                textGift.setText("Du hast " + Integer.toString(milk2) + " Milchgläser gewonnen!");
                                gameState.incMilk(milk2);
                            } else if (eventID == 1) {
                                int milk1 = 15;
                                textGift.setText("Du hast " + Integer.toString(milk1) + " Milchglas gewonnen!");
                                gameState.incMilk(milk1);
                            } else if (eventID >= 2 && eventID <= 8) {
                                int rarenc = 50 + R.nextInt(50);
                                textGift.setText("Du hast " + Integer.toString(rarenc) + " ganze Kekse gewonnen!");
                                gameState.incRarenc(rarenc);
                            } else if (eventID>=9 && eventID <= 20) {
                                int ccount = 5000 + R.nextInt(10000);
                                textGift.setText("Du hast " + Integer.toString(ccount) + " Kekssplitter gewonnen!");
                                gameState.incCcount(ccount);

                            } else if (eventID == 21) {
                                if (gameState.getMilk() >= 15) {
                                    int milk2 = -15;
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir 15 Milchgläser weggetrunken!");
                                    gameState.incMilk(milk2);
                                } else {
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir den Rest deiner Milchgläser weggetrunken!");
                                    gameState.incMilk( - gameState.getMilk());
                                }
                            } else if (eventID == 22) {
                                if (gameState.getMilk() >= 7) {
                                    int milk2 = -7;
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir 7 Milchgläser weggetrunken!");
                                    gameState.incMilk(milk2);
                                } else {
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir den Rest deiner Milchgläser weggetrunken!");
                                    gameState.incMilk( - gameState.getMilk());
                                }
                            } else if (eventID >= 23 && eventID <= 29 ) {
                                if (gameState.getRarenc() >= 40) {
                                    int rarenc = -40;                          // gibt es einen anderen weg die verlorenen kekse wieder variabel zumachen
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir 40 deiner ganzen Kekse weggegessen!");
                                    gameState.incRarenc(rarenc);
                                } else {
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir den Rest deiner ganzen Kekse weggegessen!");
                                    gameState.incRarenc(- gameState.getRarenc());

                                }
                            } else {
                                    int ccount = (int) (gameState.getCcount() * 0.2);
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat "+Integer.toString(ccount)+" deiner Keksesplitter weggegessen!");
                                    gameState.incCcount(- ccount);
                            }
                            }
                        }
        });
    }
}