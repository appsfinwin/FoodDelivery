package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.finwin.brahmagiri.fooddelivery.Adapter.ViewPageSignUpAdapter;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class ActivitySignUp extends AppCompatActivity {

    ImageButton iBtn_back;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_main);

        viewPager = (ViewPager) findViewById(R.id.viewPagerSignup);
        viewPager.setAdapter(new ViewPageSignUpAdapter(getSupportFragmentManager()));

        iBtn_back = findViewById(R.id.ibtn_signup_back);
        iBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().popBackStack();
                finish();
            }
        });

    }


}
