package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.Objects;

public class FragSignUpDetails extends Fragment {

    View rootview;
    Button btnSignup;
    TextView tvSignin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSignup = rootview.findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySignUp.viewPager.setCurrentItem(1, true);
            }
        });

        tvSignin = rootview.findViewById(R.id.tv_signin);
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

    }

    public static FragSignUpDetails newInstance(String text) {
        FragSignUpDetails f = new FragSignUpDetails();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }



}
