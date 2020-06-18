package com.finwin.brahmagiri.fooddelivery;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseLogin;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class FragLogin extends Fragment {

    View rootview;
    LinearLayout linSignup;
    Button btnLogin;
    RequestQueue requestQueue;
    EditText edUsername,edPass;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        edUsername=rootview.findViewById(R.id.ed_username);
        edPass=rootview.findViewById(R.id.ed_passwd);


        btnLogin = rootview.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getPendingLoanList();


                String username=edUsername.getText().toString().trim();
                String password=edPass.getText().toString().trim();
                if (username.equals("")){
                 edUsername.setError("This field is empty");
                }else if (password.equals("")){
                    edPass.setError("This field is empty");
                }else{
                    doLogin(username,password);
                }

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


    private void doLogin(String username, String password) {
        ApiService apiService= APIClient.getClient().create(ApiService.class);
        Call<ResponseLogin> call=apiService.dologinoutlet("test",username,password);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                if (response!=null&&response.code()==200){
                    ResponseLogin responseLogin=response.body();
                    String mAccesstoken=response.body().getAccessToken();
                    if (mAccesstoken!=null){
                        LocalPreferences.storeStringPreference(getActivity(),"Accesstoken",mAccesstoken);
                        LocalPreferences.storeStringPreference(getActivity(),"partnerid",responseLogin.getPartnerId().toString());
                        LocalPreferences.storeStringPreference(getActivity(),"zone",responseLogin.getZone().toString());

                        LocalPreferences.storeBooleanPreference(getActivity(),"isLoggedin",true);
                        LocalPreferences.storeStringPreference(getActivity(),"userid",response.body().getUid().toString());
                        startActivity(new Intent(getContext(), ActivityMain.class));
                        Objects.requireNonNull(getActivity()).finish();

                    }
                }else{
                    Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getActivity(), "Login Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

}
