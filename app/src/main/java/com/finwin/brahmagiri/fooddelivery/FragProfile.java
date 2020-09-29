package com.finwin.brahmagiri.fooddelivery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Activity.UpdateMobActivity;
import com.finwin.brahmagiri.fooddelivery.Responses.District;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseDistricts;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProfile;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseStates;
import com.finwin.brahmagiri.fooddelivery.Responses.Signup_Zone;
import com.finwin.brahmagiri.fooddelivery.Responses.States;
import com.finwin.brahmagiri.fooddelivery.Responses.Zone;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.FragProfileBinding;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class FragProfile extends Fragment {
    FragProfileBinding binding;
    View rootview;
    Button btnLogin;
    ImageButton ibtn_back;
    ImageView img_Chng_homeAdd, img_Chng_ofcAdd;
    String name;
    String mobile;
    String email, address, street, city, pincode, landmark;
    ProgressDialog progressDialog;
    List<Zone> dataset;
    List<States> datasetstates;
    List<District> datasetdistrict;
    AppCompatSpinner spinner, spinnerdistrict, spinnerstate;
    ArrayAdapter<Zone> adapters;
    ArrayAdapter<District> adapterdistrict;
    ArrayAdapter<States> adapterstate;
    String selecteditem,selecteditemdistrict;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View view= rootview = inflater.inflate(R.layout.frag_profile, container, false);

        binding = DataBindingUtil.inflate(
                inflater, R.layout.frag_profile, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        binding.imgvMobedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateMobActivity.class));
            }
        });
        dataset = new ArrayList<>();
        datasetstates = new ArrayList<>();
        datasetdistrict = new ArrayList<>();
        binding.ibtnBackProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        binding.imgvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAdress("Home");
            }
        });


        binding.imgvOfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAdress("Office");
            }
        });

        return binding.getRoot();
    }

    private void doFetch() {
        progressDialog.show();
        String userid = LocalPreferences.retrieveStringPreferences(getActivity(), "partnerid");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("partner_id", Integer.parseInt(userid));
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProfile> call = apiService.doFetchProfile(database, mAccesstoken, jsonObject);
        call.enqueue(new Callback<ResponseFetchProfile>() {
            @Override
            public void onResponse(Call<ResponseFetchProfile> call, Response<ResponseFetchProfile> response) {
                progressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    name = response.body().getName();
                    mobile = response.body().getMobile();
                    email = response.body().getEmail();
                    address = response.body().getHouseNo();
                    street = response.body().getStreet();
                    city = response.body().getCity();
                    landmark = response.body().getLandmark();
                    pincode = response.body().getPincode().toString();
                    binding.tvname.setText(name);
                    binding.tvEmail.setText(email);
                    binding.tvMobile.setText(mobile);
                    binding.tvZone.setText(response.body().getZone());
                    String address = response.body().getHouseNo() + " ," + response.body().getStreet() + " ," + response.body().getCity() + "  ,"
                            + response.body().getDistrict() + " ,"
                            + response.body().getState() + " ," + "\n" + "Landmark - " + response.body().getLandmark() + "\n" + "Pincode - " + response.body().getPincode();

                    binding.address1.setText(address);
                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProfile> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    private void ChangeAdress(String addrsType) {

        AlertDialog.Builder b = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        String addTest = "Change " + addrsType + " Address";
        b.setTitle(addTest);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_chng_address, null);
        final EditText edname = dialogView.findViewById(R.id.ed_name);
        spinner = dialogView.findViewById(R.id.ed_spinner);
        spinnerstate = dialogView.findViewById(R.id.ed_state);
        spinnerdistrict = dialogView.findViewById(R.id.ed_district);
        final EditText edmobile = dialogView.findViewById(R.id.ed_mobile);
        final EditText edlandmark = dialogView.findViewById(R.id.ed_landmark);
        final EditText edaddress = dialogView.findViewById(R.id.ed_address);
        final EditText edstreet = dialogView.findViewById(R.id.ed_street);
        final EditText edcity = dialogView.findViewById(R.id.ed_city);
        final EditText edpin = dialogView.findViewById(R.id.ed_pin);
        final EditText edemail = dialogView.findViewById(R.id.ed_email);
        LoadZone();
        LoadStates();
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
        spinnerstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                String    selecteditemstate = Objects.requireNonNull(adapterstate.getItem(i)).getId().toString();
                    String las = adapterstate.getItem(i).getId().toString();
                    Log.d("onItemSelected", "onItemSelected: " + i);
                    LoadDistricts(selecteditemstate);

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
        edname.setText(name);
        edmobile.setText(mobile);
        edlandmark.setText(landmark);
        edaddress.setText(address);
        edstreet.setText(street);
        edcity.setText(city);
        edpin.setText(pincode);
        edemail.setText(email);
        b.setView(dialogView);
        b.setCancelable(false);
        b.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("onClick: ", "+++++");
                doUpdateProfile(edname.getText().toString(), edmobile.getText().toString(), edlandmark.getText().toString(),
                        edaddress.getText().toString(), edstreet.getText().toString(), edcity.getText().toString(), edemail.getText().toString(), edpin.getText().toString(), selecteditem,selecteditemdistrict);
            }
        });

        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("onClick: ", "-----");
            }
        });

//        LinearLayout linCOD = (LinearLayout) dialogView.findViewById(R.id.linr_cod);
//        LinearLayout linPaytm = (LinearLayout) dialogView.findViewById(R.id.linr_paytm);
//
        final AlertDialog alertDialog = b.create();
//        linCOD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        linPaytm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
        alertDialog.show();
    }

    private void doUpdateProfile(String edname, String edmobile, String edlandmark,
                                 String edaddress, String edstreet, String edcity, String edemail, String edpin, String selecteditem,String selecteditemdistrict) {
        String userid = LocalPreferences.retrieveStringPreferences(getActivity(), "partnerid");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("partner_id", Integer.parseInt(userid));
        jsonObject.addProperty("name", edname);
        jsonObject.addProperty("mobile", edmobile);
        jsonObject.addProperty("landmark", edlandmark);
        jsonObject.addProperty("address", edaddress);
        jsonObject.addProperty("street", edstreet);
        jsonObject.addProperty("city", edcity);
        jsonObject.addProperty("email", edemail);
        jsonObject.addProperty("pincode", edpin);
        jsonObject.addProperty("zone", Integer.parseInt(selecteditem));
        jsonObject.addProperty("district", Integer.parseInt(selecteditemdistrict));

        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<JsonObject> call = apiService.doUpdateProfile(database, mAccesstoken, jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null && response.code() == 200) {
                    doFetch();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        doFetch();
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
}
