package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.finwin.brahmagiri.fooddelivery.FragSignUpDetails;
import com.finwin.brahmagiri.fooddelivery.FragSignUpOTP;

public class ViewPageSignUpAdapter extends FragmentPagerAdapter {

    public ViewPageSignUpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return FragSignUpDetails.newInstance("FirstFragment, Instance 1");
            case 1:
                return FragSignUpOTP.newInstance("SecondFragment, Instance 2");
            default:
                return FragSignUpDetails.newInstance("ThirdFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
