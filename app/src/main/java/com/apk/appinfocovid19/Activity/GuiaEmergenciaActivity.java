package com.apk.appinfocovid19.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.apk.appinfocovid19.R;

public class GuiaEmergenciaActivity extends AppCompatActivity {
    Toolbar toolbar;


    View imgwhatsapp,imgnumero,imgnumero2,imgnumero3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia_emergencia);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgwhatsapp=(View)findViewById(R.id.imgwatapp);
        imgnumero=(View)findViewById(R.id.imgnumero);
        imgnumero2=(View)findViewById(R.id.imgnumero2);
        imgnumero3=(View)findViewById(R.id.imgnumero3);


    imgwhatsapp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String telefono="952842623";
            WhataApps(telefono);
        }
    });

    imgnumero.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String  numero="113";
            Telefono(numero);
        }
    });

    imgnumero2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String  numero="106";
            Telefono(numero);
        }
    });

    imgnumero3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String  numero="3156600";
            Telefono(numero);
        }
    });
    }

    private void Telefono(String numero) {
        try {
            if (TextUtils.isEmpty(numero)){
                Toast.makeText(this, "no existe numero de telefono", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+numero));
                startActivity(intent);
            }

        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void WhataApps(String telefono) {
        try {

            if (TextUtils.isEmpty(telefono)){
                Toast.makeText(this, "no hay telefono", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent _intencion = new Intent("android.intent.action.MAIN");
                _intencion.setAction(Intent.ACTION_SEND);
                _intencion.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                _intencion.putExtra("jid", PhoneNumberUtils.stripSeparators("51"+telefono)+"@s.whatsapp.net");
                startActivity(_intencion);
            }

        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
