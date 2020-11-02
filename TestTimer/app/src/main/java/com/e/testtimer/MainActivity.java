package com.e.testtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    long startTime = 0;
    boolean Sittuation = false;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 10000);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.text);

        FloatingActionButton b = findViewById(R.id.playpause);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FloatingActionButton b = (FloatingActionButton) v;
                if (Sittuation) {
                    Sittuation = false;
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                } else {
                    Sittuation = true;
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setImageResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        FloatingActionButton b = findViewById(R.id.playpause);
        b.setImageResource(R.drawable.ic_baseline_play_arrow_24);
    }

}