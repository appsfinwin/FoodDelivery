package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.skyfishjy.library.RippleBackground;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FetchCurrentLocation extends AppCompatActivity {
    private RippleBackground rippleBg;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_current_location);
        rippleBg = findViewById(R.id.ripple_bg);
        rippleBg.startRippleAnimation();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                getDeviceLocation();
            }
        }, 2000);
    }


    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {


        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(FetchCurrentLocation.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.d("onLocationResult", "onLocationResult: " + location.getLatitude());

                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();


                                LocalPreferences.storeStringPreference(getApplicationContext(), "currentlocation", address + "," + city + " " + state);
                                LocalPreferences.storeStringPreference(getApplicationContext(), "latitude", String.valueOf(location.getLatitude()));
                                LocalPreferences.storeStringPreference(getApplicationContext(), "longitude", String.valueOf(location.getLongitude()));
                                LocalPreferences.storeStringPreference(getApplicationContext(), "dellatitude", String.valueOf(location.getLatitude()));
                                LocalPreferences.storeStringPreference(getApplicationContext(), "dellongitude", String.valueOf(location.getLongitude()));

                                rippleBg.stopRippleAnimation();
                                startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                                finish();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }
}