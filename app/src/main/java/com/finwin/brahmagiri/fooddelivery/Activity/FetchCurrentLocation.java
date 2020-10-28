package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.GpsUtils;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.skyfishjy.library.RippleBackground;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FetchCurrentLocation extends AppCompatActivity {
    private RippleBackground rippleBg;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    private boolean isGPS = false;
    FusedLocationProviderClient mFusedLocationClient;
    BroadcastReceiver receiver;
    LocationCallback mLocationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_current_location);


        rippleBg = findViewById(R.id.ripple_bg);
        rippleBg.startRippleAnimation();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        // checkWhetherLocationSettingsAreSatisfied();
        // getDeviceLocation();
      //  checkWhetherLocationSettingsAreSatisfied();
    /*    new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });*/

        displayLocationSettingsRequest(FetchCurrentLocation.this);
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
                            Toast.makeText(getApplicationContext(), "location" + location.getLongitude(), Toast.LENGTH_SHORT).show();

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

                               // rippleBg.stopRippleAnimation();
                                startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                                finish();

                            } catch (IOException e) {
                                Log.d("onLocationResult", "onLocationResult: " + location.getLatitude());
                                Toast.makeText(getApplicationContext(), "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();


                                e.printStackTrace();
                            }

                        }
                    }
                });
    }


    private void checkWhetherLocationSettingsAreSatisfied() {

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        builder.setNeedBle(true);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("Loc", "onSuccess() called with: locationSettingsResponse = [" + locationSettingsResponse + "]");
                // hasLocationPermission();
             //   getDeviceLocation();

                getmylocation();

            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Loc", "onSuccess --> onFailure() called with: e = [" + e.getMessage() + "]");
                Toast.makeText(getApplicationContext(), "GPSFAILURE", Toast.LENGTH_SHORT).show();
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(FetchCurrentLocation.this,
                                51);
                    } catch (IntentSender.SendIntentException es) {

                        es.printStackTrace();
                    }
                }

            }
        });
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocations() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {


                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        Toast.makeText(getApplicationContext(), "Location" + mLastKnownLocation.getLongitude(), Toast.LENGTH_SHORT).show();


                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(FetchCurrentLocation.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("TAGdata", "All location settings are satisfied.");
                        getmylocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("TAGdata", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(FetchCurrentLocation.this, 101);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("TAGdata", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("TAGdata", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "okstatus" + resultCode, Toast.LENGTH_SHORT).show();
        if (requestCode == 101) {
            getmylocation();
        }
    }

    public void getmylocation() {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);

        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
         mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                   // Toast.makeText(getApplicationContext(),"Locationnull",Toast.LENGTH_SHORT).show();

                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        //TODO: UI updates.
                      //  Toast.makeText(getApplicationContext(),"LocationFound",Toast.LENGTH_SHORT).show();

                       // LocalPreferences.storeStringPreference(getApplicationContext(), "currentlocation", address + "," + city + " " + state);
                        LocalPreferences.storeStringPreference(getApplicationContext(), "latitude", String.valueOf(location.getLatitude()));
                        LocalPreferences.storeStringPreference(getApplicationContext(), "longitude", String.valueOf(location.getLongitude()));
                        LocalPreferences.storeStringPreference(getApplicationContext(), "dellatitude", String.valueOf(location.getLatitude()));
                        LocalPreferences.storeStringPreference(getApplicationContext(), "dellongitude", String.valueOf(location.getLongitude()));

                        //rippleBg.stopRippleAnimation();
                       LocationServices.getFusedLocationProviderClient(FetchCurrentLocation.this).removeLocationUpdates(mLocationCallback);

                        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                        finish();


                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }





}