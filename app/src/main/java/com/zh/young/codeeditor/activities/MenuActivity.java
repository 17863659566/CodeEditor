package com.zh.young.codeeditor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.zh.young.codeeditor.R;

import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MenuActivity.this, EditActivity.class));
            }
        };
        timer.schedule(timerTask,1000);
    }
}
