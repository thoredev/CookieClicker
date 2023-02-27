package de.thore_dev.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
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
        btnMultUp.setText("Multiclick: " + gameState.BigIntToSuffixString(gameState.getMultiplier().multiply(new BigInteger("1000")))
                            + "\nLevel: " + gameState.BigIntToSuffixString(gameState.getMultiplier()));
        btnMultUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameState.getCcount().compareTo(gameState.getMultiplier().multiply(new BigInteger("1000"))) >= 0){
                    gameState.incCcount(gameState.getMultiplier().multiply(new BigInteger("-1000")));
                    gameState.setMultiplier(gameState.getMultiplier().add(new BigInteger("1")));

                    btnMultUp.setText("Multiclick: " + gameState.BigIntToSuffixString(gameState.getMultiplier().multiply(new BigInteger("1000")))
                                         + "\nLevel: " + gameState.BigIntToSuffixString(gameState.getMultiplier()));
                }
            }
        });

        Button btnAutoclick = findViewById(R.id.button5);
        btnAutoclick.setText("Autoclick: " + gameState.BigIntToSuffixString(gameState.getClickspersecond().multiply(new BigInteger("1000")).add(new BigInteger("2500")))
                            + "\nLevel: " + gameState.BigIntToSuffixString(gameState.getClickspersecond()));
        btnAutoclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameState.getCcount().compareTo(gameState.getClickspersecond().multiply(new BigInteger("1000")).add(new BigInteger("2500"))) >= 0){
                    gameState.incCcount(gameState.getClickspersecond().multiply(new BigInteger("1000")).add(new BigInteger("2500")).multiply(new BigInteger("-1")));
                    gameState.setClickspersecond(gameState.getClickspersecond().add(new BigInteger("1")));

                    btnAutoclick.setText("Autoclick: " + gameState.BigIntToSuffixString(gameState.getClickspersecond().multiply(new BigInteger("1000")).add(new BigInteger("2500")))
                            + "\nLevel: " + gameState.BigIntToSuffixString(gameState.getClickspersecond()));
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
                if(gameState.getMilk() > 0) {
                    gameState.incMilk(-1);
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
                        gameState.incRarenc(new BigInteger(Integer.toString(rarenc)));
                    } else {
                        int ccount = 500 + R.nextInt(1000);
                        textGift.setText("Du hast " + Integer.toString(ccount) + " Kekssplitter gewonnen!");
                        gameState.incCcount(new BigInteger(Integer.toString(ccount)));

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
                                gameState.incRarenc(new BigInteger(Integer.toString(rarenc)));
                            } else {
                                int ccount = 5000 + R.nextInt(10000);
                                textGift.setText("Du hast " + Integer.toString(ccount) + " Kekssplitter gewonnen!");
                                gameState.incCcount(new BigInteger(Integer.toString(ccount)));

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
                                gameState.incRarenc(new BigInteger(Integer.toString(rarenc)));
                            } else if (eventID>=9 && eventID <= 20) {
                                int ccount = 5000 + R.nextInt(10000);
                                textGift.setText("Du hast " + Integer.toString(ccount) + " Kekssplitter gewonnen!");
                                gameState.incCcount(new BigInteger(Integer.toString(ccount)));

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
                                if (gameState.getRarenc().compareTo(new BigInteger("40")) >= 0) {
                                    int rarenc = -40;                          // gibt es einen anderen weg die verlorenen kekse wieder variabel zumachen
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir 40 deiner ganzen Kekse weggegessen!");
                                    gameState.incRarenc(new BigInteger(Integer.toString(rarenc)));
                                } else {
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat dir den Rest deiner ganzen Kekse weggegessen!");
                                    gameState.incRarenc(gameState.getRarenc().multiply(new BigInteger("-1")));

                                }
                            } else {
                                    textGift.setText("!OH Nein! \n Das Krümelmonster hat "+gameState.BigIntToSuffixString(gameState.getCcount().divide(new BigInteger("-5")))+" deiner Keksesplitter weggegessen!");
                                    gameState.incCcount(gameState.getCcount().divide(new BigInteger("-5")));
                            }
                            }
                        }
        });
    }
}