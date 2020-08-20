package com.apk.appinfocovid19.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apk.appinfocovid19.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

public class UbicacionActivity extends AppCompatActivity   implements OnMapReadyCallback, LocationListener {


    private TextView tvLatitud, tvLongitud, tvAltura, tvPrecision;



    Button btnenviar;

    private GoogleMap mMap;
    double lat, lon;
    double lat_conductir, lon_conductor;
    String key_envio;
    DatabaseReference reference;
    Location location;
    LocationManager lm;
    private LocationManager locManager;
    private Location loc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        btnenviar = (Button)findViewById(R.id.btnenviar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);


        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double lat=1;
                double lon=2;
                Enviar(lat_conductir,lon_conductor);
            }
        });

    }

    private void Enviar(double lat, double lon) {


        String la=String.valueOf(lat);
        String lo =String.valueOf(lon);
           /*
        SharedPreferences preferences=getSharedPreferences("ubicacion", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("lati",la);
        editor.putString("lon",lo);

         */
        DonacionActivity.latiud=la;
        DonacionActivity.longi=lo;

        ReportarCasoActivity.latiud=la;
        ReportarCasoActivity.longi=lo;


//        editor.commit();
        finish();

    }

    private void enviardatos() {

       // MainActivity.lat=String.valueOf(lat);
       // MainActivity.lon=String.valueOf(lon);
      //  NavUtils.navigateUpFromSameTask(getActivity());

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lon_conductor= location.getLongitude();
        lat_conductir = location.getLatitude();
        LatLng aquitoy = new LatLng(lat_conductir, lon_conductor);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(aquitoy)
                .zoom(12)//esto es el zoom
                .bearing(30)//esto es la inclonacion
                .build();

        //ENVENTO CLICK PARA EL
        mMap.addMarker(new MarkerOptions().position(aquitoy).title("Yo"));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions();
                // MarkerOptions cliente = new MarkerOptions();
                LatLng cliente = new LatLng(lat, lon);
                markerOptions.position(latLng);
                mMap.clear();

             //   Toast.makeText(UbicacionActivity.this,  String.valueOf(latLng.latitude+ "-" +latLng.longitude), Toast.LENGTH_SHORT).show();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                lat_conductir=latLng.latitude;
                lon_conductor=latLng.longitude;
                mMap.addMarker(markerOptions);
                mMap.addMarker(new MarkerOptions().position(cliente).title("Yo"));

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            Log.v("Localizacion", location.getLatitude() + " y " + location.getLongitude());
            lm.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
