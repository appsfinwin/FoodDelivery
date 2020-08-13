package com.finwin.brahmagiri.fooddelivery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Responses.District;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseDistricts;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseStates;
import com.finwin.brahmagiri.fooddelivery.Responses.Response_Signup;
import com.finwin.brahmagiri.fooddelivery.Responses.Signup_Zone;
import com.finwin.brahmagiri.fooddelivery.Responses.States;
import com.finwin.brahmagiri.fooddelivery.Responses.Zone;
import com.finwin.brahmagiri.fooddelivery.Utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.Utilities.Constants.database;

public class FragSignUpDetails extends Fragment {
    AppCompatSpinner spinner, spinnerdistrict, spinnerstate;
    View rootview;
    Button btnSignup;
    TextView tvSignin;
    List<Zone> dataset;
    List<States> datasetstates;
    List<District> datasetdistrict;

    ArrayAdapter<Zone> adapters;
    ArrayAdapter<District> adapterdistrict;
    ArrayAdapter<States> adapterstate;

    EditText Edname, Edmobile, Edemail, Edpin, Edaddress, Edusername, Edpasswd, EdConfirm, EdStreet, EdCity, Edlandmark;
    private String selecteditem, selecteditemdistrict, selecteditemstate;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataset = new ArrayList<>();
        LoadZone();
        LoadStates();

        spinner = rootview.findViewById(R.id.ed_spinner);
        spinnerdistrict = rootview.findViewById(R.id.ed_district);
        spinnerstate = rootview.findViewById(R.id.ed_state);
        EdStreet = rootview.findViewById(R.id.ed_streetaddress);
        EdCity = rootview.findViewById(R.id.ed_city);
        Edlandmark = rootview.findViewById(R.id.ed_landmark);


        Edname = rootview.findViewById(R.id.ed_name);
        Edmobile = rootview.findViewById(R.id.ed_mobile);
        Edemail = rootview.findViewById(R.id.ed_email);
        Edpin = rootview.findViewById(R.id.ed_pin);
        Edaddress = rootview.findViewById(R.id.ed_address);
        Edusername = rootview.findViewById(R.id.ed_username);
        Edpasswd = rootview.findViewById(R.id.ed_pwd);
        EdConfirm = rootview.findViewById(R.id.ed_confirmpwd);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    selecteditem = adapters.getItem(i).getId().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    selecteditemdistrict = adapterdistrict.getItem(i).getId().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    selecteditemstate = Objects.requireNonNull(adapterstate.getItem(i)).getId().toString();
                    String las = adapterstate.getItem(i).getId().toString();
                    Log.d("onItemSelected", "onItemSelected: " + i);
                    LoadDistricts(selecteditemstate);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSignup = rootview.findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaname = Edname.getText().toString();
                String vaMobile = Edmobile.getText().toString();
                String vaPin = Edpin.getText().toString();
                String vaaddress = Edaddress.getText().toString();
                String vausername = Edusername.getText().toString();
                String vapassword = Edpasswd.getText().toString();
                String vaConfirmpasswd = EdConfirm.getText().toString();
                String vastreet = EdStreet.getText().toString();
                String vaCity = EdCity.getText().toString();
                String vaLandmark = Edlandmark.getText().toString();
                String vaEmail = Edemail.getText().toString();


                if (vaname.equals("")) {
                    Edname.setError("Field Required");

                } else if (vaMobile.equals("")) {
                    Edmobile.setError("Field Required");
                } else if (!new AppUtility(getActivity()).isValidMobile(vaMobile)) {
                    Edmobile.setError("Invalid Mobile");
                    Edmobile.requestFocus();


                } else if (vaPin.equals("")) {
                    Edpin.setError("Field Required");


                } else if (vaaddress.equals("")) {
                    Edaddress.setError("Field Required");


                } else if (vaCity.equals("")) {
                    EdCity.setError("Field Required");


                } else if (vastreet.equals("")) {
                    EdStreet.setError("Field Required");


                } else if (vaLandmark.equals("")) {
                    Edlandmark.setError("Field Required");


                } else if (vausername.equals("")) {
                    Edusername.setError("Field Required");


                } else if (vapassword.equals("")) {
                    Edpasswd.setError("Field Required");


                } else if (!vapassword.equals(vaConfirmpasswd)) {
                    EdConfirm.setError("Field Required");

                } else {
                    doSignup(vaname, vaMobile, vaEmail, vaPin, vaaddress, vausername, vapassword, vaLandmark, vastreet, vaCity);
                }

                // doSignup(vaname, vaMobile, "vaemail", vaPin, vaaddress, vausername, vapassword);


                // ActivitySignUp.viewPager.setCurrentItem(1, true);
            }
        });

        tvSignin = rootview.findViewById(R.id.tv_signin);
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

    }

    private void doSignup(String vaname, String vaMobile, String vaemail, String vaPin, String vaaddress, String vausername, String vapassword, String vaLandmark, String vastreet, String vaCity) {


        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<Response_Signup> call = apiService.dosignup(database, vaname, vaMobile, vaaddress, Integer.parseInt(vaPin), vausername, vapassword, vastreet, vaCity, vaLandmark, Integer.parseInt(selecteditemstate), Integer.parseInt(selecteditemdistrict), vaemail, selecteditem);
        call.enqueue(new Callback<Response_Signup>() {
            @Override
            public void onResponse(Call<Response_Signup> call, Response<Response_Signup> response) {
                if (response.body() != null && response.code() == 200) {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), ActivityInitial.class));
                    getActivity().finishAffinity();

                } else {
                    Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Response_Signup> call, Throwable t) {

            }
        });


    }

    private void LoadZone() {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<Signup_Zone> call = apiService.fetchzonesignup(database);
        call.enqueue(new Callback<Signup_Zone>() {
            @Override
            public void onResponse(Call<Signup_Zone> call, Response<Signup_Zone> response) {
                if (response.body() != null && response.code() == 200) {
                    Signup_Zone signup_zone = response.body();
                    dataset = signup_zone.getZones();
                    adapters = new ArrayAdapter<Zone>(getActivity(), android.R.layout.simple_spinner_item, dataset);
                    adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapters);

                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Signup_Zone> call, Throwable t) {

            }
        });
    }

    private void LoadStates() {

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseStates> call = apiService.doFetchStates(database);
        call.enqueue(new Callback<ResponseStates>() {
            @Override
            public void onResponse(Call<ResponseStates> call, Response<ResponseStates> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseStates signup_zone = response.body();
                    datasetstates = signup_zone.getStates();
                    adapterstate = new ArrayAdapter<States>(getActivity(), android.R.layout.simple_spinner_item, datasetstates);
                    adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerstate.setAdapter(adapterstate);

                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStates> call, Throwable t) {

            }
        });
    }

    private void LoadDistricts(String selecteditemstate) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", Integer.parseInt(selecteditemstate));

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseDistricts> call = apiService.doFetchDistricts(database, jsonObject);
        call.enqueue(new Callback<ResponseDistricts>() {
            @Override
            public void onResponse(Call<ResponseDistricts> call, Response<ResponseDistricts> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseDistricts signup_zone = response.body();
                    datasetdistrict = signup_zone.getDistricts();
                    adapterdistrict = new ArrayAdapter<District>(getActivity(), android.R.layout.simple_spinner_item, datasetdistrict);
                    adapterdistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerdistrict.setAdapter(adapterdistrict);

                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDistricts> call, Throwable t) {

            }
        });
    }

    public static FragSignUpDetails newInstance(String text) {
        FragSignUpDetails f = new FragSignUpDetails();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


}
