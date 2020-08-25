package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.finwin.brahmagiri.fooddelivery.ActivityInitial;
import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

      //  Glide.with(getApplicationContext()).load("https://drive.google.com/file/d/1rGSlVhg9h0qyrCTsFGYt3ZRhY4brE6Tp/view").into(im);
      //  startActivity(new Intent(getApplicationContext(), PaymentSuccessActivity.class).putExtra("trnxnid", "orderId").putExtra("paymode", "onlinepayment"));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), PaymentSuccess.class).putExtra("trnxnid", "orderId").putExtra("paymode", "onlinepayment"));
                finishAffinity();


                //the current activity will get finished.
            }
        }, 2000);


    }
}
