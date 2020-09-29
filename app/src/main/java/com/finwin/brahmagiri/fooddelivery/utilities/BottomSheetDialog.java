package com.finwin.brahmagiri.fooddelivery.utilities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.finwin.brahmagiri.fooddelivery.Activity.MapsActivity;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    EditText EdName, EdFloor, Edaddress, Edlocality;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private PlacesClient placesClient;
    LatLng latLng;
    List<String> countries;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,
                container, false);
        Places.initialize(getActivity(), "AIzaSyADTX6MqUTZYV3kox7HEOEhHEpTlXP3ouA");
        placesClient = Places.createClient(getActivity());
        countries = new ArrayList<>();

        // Initialize the AutocompleteSupportFragment.
    /*    AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
              getActivity().  getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });*/

        Edaddress = v.findViewById(R.id.edAddress);
        EdFloor = v.findViewById(R.id.edfloor);
        // searchLocation(Edaddress.getText().toString());
        Edlocality = v.findViewById(R.id.edArea);
        Edlocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

                countries.add("IN");
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(countries)
                        .build(getActivity());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        Button update = v.findViewById(R.id.btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocalPreferences.storeStringPreference(getActivity(), "delcurrentlocation", EdFloor.getText().toString() + " " + Edaddress.getText().toString() + "," + Edlocality.getText().toString() + " ");

                startActivity(new Intent(getActivity(), MapsActivity.class)
                        .putExtra("isfromcheckout", "YES")
                        .putExtra("Latlng", latLng));


               dismiss();

            }
        });


        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("TAG", "Place: " + place.getLatLng() + ", " + place.getId());
                latLng = place.getLatLng();
                Edlocality.setText("" + place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void searchLocation(String location) {
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            //  latLng = new LatLng(address.getLatitude(), address.getLongitude());
            // mMap.addMarker(new MarkerOptions().position(latLng).title(location));
            // mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
