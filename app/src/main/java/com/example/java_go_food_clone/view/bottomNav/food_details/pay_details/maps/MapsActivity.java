package com.example.java_go_food_clone.view.bottomNav.food_details.pay_details.maps;

import static androidx.core.location.LocationManagerCompat.isLocationEnabled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.java_go_food_clone.R;
import com.example.java_go_food_clone.view.splash_screen.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements MapboxMap.OnMapClickListener, OnMapReadyCallback {
    private MapView mapView;
    private MapboxMap map;
    private TextView tvLatLng;
    private Point originPositon;
    private Point destinationPosition;
    private Marker destinationMarker;
    private Button btnNext;
    private Double lat, lng;
    String location2 = "";
    private FloatingActionButton fabCurrentLocation;
    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_maps);

        Intent source = new Intent();

        mapView = (MapView) findViewById(R.id.mapView);
        btnNext = (Button) findViewById(R.id.btnNext);
        tvLatLng = findViewById(R.id.tvLatLong);
        fabCurrentLocation = findViewById(R.id.fabCurrentLocation);
        client = LocationServices.getFusedLocationProviderClient(this);


        // error klo jalan di emulator
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location2 = lat + "," + lng;

                //
                source.putExtra("getLocation_id", location2);
                setResult(Activity.RESULT_OK, source);
                finish();
            }
        });

        fabCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location lastKnownLocation = map.getLocationComponent().getLastKnownLocation();

                if (lastKnownLocation != null) {

                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude())) // Sets the new camera position
                            .zoom(16)
                            .bearing(0)
                            .tilt(0)
                            .build(); // Creates a CameraPosition from the builder

                    map.animateCamera(CameraUpdateFactory
                            .newCameraPosition(position), 7000);
                }
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        if (destinationMarker != null) {
            map.removeMarker(destinationMarker);
        }

        destinationMarker = map.addMarker(new MarkerOptions().position(point));

        destinationPosition = Point.fromLngLat(point.getLongitude(), point.getLatitude());

        // get POINT
        lng = point.getLongitude();
        lat = point.getLatitude();

        tvLatLng.setText("Long: " + lng + " Lat: " + lat);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.addOnMapClickListener(this);
        mapboxMap.setStyle(Style.OUTDOORS);
    }
}