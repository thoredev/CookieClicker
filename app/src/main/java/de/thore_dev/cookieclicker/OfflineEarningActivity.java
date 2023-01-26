package de.thore_dev.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OfflineEarningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_earning);

        GameState gameState = new GameState(getSharedPreferences("gameState", 0));

        Date curTime = Calendar.getInstance().getTime();
        Date lastTime = gameState.getOfflineTime();
        long diffInMillies = curTime.getTime()- lastTime.getTime();
        long offlineTime = TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
        if(offlineTime>120){
            offlineTime = 120;
        }

        int offEarn = (int)offlineTime*60*gameState.getClickspersecond();

        TextView textView = findViewById(R.id.textView7);
        textView.setText("Du warst " + offlineTime + "/120 Minuten offline\n und hast "+ offEarn + " Kekse verdient.");
        gameState.incCcount(offEarn);

        ImageButton btnContinue = findViewById(R.id.button6);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivity = new Intent(OfflineEarningActivity.this, GameActivity.class);
                startActivity(switchActivity);
            }
        });
    }
}