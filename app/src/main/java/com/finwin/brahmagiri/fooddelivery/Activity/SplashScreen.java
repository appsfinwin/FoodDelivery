package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.finwin.brahmagiri.fooddelivery.ActivityInitial;
import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCheckVersion;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.BuildConfig;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.Utilities.Constants.database;

public class SplashScreen extends AppCompatActivity {
    String versionName;
    int verCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        versionName = BuildConfig.VERSION_NAME;
        verCode = BuildConfig.VERSION_CODE;
        dofetchVersion();


    }

    private void dofetchVersion() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("app_type","Consumer App");
        ApiService apiService= APIClient.getClient().create(ApiService.class);
        Call<ResponseCheckVersion>call=apiService.doFetchVersionControl(database,jsonObject);
        call.enqueue(new Callback<ResponseCheckVersion>() {
            @Override
            public void onResponse(Call<ResponseCheckVersion> call, Response<ResponseCheckVersion> response) {
                if (response.body()!=null&&response.code()==200){
                    ResponseCheckVersion responseCheckVersion=response.body();

                    int apiversioncode=Integer.parseInt(responseCheckVersion.getVersionCode());
                    if (verCode<apiversioncode){
                        startActivity(new Intent(getApplicationContext(),DemoSplash.class));
                        finish();
                    }else{
                        setLogin();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckVersion> call, Throwable t) {

            }
        });
    }

    private void setLogin() {
        final boolean islooged= LocalPreferences.retrieveBooleanPreferences(getApplicationContext(),"isLoggedin");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (islooged){
                    Intent i=new Intent(getApplicationContext(),
                            ActivityMain.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                }else{
                    Intent i=new Intent(getApplicationContext(),
                            ActivityInitial.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                }

                //the current activity will get finished.
            }
        }, 1000);
    }
}
