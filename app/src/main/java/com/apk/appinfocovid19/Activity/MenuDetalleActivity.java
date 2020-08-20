package com.apk.appinfocovid19.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apk.appinfocovid19.Adapters.AdapterPaises;
import com.apk.appinfocovid19.Config.env;
import com.apk.appinfocovid19.Model.Pais;
import com.apk.appinfocovid19.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Arrays;

public class MenuDetalleActivity extends AppCompatActivity {

    String paisSeleccionado;
    TextView confirmedCountText, deathCountText, recoveredCountText, updateText;
    Toolbar toolbar;
    List<Pais> paisList;

    android.app.AlertDialog.Builder builder1;
    AlertDialog alert;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private AdapterPaises adapterPaises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detalle);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Convierto la lista a un array
        paisList = new ArrayList<>();

        //Recibo el parametro enviado desde la vista principal
        Intent intent = getIntent();
        paisSeleccionado = intent.getStringExtra("pais");

        //Declaro los componentes que se usara en el layout de esta activity
        confirmedCountText = findViewById(R.id.confirmedCountText);
        deathCountText = findViewById(R.id.deathCountText);
        recoveredCountText = findViewById(R.id.recoveredCountText);
        updateText = findViewById(R.id.updateText);

        //Cargo las estadisticas globles
        VerEstadisticasGlobales();

        //Carga la lista de paises
        ListarPaisesData();

        //Declaro los componentes que necesito para el recyclerview
        recyclerView = findViewById(R.id.globalCasesRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterPaises = new AdapterPaises(paisList,this);
        recyclerView.setAdapter(adapterPaises);
        adapterPaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //      Toast.makeText( MenuDetalleActivity.this, String.valueOf(paisList.get(recyclerView.getChildAdapterPosition(v)).getNombrePais()), Toast.LENGTH_SHORT).show();
                 Detallees(paisList.get(recyclerView.getChildAdapterPosition(v)).getNombrePais());
              // Detalle2(paisList.get(recyclerView.getChildAdapterPosition(v)).getNombrePais());

            }
        });
        updateText.setText(ObtenerFechaActual());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Detalle2(String nombrePais) {

        Intent i = new Intent(MenuDetalleActivity.this, DetallePaisActivity.class);
        i.putExtra("pais", nombrePais);
        startActivity(i);

    }

    private void Detallees(final String paisDetalle) {
        final TextView confirmedCountText, deathCountText, recoveredCountText, updateText, totalCaseActive, totalCaseCritical;
        final ImageView img_country;

        final TextView txtnuevosdia,txtfallecidosdia;
        builder1 = new AlertDialog.Builder(this);
        Button btcerrrar;
        final EditText etnombre;
        View v = LayoutInflater.from(this).inflate(R.layout.dialogo_detalle, null);

        confirmedCountText = v.findViewById(R.id.confirmedCountText);
        deathCountText = v.findViewById(R.id.deathCountText);
        recoveredCountText = v.findViewById(R.id.recoveredCountText);
      //  updateText = v.findViewById(R.id.updateText);
        totalCaseActive = v.findViewById(R.id.totalCaseActive);
        totalCaseCritical = v.findViewById(R.id.totalCaseCritical);
        img_country = v.findViewById(R.id.img_country);

        txtnuevosdia=(TextView) v.findViewById(R.id.totalnuevosdia);
        txtfallecidosdia=(TextView)v.findViewById(R.id.totalFallecidosdia);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, env.api_detail_country + paisDetalle, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonSub = jsonObject.getJSONObject("countryInfo");
                        confirmedCountText.setText(String.valueOf(jsonObject.getInt("cases")));
                        deathCountText.setText(String.valueOf(jsonObject.getInt("deaths")));
                        recoveredCountText.setText(String.valueOf(jsonObject.getInt("recovered")));

                        totalCaseCritical.setText(String.valueOf(jsonObject.getInt("critical")));
                        totalCaseActive.setText(String.valueOf(jsonObject.getInt("active")));

                        txtnuevosdia.setText(String.valueOf(jsonObject.getInt("todayCases")));
                        txtfallecidosdia.setText(String.valueOf(jsonObject.getInt("todayDeaths")));


                        Glide.with(MenuDetalleActivity.this)
                                .load(jsonSub.getString("flag"))
                                .into(img_country);
                     //   updateText.setText(paisDetalle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuDetalleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        builder1.setView(v);
      //  btcerrrar=(Button)v.findViewById(R.id.id_btnagregarcarpeta);
        //etnombre=(EditText)v.findViewById(R.id.id_etnombrecarpeta);


        alert  = builder1.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alert.show();

    }


    public String ObtenerFechaActual() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return "Actualizado al " + formattedDate;
    }

    public void ListarPaisesData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, env.api_all_country, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject json = null;
                        JSONObject jsonSub = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Pais pais = new Pais();
                            json = jsonArray.getJSONObject(i);
                            pais.setIdPais(i+1);
                            pais.setNombrePais(json.getString("country"));
                            pais.setCasosConfirmados(json.getInt("cases"));
                            pais.setBajasTotales(json.getInt("deaths"));
                            pais.setCasosRecuperadosTotales(json.getInt("recovered"));
                            paisList.add(pais);
                            Log.d("myTag", json.getString("country"));
                        }
                        adapterPaises.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuDetalleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void VerEstadisticasGlobales() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, env.api_all_countries, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        confirmedCountText.setText(String.valueOf(jsonObject.getInt("cases")));
                        deathCountText.setText(String.valueOf(jsonObject.getInt("deaths")));
                        recoveredCountText.setText(String.valueOf(jsonObject.getInt("recovered")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuDetalleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
