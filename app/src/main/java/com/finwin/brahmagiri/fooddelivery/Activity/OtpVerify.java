package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mukesh.OtpView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class OtpVerify extends BaseActivity {
    private OtpView otpView;
    Button btnext, Rsend;
    TextView mTextField;
    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        otpView = findViewById(R.id.otp_view);
        mTextField = findViewById(R.id.otptimer);
        btnext = findViewById(R.id.btn_next);
        Rsend = findViewById(R.id.btndresend);
        final String mob = getIntent().getStringExtra("mob");
        flag = getIntent().getStringExtra("isfromupdate");
        Rsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSend(mob);

            }
        });

        final String id = getIntent().getStringExtra("user_id");
        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = otpView.getText().toString();
                if (otp.equals("")) {
                    Toast.makeText(OtpVerify.this, "Please Enter Otp", Toast.LENGTH_SHORT).show();
                } else if (flag.equalsIgnoreCase("yes")) {
                    Validateotpmob(otp, id, mob);
                } else {
                    Validateotp(otp, id);
                }
            }
        });
        startcount();
    }

    private void startcount() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("OTP will Expire in : " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                Rsend.setVisibility(View.VISIBLE);
                //  mTextField.setText("done!");
            }

        }.start();
    }


    private void Validateotpmob(String otp, String id, String mob) {
        showdialog();
        String partnerid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");

        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");

        JsonObject jsonObjects = new JsonObject();
        jsonObjects.addProperty("partner_id", Integer.parseInt(partnerid));
        jsonObjects.addProperty("user_id", Integer.parseInt(id));
        jsonObjects.addProperty("otp", otp);
        jsonObjects.addProperty("mobile", mob);

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<JsonObject> call = apiService.Verifyotpformobilenum(mAccesstoken, database, jsonObjects);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissdialog();
                if (response.body() != null && response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        String msg = jsonObject.getString("message");
                        String id = jsonObject.getString("user_id");

                        if (msg.equalsIgnoreCase("Mobile Number Updated Successfully")) {
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();

                        }
//
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


    private void Validateotp(String otp, String id) {
        showdialog();
        JsonObject jsonObjects = new JsonObject();
        jsonObjects.addProperty("otp", otp);
        jsonObjects.addProperty("user_id", Integer.parseInt(id));

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<JsonObject> call = apiService.Verifyotp(database, jsonObjects);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissdialog();
                if (response.body() != null && response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        String msg = jsonObject.getString("message");
                        String id = jsonObject.getString("user_id");

                        if (msg.equalsIgnoreCase("OTP Verified")) {
                            startActivity(new Intent(getApplicationContext(), Changepassword.class).putExtra("user_id", id));

                        } else {
                            Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();

                        }
//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissdialog();
                if (new AppUtility(OtpVerify.this).checkInternet()) {

                } else {

                    Toast.makeText(OtpVerify.this, "NO INTERNET", Toast.LENGTH_SHORT).show();

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

    private void doSend(String mob) {
        showdialog();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile", mob);
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<JsonObject> call = apiService.doSendOtp(database, jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismissdialog();
                startcount();
                if (response.body() != null && response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        String msg = jsonObject.getString("user_id");
                        //startActivity(new Intent(getApplicationContext(),OtpVerify.class).putExtra("user_id",msg));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissdialog();
                if (new AppUtility(OtpVerify.this).checkInternet()) {

                } else {

                    Toast.makeText(OtpVerify.this, "NO INTERNET", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}