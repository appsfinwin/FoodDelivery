package com.finwin.brahmagiri.fooddelivery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Responses.Response_Signup;
import com.finwin.brahmagiri.fooddelivery.Responses.Signup_Zone;
import com.finwin.brahmagiri.fooddelivery.Responses.Zone;
import com.finwin.brahmagiri.fooddelivery.Utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSignUpDetails extends Fragment {
    AppCompatSpinner spinner;
    View rootview;
    Button btnSignup;
    TextView tvSignin;
    List<Zone> dataset;
    ArrayAdapter<Zone> adapters;
    EditText Edname, Edmobile, Edemail, Edpin, Edaddress, Edusername, Edpasswd, EdConfirm;
    private String selecteditem;


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
        spinner = rootview.findViewById(R.id.ed_spinner);

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


                } else if (vausername.equals("")) {
                    Edusername.setError("Field Required");


                } else if (vapassword.equals("")) {
                    Edpasswd.setError("Field Required");


                } else if (!vapassword.equals(vaConfirmpasswd)) {
                    EdConfirm.setError("Field Required");

                } else {
                    doSignup(vaname, vaMobile, "vaemail", vaPin, vaaddress, vausername, vapassword);
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

    private void doSignup(String vaname, String vaMobile, String vaemail, String vaPin, String vaaddress, String vausername, String vapassword) {


        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<Response_Signup> call = apiService.dosignup("test", vaname, vaMobile, vaaddress, Integer.parseInt(vaPin), vausername, vapassword, selecteditem);
        call.enqueue(new Callback<Response_Signup>() {
            @Override
            public void onResponse(Call<Response_Signup> call, Response<Response_Signup> response) {
                if (response.body() != null && response.code() == 200) {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), ActivityInitial.class));
                    getActivity().finishAffinity();

                }else{
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
        Call<Signup_Zone> call = apiService.fetchzonesignup("test");
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

    public static FragSignUpDetails newInstance(String text) {
        FragSignUpDetails f = new FragSignUpDetails();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


}
