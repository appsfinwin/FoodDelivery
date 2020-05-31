package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.finwin.brahmagiri.fooddelivery.ActivityInitial;
import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
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
