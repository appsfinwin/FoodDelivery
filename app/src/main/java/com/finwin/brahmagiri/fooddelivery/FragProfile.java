package com.finwin.brahmagiri.fooddelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.Objects;

public class FragProfile extends Fragment {

    View rootview;
    Button btnLogin;
    ImageButton ibtn_back;
    ImageView img_Chng_homeAdd, img_Chng_ofcAdd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.frag_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ibtn_back = rootview.findViewById(R.id.ibtn_back_prof);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        img_Chng_homeAdd = rootview.findViewById(R.id.imgv_home);
        img_Chng_homeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePayment("Home");
            }
        });

        img_Chng_ofcAdd = rootview.findViewById(R.id.imgv_ofc);
        img_Chng_ofcAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePayment("Office");
            }
        });

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
                Log.e("onClick: ","+++++" );
            }
        });

        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("onClick: ","-----" );
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
