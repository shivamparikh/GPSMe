package com.hackgeny.megps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewPlaces extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double longitude;
    private double latitude;
    protected LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_places);
        setUpMapIfNeeded();
        if(mMap == null){
            Toast.makeText(ViewPlaces.this, "Google Play Services Needed\nPlease Install before Use", Toast.LENGTH_LONG).show();
            finish();
        }
        Toast.makeText(ViewPlaces.this, "Map Successfully Set Up", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new MyLocationListener());
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
        mMap.setMyLocationEnabled(true);
    }
    public void getCurrentLocation(){
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }
    private double floatInRange(double min, double max){
        return Math.random() < 0.5 ? ((1-Math.random()) * (max-min) + min) : (Math.random() * (max-min) + min);
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        for(int i = 0; i < 20; i++){
            double longitude = floatInRange(-121.22, -77.88);
            double latitude = floatInRange(30, 50);
            String title;
            if(i%2 == 0){
                title = "SPRAY";
            }
            else{
                title = "PANIC";
            }
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(title));

        }


    }
    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {

        }
        public void onStatusChanged(String s, int i, Bundle b) {
        }
        public void onProviderDisabled(String s) {
            Toast.makeText(ViewPlaces.this, "Provider disabled by the user. GPS turned off", Toast.LENGTH_LONG).show();
        }
        public void onProviderEnabled(String s) {
            Toast.makeText(ViewPlaces.this,"Provider enabled by the user. GPS turned on",Toast.LENGTH_LONG).show();
        }
    }
}
