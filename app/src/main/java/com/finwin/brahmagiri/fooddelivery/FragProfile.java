package com.finwin.brahmagiri.fooddelivery;

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

public class FragProfile extends Fragment {
    FragProfileBinding binding;
    View rootview;
    Button btnLogin;
    ImageButton ibtn_back;
    ImageView img_Chng_homeAdd, img_Chng_ofcAdd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View view= rootview = inflater.inflate(R.layout.frag_profile, container, false);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.frag_profile, container, false);



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
        String userid = LocalPreferences.retrieveStringPreferences(getActivity(), "partnerid");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("partner_id", Integer.parseInt(userid));
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProfile> call = apiService.doFetchProfile("test", mAccesstoken, jsonObject);
        call.enqueue(new Callback<ResponseFetchProfile>() {
            @Override
            public void onResponse(Call<ResponseFetchProfile> call, Response<ResponseFetchProfile> response) {
                if (response.body() != null && response.code() == 200) {
                    String name = response.body().getName();
                    String mobile=response.body().getMobile();
                    String email=response.body().getEmail();
                    binding.tvname.setText(name);
                    binding.tvEmail.setText(email);
                    binding.tvMobile.setText(mobile);
                    String address=response.body().getHouseNo()+" ,"+response.body().getStreet()+" ,"+response.body().getCity()+"  ,"
                            +response.body().getDistrict()+" ,"
                            +response.body().getState()+" ,"+ "\n"+"Landmark - "+response.body().getLandmark()+ "\n"+"Pincode - "+response.body().getPincode();

                    binding.address1.setText(address);
                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProfile> call, Throwable t) {

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
        b.setView(dialogView);
        b.setCancelable(false);
        b.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("onClick: ", "+++++");
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


}
