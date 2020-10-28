package com.finwin.brahmagiri.fooddelivery.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.ActivityInitial;
import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.FragProfile;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCheckVersion;
import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.BuildConfig;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class SplashScreen extends AppCompatActivity {
    String versionName;
    int verCode;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        versionName = BuildConfig.VERSION_NAME;
        verCode = BuildConfig.VERSION_CODE;
        TextView tvver = findViewById(R.id.tvversion);
        tvver.setText("" + versionName);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

    }

    private void dofetchVersion() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("app_type", "Consumer App");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseCheckVersion> call = apiService.doFetchVersionControl(database, jsonObject);
        call.enqueue(new Callback<ResponseCheckVersion>() {
            @Override
            public void onResponse(Call<ResponseCheckVersion> call, Response<ResponseCheckVersion> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseCheckVersion responseCheckVersion = response.body();

                    int apiversioncode = Integer.parseInt(responseCheckVersion.getVersionCode());
                    if (verCode < apiversioncode) {
                        startActivity(new Intent(getApplicationContext(), DemoSplash.class));
                        finish();
                    } else {
                        setLogin();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckVersion> call, Throwable t) {

            }
        });
    }

    private void setLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                doCheckpermission();
                //the current activity will get finished.
            }
        }, 1000);
    }

    private void doCheckpermission() {

        Dexter.withActivity(SplashScreen.this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CALL_PHONE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted

                        if (report.areAllPermissionsGranted()) {
                            dologin();


                        }else{
                            showSettingsDialog();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings


                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);

        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();



    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {


        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(SplashScreen.this, new OnSuccessListener<Location>() {
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



                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void dologin() {
        final boolean islooged = LocalPreferences.retrieveBooleanPreferences(getApplicationContext(), "isLoggedin");
        if (islooged) {
            Intent i = new Intent(getApplicationContext(),
                    FetchCurrentLocation.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(getApplicationContext(),
                    ActivityInitial.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (new AppUtility(SplashScreen.this).checkInternet()) {
            dofetchVersion();
        } else {
            Toast.makeText(getApplicationContext(), "NO INTERNET", Toast.LENGTH_SHORT).show();

        }
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

            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Loc", "onSuccess --> onFailure() called with: e = [" + e.getMessage() + "]");
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(SplashScreen.this,
                                51);
                    } catch (IntentSender.SendIntentException es) {

                        es.printStackTrace();
                    }
                }

            }
        });
    }
}
