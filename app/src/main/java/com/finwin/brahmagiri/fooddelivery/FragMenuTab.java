package com.finwin.brahmagiri.fooddelivery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ToCartButtonListener;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapCartItem;

public class FragMenuTab extends Fragment implements ToCartButtonListener {

    View rootview;
    ImageButton ibtn_back;
    String StrMenuType;

    TextView tvToCart;
    TabLayout tabLayout;
    TabAdapter tabAdapter;
    ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.item_list_menuview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hMapCartItem = new HashMap<String, Map<String, String>>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            StrMenuType = bundle.getString(ConstantClass.MENU_TPYE, "");
        }

        tabLayout = rootview.findViewById(R.id.tab);
        viewPager = rootview.findViewById(R.id.view_pgr);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        try {
            if (StrMenuType.equals(ConstantClass.VIEW_ALL)) {
                tabLayout.addTab(tabLayout.newTab().setText("POPULAR"));
                tabLayout.addTab(tabLayout.newTab().setText("SPECIAL"));

//                //To hide the first tab
//                ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
//                tabLayout.removeTabAt(0);
//                //Set the next  tab as selected tab
//
//                TabLayout.Tab tab = tabLayout.getTabAt(1);
//                Objects.requireNonNull(tab).select();

            } else {
                tabLayout.addTab(tabLayout.newTab().setText("SELECTED"));
                tabLayout.addTab(tabLayout.newTab().setText("POPULAR"));
                tabLayout.addTab(tabLayout.newTab().setText("SPECIAL"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            tabLayout.addTab(tabLayout.newTab().setText("POPULAR"));
            tabLayout.addTab(tabLayout.newTab().setText("SPECIAL"));
        }

        tabLayout.setTabGravity(TabLayout.MODE_FIXED);
        tabAdapter = new TabAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        ibtn_back = rootview.findViewById(R.id.ibtn_back);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });


        tvToCart = rootview.findViewById(R.id.tv_tocart);
        tvToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new FragCart();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fm).beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fr);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("tocart_data"));
    }

    //========================================================================================================
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String intentRslt = intent.getStringExtra("data");
                if (!TextUtils.isEmpty(intentRslt)) {
                    if (intentRslt.equals("hide_btn")) {
                        tvToCart.setVisibility(View.GONE);
                    } else if (intentRslt.equals("show_btn")) {
                        if (tvToCart.getVisibility() == View.INVISIBLE || tvToCart.getVisibility() == View.GONE)
                            tvToCart.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
    //========================================================================================================

    public static void addToCart(String itemId, String itemName, String itemAmount, int count) {
        Map<String, String> data = new TreeMap<String, String>();
        data.put(ConstantClass.H_ITEM_NAME, itemName);
        data.put(ConstantClass.H_ITEM_AMNT, itemAmount);
        data.put(ConstantClass.H_ITEM_COUNT, String.valueOf(count));
        hMapCartItem.put(itemId, data);
    }

    public static void removeFromCart(String itemId) {
        hMapCartItem.remove(itemId);
    }


    public class TabAdapter extends FragmentStatePagerAdapter {
        int mnumoftabs;
        FragMenuItemSELECT tab1;
        FragMenuItem tab2;
        FragMenuItem tab3;

        TabAdapter(FragmentManager fm, int numoftabs) {
            super(fm);
            this.mnumoftabs = numoftabs;
        }

        @Override
        public Fragment getItem(int position) {
            try {
                if (StrMenuType.equals(ConstantClass.VIEW_ALL)) {
                    switch (position) {
                        case 0:
                            if (tab2 == null)
                            tab2 = new FragMenuItem();
                            return tab2;
                        case 1:
                            if (tab3 == null)
                            tab3 = new FragMenuItem();
                            return tab3;
                        default:
                            return null;
                    }
                } else {
                    switch (position) {
                        case 0:
                            if (tab1 == null) {
                                Bundle bundle = new Bundle();
                                bundle.putString(ConstantClass.MENU_ITEM_ID, StrMenuType);
                                tab1 = new FragMenuItemSELECT();
                                tab1.setArguments(bundle);
                            }
                            return tab1;
                        case 1:
                            if (tab2 == null)
                            tab2 = new FragMenuItem();
                            return tab2;
                        case 2:
                            if (tab3 == null)
                            tab3 = new FragMenuItem();
                            return tab3;
                        default:
                            return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                switch (position) {
                    case 0:
                        if (tab2 == null)
                        tab2 = new FragMenuItem();
                        return tab2;
                    case 1:
                        if (tab3 == null)
                        tab3 = new FragMenuItem();
                        return tab3;
                    default:
                        return null;
                }
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mnumoftabs;
        }

    }


    //=========================

    @Override
    public void toCartHide() {
        Log.e("toCartHide: ", "ttttttttt");
    }

    @Override
    public void toCartShow() {
        Log.e("toCartShow: ", "ttttttttt");
    }

    @Override
    public void calcSubTotal() {
        Log.e("calcSubTotal: ", "ttttttttt");
    }

    //=========================


}

