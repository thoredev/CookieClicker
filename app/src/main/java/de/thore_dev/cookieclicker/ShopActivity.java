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

        Button btnMultUp = findViewById(R.id.button3);
        btnMultUp.setText("Multiplizierer-Upgrade für: " + (int)(1000*Math.pow(1.5, MainActivity.multiplier-1)));
        btnMultUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.ccount >= (int)(1000*Math.pow(1.5, MainActivity.multiplier-1))){
                    MainActivity.ccount -= (int)(1000*Math.pow(1.5, MainActivity.multiplier-1));
                    MainActivity.multiplier++;

                    SharedPreferences.Editor edit = MainActivity.countSettings.edit();
                    edit.putInt("mults",MainActivity.multiplier);
                    edit.putInt("counts",MainActivity.ccount);
                    edit.apply();

                    btnMultUp.setText("Multiplizierer-Upgrade für: " + (int)(1000*Math.pow(1.5, MainActivity.multiplier-1)));
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