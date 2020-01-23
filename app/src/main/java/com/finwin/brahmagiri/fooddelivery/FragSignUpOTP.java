package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragSignUpOTP extends Fragment {

    RequestQueue requestQueue;
    SweetAlertDialog sweetDialog;

    Button btn_SubmitOTP;
    EditText edt_OTP;
    TextView tv_ReSendOTP;
    String demessage, TrMsg, error,
            StrOTP_data, StrOTP_id,
            Str_OTP;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_signup_otp, container, false);
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        sweetDialog = new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.PROGRESS_TYPE);

        edt_OTP = rootView.findViewById(R.id.edt_signup_otp);
        tv_ReSendOTP = rootView.findViewById(R.id.tv_signup_resend_otp);
        btn_SubmitOTP = rootView.findViewById(R.id.btn_signup_sbmt);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_SubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_OTP.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter OTP", Toast.LENGTH_LONG).show();
                } else {
                    btn_SubmitOTP.setEnabled(false);
                    Str_OTP = edt_OTP.getText().toString();
//                    Transfer();
                    goToLogin();
                }
            }
        });

        tv_ReSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ReSendOTP.setEnabled(false);
//                re_generateOTP();
            }
        });
    }

    public static FragSignUpOTP newInstance(String text) {
        FragSignUpOTP f = new FragSignUpOTP();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    private void goToLogin() {
        try {
            Objects.requireNonNull(getActivity()).finish();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                FragmentManager fragmentManager = getFragmentManager();
                Class fragmentClass = FragLogin.class;
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                assert fragmentManager != null;
                fragmentManager.beginTransaction().replace(R.id.frame_layout_login, fragment).commit();
            } catch (java.lang.InstantiationException | IllegalAccessException ee) {
                ee.printStackTrace();
            }
        }
    }

}
