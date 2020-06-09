package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        ImageView im=findViewById(R.id.imv);
      //  Glide.with(getApplicationContext()).load("https://drive.google.com/file/d/1rGSlVhg9h0qyrCTsFGYt3ZRhY4brE6Tp/view").into(im);
    }
}
