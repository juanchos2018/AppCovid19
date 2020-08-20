package com.apk.appinfocovid19.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apk.appinfocovid19.R;

import java.util.Timer;
import java.util.TimerTask;

public class ResuladoActivity extends AppCompatActivity {


    int porcentaje=0;
    ProgressBar progressbar;
    TextView txtvalor;

    int contador1=0;
    int termino=0;
    TextView txtrespuesta;

    Button btncerar,btnreportar;
  //  int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulado);

        porcentaje=getIntent().getIntExtra("porcentaje",0);
        termino=getIntent().getIntExtra("porcentaje",0);
        progressbar=findViewById(R.id.progressBarCinco);

        btncerar=(Button)findViewById(R.id.btncerrar);
        btnreportar=(Button)findViewById(R.id.btnreportar);
        txtrespuesta=(TextView)findViewById(R.id.txtrespuesta);
        txtvalor=(TextView)findViewById(R.id.txtvalor);

        progressbar.setMax(100);

        final Timer t = new Timer();

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                contador1++;
                progressbar.setProgress(contador1);
             //   txtvalor.setText("" +conttador);
                UpdateTime();
                if(contador1==porcentaje){
                    t.cancel();
                    //mensaje();
                }
            }


        };
        t.schedule(tt,0,40);


        btncerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnreportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ResuladoActivity.this,ReportarCasoActivity.class));

            }
        });

    }

    private void mensaje(int resultado) {

        if (resultado>45){
            txtrespuesta.setText("Tu situación de salud debe ser revisada por un profesional.");
        }
        else{
            txtrespuesta.setText("   La probabilidad de que usted tenga Covid-19 es baja");
        }
       // Toast.makeText(this, "terino", Toast.LENGTH_SHORT).show();
    }

    public void UpdateTime()
    {
     //   final int contador=0;
        runOnUiThread(new Runnable()
        {
            public void run()
            {

                txtvalor.setText( "" + contador1 +" %" );
                if (termino==contador1){
                    mensaje(termino);
                }

            }
        });




    }


}
