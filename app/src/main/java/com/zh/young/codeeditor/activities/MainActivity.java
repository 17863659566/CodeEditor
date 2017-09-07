package com.zh.young.codeeditor.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.zh.young.codeeditor.R;


import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
                finish();
            }
        };
        timer.schedule(timerTask,1000);
    }


}
