package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class StatusActivity extends Activity {
    TextView status;

    String mStatus[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_status);

        status = (TextView) findViewById(R.id.status);

        Bundle bundle = this.getIntent().getExtras();
        mStatus = bundle.getString("status").toString().split("\\|");

        status.setText(bundle.getString("status"));

    }

}

