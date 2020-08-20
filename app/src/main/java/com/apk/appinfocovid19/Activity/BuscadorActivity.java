package com.apk.appinfocovid19.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apk.appinfocovid19.Adapters.AdapterBuscadorPaises;
import com.apk.appinfocovid19.Adapters.AdapterPaises;
import com.apk.appinfocovid19.Config.env;
import com.apk.appinfocovid19.Model.Pais;
import com.apk.appinfocovid19.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscadorActivity extends AppCompatActivity implements AdapterBuscadorPaises.EscuchaEventosClick {

    List<Pais> paisList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter adapter;
    AdapterBuscadorPaises adapter;
    AppCompatImageView backButton;
    EditText searchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        backButton = findViewById(R.id.backButton);
        searchEdit = findViewById(R.id.searchEdit);
        //Convierto la lista a un array
        paisList = new ArrayList<>();
        //Carga la lista de paises
        ListarPaisesData();
        //Declaro los componentes que necesito para el recyclerview
        recyclerView = findViewById(R.id.searchRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterBuscadorPaises(paisList, BuscadorActivity.this, this);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

    }

    private void filtrar(String textoEntrante) {
        ArrayList<Pais> filtrarLista = new ArrayList<>();
        for (Pais pais: paisList){
            if (pais.getNombrePais().toLowerCase().contains(textoEntrante.toLowerCase())){
                filtrarLista.add(pais);
            }
        }
        adapter.filtrar(filtrarLista);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                            pais.setIdPais(i + 1);
                            pais.setNombrePais(json.getString("country"));
                            paisList.add(pais);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BuscadorActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterBuscadorPaises.ViewHolder holder, int posicion) {
        String textoEntrante = searchEdit.getText().toString();
        ArrayList<Pais> filtrarLista = new ArrayList<>();
        for (Pais pais: paisList){
            if (pais.getNombrePais().toLowerCase().contains(textoEntrante.toLowerCase())){
                filtrarLista.add(pais);
            }
        }
        String pais = filtrarLista.get(posicion).getNombrePais();
        Intent i = new Intent(BuscadorActivity.this, MenuActivity.class);
        i.putExtra("pais", pais);
        startActivity(i);
    }
}