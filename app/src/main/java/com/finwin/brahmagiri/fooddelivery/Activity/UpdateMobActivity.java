package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.finwin.brahmagiri.fooddelivery.Responses.ResponsenumUpdate;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityEnterMobBinding;
import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class UpdateMobActivity extends BaseActivity {
    ActivityEnterMobBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_enter_mob);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_mob);
        String mobile=getIntent().getStringExtra("data");
        binding.edUsername.setText(mobile);
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mob = binding.edUsername.getText().toString();
                if (mob.equals("")) {
                    Toast.makeText(UpdateMobActivity.this, "Field Empty", Toast.LENGTH_SHORT).show();
                } else if (!new AppUtility(UpdateMobActivity.this).isValidMobile(mob)) {
                    Toast.makeText(UpdateMobActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();

                } else {
                    doSend(mob);
                }
            }
        });

    }

    private void doSend(final String mob) {
        String partnerid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");

        showdialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("partner_id", Integer.parseInt(partnerid));
        jsonObject.addProperty("mobile", mob);
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponsenumUpdate> call = apiService.doSendOtpforUpdation(mAccesstoken, database, jsonObject);
        call.enqueue(new Callback<ResponsenumUpdate>() {
            @Override
            public void onResponse(Call<ResponsenumUpdate> call, Response<ResponsenumUpdate> response) {
                dismissdialog();
                if (response.body() != null && response.code() == 200) {
                    // startActivity(new Intent(getApplicationContext(), OtpVerify.class).putExtra("isfromupdate", "yes").putExtra("mob", mob));
                    ResponsenumUpdate responsenumUpdate=response.body();
                    String msg = responsenumUpdate.getUser_id();
                    String usmsg=responsenumUpdate.getMessage();
                    try {

                        if (usmsg!=null&&usmsg.equalsIgnoreCase("Mobile Already Registered!")){
                         SweetAlertDialog   sweetAlertDialog = new SweetAlertDialog(UpdateMobActivity.this, SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitleText("Already Exist !");
                            sweetAlertDialog.setContentText(usmsg);
                            sweetAlertDialog.show();
                        }else{
                            startActivity(new Intent(getApplicationContext(), OtpVerify.class)
                                    .putExtra("isfromupdate", "yes")
                                    .putExtra("user_id", msg)
                                    .putExtra("mob", mob));
                            finish();
                        }




                    } catch (Exception  e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponsenumUpdate> call, Throwable t) {
                dismissdialog();

            }
        });
    }

    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}