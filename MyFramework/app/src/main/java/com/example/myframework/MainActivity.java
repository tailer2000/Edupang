package com.example.myframework;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ProgressBar prog = null;
    Timer timer = null;
    TimerTask timerTask = null;
    int countdown;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        try {
            setContentView(new GameView(this));

        } catch (Exception e) {
            e.printStackTrace();
        }

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        AppManager.getInstance().setSize(width, height);

        //prog = findViewById(R.id.progressBar);
        //prog.setBackground(ContextCompat.getDrawable(this, R.drawable.android));
        //initProg();
        //startTimerThread();
    }

    public void initProg(){
        prog.setMax(countdown);
        prog.setProgress(countdown);
    }

    public void startTimerThread(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                decreaseBar();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    public void decreaseBar(){
        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                countdown = prog.getProgress();
                if(countdown > 0){
                    countdown = countdown - 1;
                }else if(countdown == 0){
                    timer.cancel();
                    Thread.interrupted();
                    }
                 prog.setProgress(countdown);
                 }
             }
        );
    }
}
