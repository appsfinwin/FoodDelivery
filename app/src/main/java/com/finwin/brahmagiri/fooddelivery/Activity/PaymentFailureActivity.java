package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentFailureActivity extends AppCompatActivity {
    TextView txnid, dateandtime,tvpaymessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        txnid = findViewById(R.id.txnid);
        dateandtime = findViewById(R.id.txniddateandtime);
        tvpaymessage=findViewById(R.id.paymessage);

        String txndata = getIntent().getStringExtra("trnxnid");
        String message = getIntent().getStringExtra("message");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateandtime.setText("" + formatter.format(date));
        txnid.setText("Transaction No."+txndata);
        tvpaymessage.setText(message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
        finishAffinity();
    }
}
