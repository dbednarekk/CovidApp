package com.aplikacja.covidprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WashHends extends AppCompatActivity {
    private ImageView wiewy;
    private TextView countdownText;
    private Button contentButton;
            private CountDownTimer countDownTimer;
    private long timeLeftInMillisecound = 60000; //1 minute
    private boolean timerun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_hends);
        countdownText = findViewById(R.id.TextTime1);
        contentButton = findViewById(R.id.StartTimeBtn);
        wiewy = findViewById(R.id.imageView3);
        wiewy.setImageResource(R.drawable.mycie1);
        contentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startStop();
            }
        });

    }
    public void startStop(){
        if(timerun){
            stopTimer();
        }else{
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillisecound, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillisecound = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        contentButton.setText("Stop");
        timerun = true;
    }

    public void stopTimer() {
    countDownTimer.cancel();
        contentButton.setText("Start");
    timerun = false;
    }

    public void updateTimer(){
        int minutes = (int) timeLeftInMillisecound/60000;
        int secounds = (int) timeLeftInMillisecound % 60000 / 1000;

        String timeleftText;
        timeleftText = "" + minutes;
        timeleftText += ":";
        if(secounds < 10) timeleftText += "0";
        timeleftText += secounds;

        countdownText.setText(timeleftText);
        if(secounds == 54 )  wiewy.setImageResource(R.drawable.mycie2);
        if(secounds == 48 )  wiewy.setImageResource(R.drawable.mycie3);
        if(secounds == 42 )  wiewy.setImageResource(R.drawable.mycie4);
        if(secounds == 36 )  wiewy.setImageResource(R.drawable.mycie5);
        if(secounds == 30 )  wiewy.setImageResource(R.drawable.mycie6);
        if(secounds == 24 )  wiewy.setImageResource(R.drawable.mycie7);
        if(secounds == 18 )  wiewy.setImageResource(R.drawable.mycie8);
        if(secounds == 12 )  wiewy.setImageResource(R.drawable.mycie9);
        if(secounds == 6 )  wiewy.setImageResource(R.drawable.mycie10);
    }
}