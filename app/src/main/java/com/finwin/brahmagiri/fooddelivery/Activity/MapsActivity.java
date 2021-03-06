package com.finwin.brahmagiri.fooddelivery.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchAddress;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.JsonObject;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.skyfishjy.library.RippleBackground;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    List<String> data;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;

    private MaterialSearchBar materialSearchBar;
    private View mapView;
    private Button btnFind;
    private RippleBackground rippleBg;
    private final float DEFAULT_ZOOM = 15;
    String Flag;
    EditText additionainfo;
    Serializable values;
    double lats, longi;
    LatLng fromPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Initialize the SDK
        //   Places.initialize(getApplicationContext(), "AIzaSyDUn0IgHk7yyYvKurogimrb9IpP4hrayac");

        // Create a new PlacesClient instance
        //    PlacesClient placesClient = Places.createClient(this);
        // Initialize the SDK
        // Initialize the AutocompleteSupportFragment.
        Flag = getIntent().getStringExtra("isfromcheckout");
        //   lats = Double.parseDouble(getIntent().getStringExtra("lat"));
        //  longi = Double.parseDouble(getIntent().getStringExtra("long"));
        Bundle bundle = getIntent().getParcelableExtra("bundle");
        //   fromPosition = bundle.getParcelable("lat");

        fromPosition = getIntent().getExtras().getParcelable("Latlng");


        //   LatLng toPosition = bundle.getParcelable("to_position");
        Log.d("onCreate", "onCreate: " + fromPosition);
        //values = getIntent().getSerializableExtra("location");
        additionainfo = findViewById(R.id.btndeliveryinfo);

        if (Flag.equalsIgnoreCase("YES")) {
            additionainfo.setVisibility(View.GONE);

        } else {
            additionainfo.setVisibility(View.GONE);

        }
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.


        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
    /*    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*/
        //  data.add("IN");
        // Start the autocomplete intent.
        materialSearchBar = findViewById(R.id.searchBar);
        btnFind = findViewById(R.id.btn_find);
        rippleBg = findViewById(R.id.ripple_bg);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        Places.initialize(MapsActivity.this, "AIzaSyCbC3NoQAKrMUj-h6IclKNC9mzaLJtNl_o");
        placesClient = Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        //  remit(token);

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, false);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    //opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }
            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountries("IN")
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();
                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionsList = new ArrayList<>();
                                for (int i = 0; i < predictionList.size(); i++) {
                                    AutocompletePrediction prediction = predictionList.get(i);
                                    suggestionsList.add(prediction.getFullText(null).toString());
                                }
                                materialSearchBar.updateLastSuggestions(suggestionsList);
                                if (!materialSearchBar.isSuggestionsVisible()) {
                                    materialSearchBar.showSuggestionsList();
                                }
                            }
                        } else {
                            Log.i("mytag", "prediction fetching task unsuccessful");
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (position >= predictionList.size()) {
                    return;
                }
                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialSearchBar.clearSuggestions();
                    }
                }, 1000);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                final String placeId = selectedPrediction.getPlaceId();
                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                        Place place = fetchPlaceResponse.getPlace();
                        Log.i("mytag", "Place found: " + place.getName());
                        LatLng latLngOfPlace = place.getLatLng();
                        if (latLngOfPlace != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            apiException.printStackTrace();
                            int statusCode = apiException.getStatusCode();
                            Log.i("mytag", "place not found: " + e.getMessage());
                            Log.i("mytag", "status code: " + statusCode);
                        }
                    }
                });
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng currentMarkerLocation = mMap.getCameraPosition().target;
                // Toast.makeText(getApplicationContext(),""+currentMarkerLocation.latitude,Toast.LENGTH_SHORT).show();
                rippleBg.startRippleAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rippleBg.stopRippleAnimation();

                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(currentMarkerLocation.latitude, currentMarkerLocation.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();

                            new SweetAlertDialog(MapsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Location")
                                    .setContentText("" + address + city)
                                    .setCancelText("Cancel")
                                    .setConfirmText("Confirm")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            if (Flag.equalsIgnoreCase("NO")) {
                                                Log.d("TAG", "onClick: " + "NO");

                                                LocalPreferences.storeStringPreference(getApplicationContext(), "currentlocation", address + "," + city + " ");
                                                LocalPreferences.storeStringPreference(getApplicationContext(), "latitude", String.valueOf(currentMarkerLocation.latitude));
                                                LocalPreferences.storeStringPreference(getApplicationContext(), "longitude", String.valueOf(currentMarkerLocation.longitude));
                                                startActivity(new Intent(getApplicationContext(), ActivityMain.class));

                                                finish();
                                            } else if (Flag.equalsIgnoreCase("YES")) {
                                                Log.d("TAG", "onClick: " + "yes");
                                                //   LocalPreferences.storeStringPreference(getApplicationContext(), "delcurrentlocation", address + "," + city + " ");
                                                LocalPreferences.storeStringPreference(getApplicationContext(), "dellatitude", String.valueOf(currentMarkerLocation.latitude));
                                                LocalPreferences.storeStringPreference(getApplicationContext(), "dellongitude", String.valueOf(currentMarkerLocation.longitude));

                                                Log.d("onClick", "onClick: " + currentMarkerLocation.latitude);

                                                changedeladdress(currentMarkerLocation.latitude, currentMarkerLocation.longitude);
                                            }


                                        }
                                    })
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.cancel();
                                        }
                                    })
                                    .show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }







                        /*startActivity(new Intent(MapsActivity.this, DemoSplash.class));
                        finish();*/
                    }
                }, 2000);

            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
        }

        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(MapsActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(MapsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(MapsActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MapsActivity.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (materialSearchBar.isSuggestionsVisible())
                    materialSearchBar.clearSuggestions();
                if (materialSearchBar.isSearchEnabled())
                    materialSearchBar.disableSearch();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {

                                if (fromPosition != null) {
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fromPosition, DEFAULT_ZOOM));

                                } else {
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                                }


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
                                        if (fromPosition != null) {
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fromPosition, DEFAULT_ZOOM));
                                        } else {
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                                        }


                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(MapsActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void changedeladdress(double latitude, double longitude) {
        String address = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "delcurrentlocation");

        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        String cartid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "cartoutid");
        String partnerid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");
        String userid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "userid");
        String locality = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "locality");

        //  String latitude = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "dellatitude");
        //   String longitude = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "dellongitude");
        JsonObject student1 = new JsonObject();
        student1.addProperty("delivery_location", address);

        student1.addProperty("landmark", locality);
        student1.addProperty("longitude", String.valueOf(longitude));
        student1.addProperty("latitude", String.valueOf(latitude));
        student1.addProperty("partner_id", Integer.parseInt(partnerid));
        student1.addProperty("user_id", Integer.parseInt(userid));
        student1.addProperty("outlet", Integer.parseInt(cartid));


        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchAddress> call = apiService.doUpdatedeliverycost(mAccesstoken, database, student1);
        call.enqueue(new Callback<ResponseFetchAddress>() {
            @Override
            public void onResponse(Call<ResponseFetchAddress> call, Response<ResponseFetchAddress> response) {
                if (response.body() != null && response.code() == 200) {
                    finish();

                }
            }

            @Override
            public void onFailure(Call<ResponseFetchAddress> call, Throwable t) {

            }
        });


    }


}