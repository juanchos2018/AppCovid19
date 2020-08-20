package com.apk.appinfocovid19.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apk.appinfocovid19.Config.env;
import com.apk.appinfocovid19.Model.Diagnostico;
import com.apk.appinfocovid19.Model.ReporteCaso;
import com.apk.appinfocovid19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DiagnosticoActivity extends AppCompatActivity {

    RadioGroup feelradioGroup, fevelradioGroup, coughradioGroup, fatigueradioGroup, breatheadioGroup, whereareadioGroup;
    Button sendButton;
    Toolbar toolbar;
    RadioButton radioButton;
    EditText edtmaildisplay, edtbodytempdisplay;

    double temperaturas;
    int resultado=0;
    Runnable runnable;
    private Handler handler;
     int conttador=0;
    FirebaseStorage storage;
    private DatabaseReference databaseReference;
    RadioGroup radioGroup;

    android.app.AlertDialog.Builder builder1;
    AlertDialog alert;
    TextView txtvalor1;
    boolean termino=true;

    private static final String TAG = "DiagnosticoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);
        toolbar = findViewById(R.id.toolbar);

        edtbodytempdisplay = findViewById(R.id.edtbodytempdisplay);
        feelradioGroup = findViewById(R.id.feelradioGroup);
        fevelradioGroup = findViewById(R.id.fevelradioGroup);
        coughradioGroup = findViewById(R.id.coughradioGroup);
        fatigueradioGroup = findViewById(R.id.fatigueradioGroup);
        breatheadioGroup = findViewById(R.id.breatheadioGroup);
        whereareadioGroup = findViewById(R.id.whereareadioGroup);


         radioGroup = (RadioGroup)findViewById(R.id.coughradioGroup);
        sendButton = findViewById(R.id.sendButton);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp =edtbodytempdisplay.getText().toString();
                String nasal="";

                int checnasal = feelradioGroup.getCheckedRadioButtonId();

                if (checnasal==-1 ){
                    nasal="";
                }
                else {
                    if (  checnasal == R.id.hanrbtn  ){
                        nasal="si";
                    }
                    if (checnasal == R.id.notwellrbtn){
                        nasal="no";
                    }

                }


                String indegestion="";
                int checkindigestion =fevelradioGroup.getCheckedRadioButtonId();
                if (checkindigestion==-1){
                    indegestion="";
                 //   indigestion_total=0;
                }
                else{
                    if (  checkindigestion == R.id.indi1  ){
                        indegestion="si";
                      //  indigestion_total=10;
                    }
                    if (checkindigestion == R.id.indi2){
                        indegestion="no";
                       // indigestion_total=0;
                    }
                }

                String tos="";
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == -1) {
                    tos="";
                }
                else{
                    if (checkedRadioButtonId == R.id.rbd1) {

                        tos="no";
                    }
                    if (  checkedRadioButtonId == R.id.rbd2  ){
                        tos="tseca";
                    }
                    if (checkedRadioButtonId == R.id.rbd3){
                        tos="tflema";
                    }
                }

                  String fatiga="";

                int checkgfatiga = fatigueradioGroup.getCheckedRadioButtonId();

                if (checkgfatiga==-1 ){
                    fatiga="";
                }
                else {
                    if (  checkgfatiga == R.id.rbdfatiga1  ){
                        fatiga="si";
                    }
                    if (checkgfatiga == R.id.rbdfatiga2){
                        fatiga="no";
                    }
                }
                String aliento="";
                int checkalimento =breatheadioGroup.getCheckedRadioButtonId();
                if ( checkalimento==-1){
                    aliento="";
                }
                else{
                    if (  checkalimento == R.id.yesrbtn  ){
                        aliento="si";
                    }
                    if (checkalimento == R.id.norbtn){
                        aliento="no";
                    }
                }

                String gusto="";
                int checkgusto_olfato=whereareadioGroup.getCheckedRadioButtonId();
                if ( checkgusto_olfato==-1){
                    gusto="";

                }
                else{
                    if (  checkgusto_olfato == R.id.rbdgusto  ){

                        gusto="si";
                    }
                    if (checkgusto_olfato == R.id.rbdolfato){

                        gusto="no";
                    }
                }
                CalcularResultado(temp,nasal,indegestion,tos,fatiga,aliento,gusto);

                // falta validar campos

             //   sendButton.setEnabled(false);

                /*
                Diagnostico obj = new Diagnostico();

                obj.setTemperaturaCorporal(edtbodytempdisplay.getText().toString());
                obj.setCongestionNasal(obtenerValorRadioButton(feelradioGroup.getCheckedRadioButtonId()));
                obj.setIndigestionEstomacal(obtenerValorRadioButton(fevelradioGroup.getCheckedRadioButtonId()));
                obj.setTieneTos(obtenerValorRadioButton(coughradioGroup.getCheckedRadioButtonId()));
                obj.setFatiga(obtenerValorRadioButton(fatigueradioGroup.getCheckedRadioButtonId()));
                obj.setFaltaAlimento(obtenerValorRadioButton(breatheadioGroup.getCheckedRadioButtonId()));
                obj.setPerdidaGusto(obtenerValorRadioButton(whereareadioGroup.getCheckedRadioButtonId()));


                if (!ValidarCampo(obj.getTemperaturaCorporal())) {
                    Toast.makeText(DiagnosticoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                 */
                //EnviarData(obj);


             //   CalcularResultado(te);

            }
        });
    }

    private void  CalcularResultado(String tempe,String nasal,String indigestion,String tos,String fatiga,String aliento,String gusto){

        if (TextUtils.isEmpty(tempe)){
            edtbodytempdisplay.setError("cmapo requerido");

        }
        else if (TextUtils.isEmpty(nasal)){
            Toast.makeText(this, "elija la opcion", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(indigestion)){
            Toast.makeText(this, "elija tipo indigestion", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tos)   ){
            Toast.makeText(this, "Elija Tos", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(fatiga)){
            Toast.makeText(this, "Elija Fatifa", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(aliento)){
            Toast.makeText(this, "Elija tipo Aliento", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(gusto)){
            Toast.makeText(this, "Elijael gusto", Toast.LENGTH_SHORT).show();
        }
        else{

        double temperatura =Double.parseDouble(tempe);

        double porcentaje_Temperatura=0;
        double congestion_nasal_total=0;
        double indigestion_total=0;
        double tos_total=5;
        double fatiga_total=0;
        double alimento_total=0;
        double gusto_total=0;

        double Total_Resultado=0;

        double temperaturaNormal_desde=36.7;
        double temperaturaNormal_hasta=37.7;

        double temperaturaModerada_desde=37.8;
        double temperaturaModerada_hasta=38.5;

        double temperaturaAlta_desde=38.6;
        double temperaturaAlta_hasta=39;


        if (temperatura<temperaturaNormal_hasta  && temperatura > temperaturaNormal_desde){
            porcentaje_Temperatura=3;
         //   Toast.makeText(this, "temperatura 3 %", Toast.LENGTH_SHORT).show();
        }

        else if(temperatura<temperaturaModerada_hasta && temperatura > temperaturaModerada_desde){
            porcentaje_Temperatura=7;
        }
        else if (temperatura<temperaturaAlta_hasta && temperatura >=temperaturaAlta_desde)
        {
            porcentaje_Temperatura=10;
        }
        else if (temperatura> temperaturaAlta_hasta){
            porcentaje_Temperatura=13;
        }

        else {
           // Toast.makeText(this, "fuera de limite", Toast.LENGTH_SHORT).show();
        }

        if (nasal.equals("si")){
            congestion_nasal_total=10;
        }

        if (indigestion.equals("si")){
            indigestion_total=10;
        }
        if (tos.equals("tseca")){
            tos_total=10;
        }
        if (tos.equals("tflema")){
                tos_total=10;
         }


        if (fatiga.equals("si")){
            fatiga_total=10;


        }
        if (aliento.equals("si")){
            alimento_total=20;
        }

        if (gusto.equals("si")){
            gusto_total=10;
        }

/*
        int checknasa=feelradioGroup.getCheckedRadioButtonId();
        if (checknasa==-1){

        }else{
            if (  checknasa == R.id.hanrbtn  ){
                congestion_nasal_total=10;
            }
            if (checknasa == R.id.notwellrbtn){
                congestion_nasal_total=0;
            }
        }


        int checkindigestion =fevelradioGroup.getCheckedRadioButtonId();
        if (checkindigestion==-1){
            indigestion_total=0;
        }
        else{
            if (  checkindigestion == R.id.indi1  ){
                indigestion_total=10;
            }
            if (checkindigestion == R.id.indi2){
                indigestion_total=0;
            }
        }

        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            tos_total=0;
        }
        else{
            if (checkedRadioButtonId == R.id.rbd1) {
                // Do something with the button
                tos_total=5;
            }
            if (  checkedRadioButtonId == R.id.rbd2  ){
                tos_total=10;
            }
            if (checkedRadioButtonId == R.id.rbd3){
                tos_total=5;
            }
        }



        int checkgfatiga = fatigueradioGroup.getCheckedRadioButtonId();

        if (checkgfatiga==-1 ){
         fatiga_total=0;
        }
        else {
            if (  checkgfatiga == R.id.rbdfatiga1  ){
                fatiga_total=10;
            }
            if (checkgfatiga == R.id.rbdfatiga2){
                fatiga_total=0;
            }
        }

        int checkalimento =breatheadioGroup.getCheckedRadioButtonId();
        if ( checkalimento==-1){
            alimento_total=0;
        }
        else{
            if (  checkalimento == R.id.yesrbtn  ){
                alimento_total=20;
            }
            if (checkalimento == R.id.norbtn){
                alimento_total=0;
            }
        }
        int checkgusto_olfato=whereareadioGroup.getCheckedRadioButtonId();
        if ( checkgusto_olfato==-1){
            gusto_total=0;

        }
        else{
            if (  checkgusto_olfato == R.id.rbdgusto  ){
                gusto_total=10;
            }
            if (checkgusto_olfato == R.id.rbdolfato){
                gusto_total=0;
            }
        }

 */
        Log.e(TAG, " \n temrepatura : "+ String.valueOf(temperatura)+ ",\n congestion nasal : " +String.valueOf(congestion_nasal_total) +"\n digeston Estomacal  : " + String.valueOf(indigestion_total) +" ,\n Tos : " +String.valueOf(tos_total) +" ,\n fatiga: " +String.valueOf(fatiga_total) +" \n,aliento :"+String.valueOf(alimento_total) +", '\n Perdodagusto :"+String.valueOf(gusto_total)  );
    //    int total =porcentaje_Temperatura+congestion_nasal_total+indigestion_total+tos_total+fatiga_total+alimento_total+gusto_total;
        Total_Resultado=porcentaje_Temperatura+congestion_nasal_total+indigestion_total+tos_total+fatiga_total+alimento_total+gusto_total;


      //  mensaje2(Total_Resultado);
        irnactitrivut(Total_Resultado);

        }
    }

    private void irnactitrivut(double i) {

        final int a = (int) Math.round(i);
        Intent intent = new Intent(DiagnosticoActivity.this,ResuladoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("porcentaje",a);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public  void  mensaje2(final double resultado){
      //  final int contador=0;
        final ProgressBar progressbar1;
        final TextView txtrespuesta;
        Button btncerrar,btnreportar;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
         View v = LayoutInflater.from(DiagnosticoActivity.this).inflate(R.layout.dialogo_resultado, null);
        progressbar1=v.findViewById(R.id.progressBarCinco);
        txtvalor1=(TextView)v.findViewById(R.id.txtvalor);
        btncerrar=(Button)v.findViewById(R.id.btncerrar);
        btnreportar=(Button)v.findViewById(R.id.btnreportar);
        txtrespuesta=(TextView)v.findViewById(R.id.txtrespuesta);

        builder.setView(v);
        final int a = (int) Math.round(resultado);

        for ( int i =0;i<a;  i++){

            handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run() {

                    progressbar1.setProgress(a);
                    txtvalor1.setText("" +resultado+ " %");

                    if (a>45){
                        txtrespuesta.setText("Tu situación de salud debe ser revisada por un profesional.");
                    }
                    else{
                        txtrespuesta.setText("   La probabilidad de que usted tenga Covid-19 es baja");
                    }

                }
            }, 2000);
      }


        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    alert.dismiss();

            }
        });

        btnreportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnosticoActivity.this,ReportarCasoActivity.class));

            }
        });


        alert = builder.create();
        alert.show();


    }



    public void EnviarData(final Diagnostico obj) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, env.apiEnviarDiagnostico,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = jsonResponse.getBoolean("status");
                            String mensaje = jsonResponse.getString("message");
                            boolean respuesta;
                            if (status) {
                                Toast.makeText(DiagnosticoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(DiagnosticoActivity.this, MenuActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(DiagnosticoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(DiagnosticoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String error_msg = error.getMessage();
                        Toast.makeText(DiagnosticoActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("temperaturaCorporal", obj.getTemperaturaCorporal());
                params.put("congestionNasal", obj.getCongestionNasal());
                params.put("indigestionEstomacal", obj.getIndigestionEstomacal());
                params.put("tieneTos", obj.getTieneTos());
                params.put("fatiga", obj.getFatiga());
                params.put("faltaAlimento", obj.getFaltaAlimento());
                params.put("perdidaGusto", obj.getPerdidaGusto());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DiagnosticoActivity.this);
        requestQueue.add(stringRequest);
    }

    public boolean ValidarCampo(String tmp) {
        return !tmp.isEmpty();
    }

    public String obtenerValorRadioButton(int radioId) {
        radioButton = findViewById(radioId);
        return radioButton.getText().toString();
    }
}