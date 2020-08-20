package com.apk.appinfocovid19.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.apk.appinfocovid19.R;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
     int contador=0;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    contador++;
                    Log.e(TAG,String.valueOf(contador) );
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();
            }
        }).start();
    }
}
