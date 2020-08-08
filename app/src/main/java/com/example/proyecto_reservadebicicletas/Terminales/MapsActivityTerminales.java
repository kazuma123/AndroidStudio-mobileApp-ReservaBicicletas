package com.example.proyecto_reservadebicicletas.Terminales;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyecto_reservadebicicletas.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivityTerminales extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private Marker terminal1;
    private Marker terminal2;
    private Marker terminal3;
    private Marker terminal4;
    private Marker terminal5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_terminales);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
        LatLng arequipa = new LatLng(-16.4323265, -71.5046024);
        terminal1 = googleMap.addMarker(new MarkerOptions().position(arequipa).title("Ciudad " +
                "de Arequipa").snippet("Terminal").icon(BitmapDescriptorFactory.fromResource(R.drawable.este)));

        LatLng plaza = new LatLng(-16.3989, -71.537);
        terminal2 = googleMap.addMarker(new MarkerOptions()
                .position(plaza)
                .title("My Spot")
                .snippet("This is my spot!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.este)));
        googleMap.setOnMarkerClickListener(this);

        LatLng plaza2 = new LatLng(-16.301234, -71.34255);
        terminal3 = googleMap.addMarker(new MarkerOptions()
                .position(plaza2)
                .title("My Spot2")
                .snippet("This is my spot2!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.este)));
        googleMap.setOnMarkerClickListener(this);

        LatLng plaza3 = new LatLng(-16.201234, -71.14255);
        terminal4 = googleMap.addMarker(new MarkerOptions()
                .position(plaza3)
                .title("My Spot3")
                .snippet("This is my spot3!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.este)));
        googleMap.setOnMarkerClickListener(this);

        LatLng plaza4 = new LatLng(-16.101234, -71.24255);
        terminal5 = googleMap.addMarker(new MarkerOptions()
                .position(plaza4)
                .title("My Spot4")
                .snippet("This is my spot4!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.este)));
        googleMap.setOnMarkerClickListener(this);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arequipa,10));
        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        boolean b;
        b = true;
        Intent intent;
        if (marker.equals(terminal1))
        {
            intent = new Intent(getApplicationContext(), MenuDeOpcionesGraficas.class);
            intent.putExtra("codigo","1");
            startActivity(intent);
            return b;
        }
        if(marker.equals(terminal2)){
            intent = new Intent(getApplicationContext(), MenuDeOpcionesGraficas.class);
            intent.putExtra("codigo","2");
            startActivity(intent);
            return b;
        }
        if(marker.equals(terminal3)){
            intent = new Intent(getApplicationContext(), MenuDeOpcionesGraficas.class);
            intent.putExtra("codigo","3");
            startActivity(intent);
            return b;
        }
        if(marker.equals(terminal4)){
            intent = new Intent(getApplicationContext(), MenuDeOpcionesGraficas.class);
            intent.putExtra("codigo","4");
            startActivity(intent);
            return b;
        }
        if(marker.equals(terminal5)){
            intent = new Intent(getApplicationContext(), MenuDeOpcionesGraficas.class);
            intent.putExtra("codigo","5");
            startActivity(intent);
            return b;
        }
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}
