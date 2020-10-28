package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.ActivityInitial;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityChangepasswordBinding;
import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class Changepassword extends BaseActivity {
ActivityChangepasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_changepassword);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_changepassword);
        final String user_id=getIntent().getStringExtra("user_id");
        binding.btnPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=binding.edPwd.getText().toString();
                String edcpass=binding.edCpasswd.getText().toString();
                if(pass.equals("")){

                }else if (edcpass.equals("")){

                }else if(!pass.equals(edcpass)){

                }else{
                    doChangePass(pass,edcpass,user_id);
                }
            }
        });

    }

    private void doChangePass(String pass, String edcpass,String userid) {
        showdialog();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("enter_password",pass);
        jsonObject.addProperty("confirm_password",edcpass);
        jsonObject.addProperty("user_id",Integer.parseInt(userid));


        ApiService apiService= APIClient.getClient().create(ApiService.class);
        Call<JsonObject>call=apiService.doChangepwd(database,jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissdialog();
                if (response.body()!=null&&response.code()==200){

                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        String  msg = jsonObject.getString("message");
                        Toast.makeText(getApplicationContext(),""+msg,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ActivityInitial.class));
                        finish();
                        finishAffinity();

//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissdialog();
                if (new AppUtility(Changepassword.this).checkInternet()) {

                } else {

                    Toast.makeText(Changepassword.this, "NO INTERNET", Toast.LENGTH_SHORT).show();

                }
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