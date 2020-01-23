package com.finwin.brahmagiri.fooddelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FragLogin extends Fragment {

    View rootview;
    LinearLayout linSignup;
    Button btnLogin;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        btnLogin = rootview.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getPendingLoanList();
                startActivity(new Intent(getContext(), ActivityMain.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        linSignup = rootview.findViewById(R.id.lin_signup);
        linSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), ActivitySignUp.class);
                startActivity(i);

//                Fragment fr = new ActivitySignUp();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fm).beginTransaction();
//                fragmentTransaction.add(R.id.frame_layout_login, fr);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }

    private void getPendingLoanList() {
//        String api_url = "http://35.196.223.10:911/EncryptDecrypt_Api";
        String api_url = "http://35.196.223.10:911/EncryptDecrypt_Api";
//        proDialog.setTitleText("Please wait..");
//        proDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, api_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response=>", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                // the POST parameters:
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("Type", "Encrypt");
                hashMap.put("Input", "Encrypt");
                return hashMap;
            }
        };
        requestQueue.add(postRequest);
    }

}
