package de.thore_dev.cookieclicker;

import android.annotation.SuppressLint;
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

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    static int ccount;
    static int multiplier;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences countSettings = getSharedPreferences("count", 0);
        ccount = countSettings.getInt("counts",0);
        multiplier = countSettings.getInt("mults", 1);
        TextView tv = findViewById(R.id.textView2);
        tv.setText(Integer.toString(ccount));

        TextView tv2 = findViewById(R.id.textView3);
        tv2.setText(Integer.toString(multiplier)+"x");

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
                        ccount += multiplier;
                        SharedPreferences.Editor edit = countSettings.edit();
                        edit.putInt("counts", ccount);
                        edit.apply();
                        tv.setText(Integer.toString(ccount));
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.cookie));
                }

                return true;
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setText("Power-Up für: " + (int)(1000*Math.pow(1.5, multiplier-1)));
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(ccount >= (int)(1000*Math.pow(1.5, multiplier-1))){
                    ccount -= (int)(1000*Math.pow(1.5, multiplier-1));
                    tv.setText(Integer.toString(ccount));
                    multiplier++;
                    SharedPreferences.Editor edit = countSettings.edit();
                    edit.putInt("mults",multiplier);
                    edit.apply();
                    tv2.setText(Integer.toString(multiplier)+"x");
                    button2.setText("Power-Up für: " + (int)(1000*Math.pow(1.5, multiplier-1)));
                }
            }
        });
    }
}