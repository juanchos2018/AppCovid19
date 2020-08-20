package com.apk.appinfocovid19.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apk.appinfocovid19.Config.env;
import com.apk.appinfocovid19.R;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class DetallePaisActivity extends AppCompatActivity {


    String paisDetalle;
    TextView confirmedCountText, deathCountText, recoveredCountText, updateText, totalCaseActive, totalCaseCritical;
    TextView txtnuevosdia,txtfallecidosdia;
    Toolbar toolbar;
    ImageView img_country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pais);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        confirmedCountText = findViewById(R.id.confirmedCountText);
        deathCountText = findViewById(R.id.deathCountText);
        recoveredCountText = findViewById(R.id.recoveredCountText);
        updateText = findViewById(R.id.updateText);
        totalCaseActive = findViewById(R.id.totalCaseActive);
        totalCaseCritical = findViewById(R.id.totalCaseCritical);
        img_country = findViewById(R.id.img_country);


        txtnuevosdia=(TextView)findViewById(R.id.totalnuevosdia);
        txtfallecidosdia=(TextView)findViewById(R.id.totalFallecidosdia);


        Intent intent = getIntent();
        if (intent.getStringExtra("pais") != null) {
            paisDetalle = intent.getStringExtra("pais");
        }
        VerEstadisticasPais();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void VerEstadisticasPais() {
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
                        Glide.with(DetallePaisActivity.this)
                                .load(jsonSub.getString("flag"))
                                .into(img_country);


                        updateText.setText(paisDetalle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallePaisActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
