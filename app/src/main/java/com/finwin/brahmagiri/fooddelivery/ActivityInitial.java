package com.finwin.brahmagiri.fooddelivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class ActivityInitial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Class fragmentClass = FragLogin.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.frame_layout_login, fragment).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void initRootCheck() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityInitial.this);
        builder1.setTitle("Your Device May Be Rooted");
        builder1.setMessage("CustMate will not work on Rooted Devices.If you would like to use this app you must unroot this device");
        builder1.setCancelable(true);
        builder1.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
                    ActivityInitial.this.finishAffinity();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityInitial.this.finishAndRemoveTask();
                }
                finish();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (RootChecker.isDeviceRooted()) {
//            initRootCheck();
//        }
    }


}
