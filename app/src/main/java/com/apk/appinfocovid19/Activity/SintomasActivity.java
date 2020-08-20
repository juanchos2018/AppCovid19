package com.apk.appinfocovid19.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apk.appinfocovid19.R;

public class SintomasActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button knowMoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        knowMoreButton = findViewById(R.id.knowMoreButton);
        knowMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SintomasActivity.this, GuiaEmergenciaActivity.class);
                startActivity(i);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
