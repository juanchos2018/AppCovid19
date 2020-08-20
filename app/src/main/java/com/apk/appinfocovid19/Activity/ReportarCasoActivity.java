package com.apk.appinfocovid19.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReportarCasoActivity extends AppCompatActivity {

    RadioGroup radioGrupoTipoCaso, radioGrupoSexo;
    RadioButton radioButton;
    EditText txtApellidosPaciente, txtNombresPaciente, txtEdad, txtCelular, txtDistrito, txtDireccion, txtOtrasObservaciones;
    Button button;
    Toolbar toolbar;

    FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private int currentAnimationFrame = 0;
    private LottieAnimationView animationView;

    private ProgressDialog progressDialog;
    android.app.AlertDialog.Builder builder1;
    AlertDialog alert;
    public static String latiud="0";
    public  static String longi="0";

    private static final String TAG = "ReportarCasoActivity";

    Button btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_caso);
        toolbar = findViewById(R.id.toolbar);

        radioGrupoTipoCaso = findViewById(R.id.radioGrupoTipoCaso);
        radioGrupoSexo =(RadioGroup) findViewById(R.id.radioGrupoSexo);
        txtApellidosPaciente = findViewById(R.id.txtApellidosPaciente);
        txtNombresPaciente = findViewById(R.id.txtNombresPaciente);
        txtEdad = findViewById(R.id.txtEdad);
        txtCelular = findViewById(R.id.txtCelular);
        txtDistrito= findViewById(R.id.txtDistrito);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtOtrasObservaciones = findViewById(R.id.txtOtrasObservaciones);

        button = findViewById(R.id.sendButton);
        btn=(Button)findViewById(R.id.btnubicar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caso="";
                String sexo="";

                int checkgfatiga = radioGrupoTipoCaso.getCheckedRadioButtonId();

                if (checkgfatiga==-1 ){
                    caso="";
                }
                else {
                    if (  checkgfatiga == R.id.radioPropio  ){
                        caso="Propio";
                    }
                    if (checkgfatiga == R.id.radioFamiliar){
                        caso="Familiar";
                    }
                    if (checkgfatiga == R.id.radioConocido){
                        caso="Conocido";
                    }
                }


                int checksexo=radioGrupoSexo.getCheckedRadioButtonId();
                if (checksexo==-1){
                    sexo="";
                }
                else{
                    if (checksexo == R.id.rbdm){
                        sexo="Masculino";
                    }
                    if (checksexo == R.id.rbdf){
                        sexo="Femenino";
                    }
                }

              //  Log.e(TAG, sexo );
              String apellido=txtApellidosPaciente.getText().toString();
              String nombre =txtNombresPaciente.getText().toString();
              String edad= txtEdad.getText().toString();
              String celular=txtCelular.getText().toString();
              String distrito=txtDistrito.getText().toString();
              String direccion = txtDireccion.getText().toString();
              String observacin =txtOtrasObservaciones.getText().toString();


              EnviarData2(caso, nombre,apellido,edad,sexo,celular,distrito,direccion,observacin);
                /*
                if (!ValidarCampo(obj.getTipoCaso())){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ValidarCampo(obj.getApellidoPaciente())){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ValidarCampo(obj.getNombrePaciente())){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ValidarCampo(obj.getSexo())){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ValidarCampo(String.valueOf(obj.getEdad()))){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ValidarCampo(String.valueOf(obj.getCelular()))){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!ValidarCampo(obj.getDistrito())){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ValidarCampo(obj.getDireccion())){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ValidarCampo(obj.getOtrasObservaciones())){
                    Toast.makeText(ReportarCasoActivity.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }


                 */
              //  EnviarData(obj);

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportarCasoActivity.this,UbicacionActivity.class));
            }
        });


    }

    private  void  DialogCheck(){
        builder1 = new AlertDialog.Builder(ReportarCasoActivity.this);
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(ReportarCasoActivity.this).inflate(R.layout.dialogo_ok, null);
        animationView = v.findViewById(R.id.animation_viewcheck);
        resetAnimationView();
        animationView.playAnimation();
        builder1.setView(v);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);

        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        alert  = builder1.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alert.show();

    }
    private void resetAnimationView() {
        currentAnimationFrame = 0;
        animationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return null;
                    }
                }
        );
    }


    public boolean ValidarCampo(String tmp) {
        return !tmp.isEmpty();
    }

                     // EnviarData2(caso, nombre,apellido,edad,celular,distrito,direccion,observacin);

    // EnviarData2(caso,apellido,nombre,edad,celular,distrito,direccion,observacin);
    public  void EnviarData2(String caso,String nombre,String apellido,String edad,String sexo, String celular,String distrito,String direccion,String observacon )
    {

        if (TextUtils.isEmpty(caso)){

            Toast.makeText(this, "Elija tipo", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(nombre)){
            txtNombresPaciente.setError("Campo requerido");

        }
        else if (TextUtils.isEmpty(apellido)){
            txtApellidosPaciente.setError("Campo Requeridi");

        }

        else if (TextUtils.isEmpty(celular)){
                txtCelular.setError("Campo requeridi");
        }

        else if (TextUtils.isEmpty(distrito)){

            txtDistrito.setError("campo requerido");
        }

        else if (TextUtils.isEmpty(direccion)){
            txtDireccion.setError("Campo requerido");
        }

        else if (TextUtils.isEmpty(observacon))
        {

            txtOtrasObservaciones.setError("campo requerido");
        }


        else if (TextUtils.isEmpty(sexo))
        {
            Toast.makeText(this, "elija el sexo", Toast.LENGTH_SHORT).show();
          //  txtOtrasObservaciones.setError("campo requerido");
        }

        else if (TextUtils.isEmpty(edad)){
            txtEdad.setError("Campo requerido");
        }
        else{

            ReporteCaso obj = new ReporteCaso();
            obj.setTipoCaso(caso);
            obj.setApellidoPaciente(apellido);
            obj.setNombrePaciente(nombre);
            obj.setSexo(sexo);
            obj.setEdad(Integer.parseInt(edad));
            obj.setCelular(Integer.parseInt(celular));
            obj.setDistrito(distrito);
            obj.setDireccion(direccion);
            obj.setOtrasObservaciones(observacon);

            SharedPreferences preferences= getSharedPreferences("mitoken", Context.MODE_PRIVATE);
            String token=preferences.getString("token","no existe we");
            obj.setToken(token);

            obj.setLatitud(latiud);
            obj.setLongitud(longi);

            databaseReference = FirebaseDatabase.getInstance().getReference("ReporteCaso");

            String key = databaseReference.push().getKey();
            obj.setKey_caso(key);
            obj.setEstado("0");

            progressDialog = new ProgressDialog(ReportarCasoActivity.this);
            progressDialog.setTitle("Registrando");
            progressDialog.setMessage("Cargando");
            progressDialog.setCancelable(false);
            progressDialog.show();

            databaseReference.child(key).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        progressDialog.dismiss();
                        DialogCheck();
                      //  progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ReportarCasoActivity.this, "ERror", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                }
            });

        }

    }


    public void EnviarData(final ReporteCaso obj) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, env.apiEnviarReporteCaso,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = jsonResponse.getBoolean("status");
                            String mensaje = jsonResponse.getString("message");
                            boolean respuesta;
                            if (status) {
                                Toast.makeText(ReportarCasoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ReportarCasoActivity.this, MenuActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(ReportarCasoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ReportarCasoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String error_msg = error.getMessage();
                        Toast.makeText(ReportarCasoActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tipoCaso",obj.getTipoCaso());
                params.put("apellidoPaciente",obj.getApellidoPaciente());
                params.put("nombrePaciente",obj.getNombrePaciente());
                params.put("sexo",obj.getSexo());
                params.put("edad",String.valueOf(obj.getEdad()));
                params.put("celular",String.valueOf(obj.getCelular()));
                params.put("distrito",obj.getDistrito());
                params.put("direccion",obj.getDireccion());
                params.put("otrasObservaciones",obj.getOtrasObservaciones());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ReportarCasoActivity.this);
        requestQueue.add(stringRequest);
    }

    public String obtenerValorRadioButton(int radioId) {
        radioButton = findViewById(radioId);
        return radioButton.getText().toString();
    }
}