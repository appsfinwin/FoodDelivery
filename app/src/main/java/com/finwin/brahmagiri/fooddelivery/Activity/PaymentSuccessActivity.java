package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.ActivityInitial;
import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentSuccessActivity extends AppCompatActivity {
TextView transaction,dateandtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        transaction=findViewById(R.id.tv_txn);
        dateandtime=findViewById(R.id.tv_txndate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println();
        final String tnxid=getIntent().getStringExtra("trnxnid");
      //  transaction.setText("Transaction Reference No. "+transaction);
      //  dateandtime.setText("Date and Time "+formatter.format(date));


        //  Glide.with(getApplicationContext()).load("https://drive.google.com/file/d/1rGSlVhg9h0qyrCTsFGYt3ZRhY4brE6Tp/view").into(im);
      //  startActivity(new Intent(getApplicationContext(), PaymentSuccessActivity.class).putExtra("trnxnid", "orderId").putExtra("paymode", "onlinepayment"));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), PaymentSuccess.class).putExtra("trnxnid", tnxid).putExtra("paymode", "op"));
                finishAffinity();


                //the current activity will get finished.
            }
        }, 2000);


    }
}
