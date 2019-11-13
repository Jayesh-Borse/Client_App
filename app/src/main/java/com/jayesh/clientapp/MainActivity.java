package com.jayesh.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
public int a = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar progressBar =findViewById(R.id.progressBar);
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                a++;
                progressBar.setProgress(a);
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
            }
        }.start();
    }
}
