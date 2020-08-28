package com.finwin.brahmagiri.fooddelivery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProfile;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.FragProfileBinding;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.Utilities.Constants.database;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View view= rootview = inflater.inflate(R.layout.frag_profile, container, false);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.frag_profile, container, false);
progressDialog=new ProgressDialog(getActivity());
progressDialog.setMessage("Loading...");
progressDialog.setCancelable(false);
progressDialog.setCanceledOnTouchOutside(false);

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
                ChangePayment("Home");
            }
        });


        binding.imgvOfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePayment("Office");
            }
        });
        doFetch();
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

    private void ChangePayment(String addrsType) {
        AlertDialog.Builder b = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        String addTest = "Change " + addrsType + " Address";
        b.setTitle(addTest);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_chng_address, null);
        final EditText edname = dialogView.findViewById(R.id.ed_name);
        final EditText edmobile = dialogView.findViewById(R.id.ed_mobile);
        final EditText edlandmark = dialogView.findViewById(R.id.ed_landmark);
        final EditText edaddress = dialogView.findViewById(R.id.ed_address);
        final EditText edstreet = dialogView.findViewById(R.id.ed_street);
        final EditText edcity = dialogView.findViewById(R.id.ed_city);
        final EditText edpin = dialogView.findViewById(R.id.ed_pin);
        final EditText edemail = dialogView.findViewById(R.id.ed_email);

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
                        edaddress.getText().toString(), edstreet.getText().toString(), edcity.getText().toString(), edemail.getText().toString(), edpin.getText().toString());
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
                                 String edaddress, String edstreet, String edcity, String edemail, String edpin) {
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


}
