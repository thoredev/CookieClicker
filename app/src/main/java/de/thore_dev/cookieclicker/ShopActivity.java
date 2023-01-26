package de.thore_dev.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        GameState gameState = new GameState(getSharedPreferences("gameState", 0));

        Button btnMultUp = findViewById(R.id.button3);
        btnMultUp.setText("Multiclick: " + (int)(1000*Math.pow(1.5, gameState.getMultiplier()-1)));
        btnMultUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameState.getCcount()>= (int)(1000*Math.pow(1.5, gameState.getMultiplier()-1))){
                    gameState.incCcount(-1* (int)(1000*Math.pow(1.5, gameState.getMultiplier())-1));
                    gameState.setMultiplier(gameState.getMultiplier()+1);

                    btnMultUp.setText("Multiclick: " + (int)(1000*Math.pow(1.5, gameState.getMultiplier()-1)));
                }
            }
        });

        Button btnAutoclick = findViewById(R.id.button5);
        btnAutoclick.setText("Autoclick: " + (2500+1000* gameState.getMultiplier()));
        btnAutoclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameState.getCcount()>= 2500+1000* gameState.getClickspersecond()){
                    gameState.incCcount(-1*(2500+1000* gameState.getClickspersecond()));
                    gameState.setClickspersecond(gameState.getClickspersecond()+1);

                    btnAutoclick.setText("Autoclick: " + (2500+1000* gameState.getMultiplier()));
                }
            }
        });

        Button btnBack = findViewById(R.id.button4);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(switchActivity);
            }
        });
    }
}