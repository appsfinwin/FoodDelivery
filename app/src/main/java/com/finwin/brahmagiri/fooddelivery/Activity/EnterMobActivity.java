package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityEnterMobBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class EnterMobActivity extends BaseActivity {
ActivityEnterMobBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_enter_mob);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_enter_mob);
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mob=binding.edUsername.getText().toString();
                if (mob.equals("")){
                    Toast.makeText(EnterMobActivity.this, "Field Empty", Toast.LENGTH_SHORT).show();
                }else if(!new AppUtility(EnterMobActivity.this).isValidMobile(mob)){

                }else{
                    doSend(mob);
                }
            }
        });

    }

    private void doSend(final String mob) {
        showdialog();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("mobile",mob);
        ApiService apiService= APIClient.getClient().create(ApiService.class);
        Call<JsonObject>call=apiService.doSendOtp(database,jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissdialog();
                if (response.body()!=null&&response.code()==200){
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                      String  msg = jsonObject.getString("user_id");
                        startActivity(new Intent(getApplicationContext(),OtpVerify.class).putExtra("user_id",msg).putExtra("mob",mob));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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