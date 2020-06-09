package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.MyOrderAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.MyOrderModel;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.Map;

public class FragMyOrder extends Fragment {

    private ArrayList<MyOrderModel> homeListModelClassArrayList1;
    private RecyclerView menuRecycler;
    private MyOrderAdapter bAdapter;

    String foodName[] = {"Beef items", "Chicken"};
    String quantity[] = {"Quantity 1", "Quantity 1"};
    String rupees[] = {"Rs 300", "Rs 300"};

    String itemArryId, itemArryName, itemArryCount, itemArryAmount;
    ImageButton ibtn_back;
    View rootview;
    TextView tvOK;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.my_orders_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuRecycler = (RecyclerView) rootview.findViewById(R.id.menuRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        menuRecycler.setLayoutManager(layoutManager);
        menuRecycler.setItemAnimator(new DefaultItemAnimator());

        homeListModelClassArrayList1 = new ArrayList<>();

//        for (int i = 0; i < foodName.length; i++) {
//            MyOrderModel beanClassForRecyclerView_contacts = new MyOrderModel(foodName[i], quantity[i], rupees[i]);
//            homeListModelClassArrayList1.add(beanClassForRecyclerView_contacts);
//        }

        if (ConstantClass.hMapCartItem != null) {
            for (Map.Entry<String, Map<String, String>> entry : ConstantClass.hMapCartItem.entrySet()) {
                itemArryId = entry.getKey();
                for (Map.Entry<String, String> secndEntry : entry.getValue().entrySet()) {
                    if (secndEntry.getKey().equals(ConstantClass.H_ITEM_NAME)) {
                        itemArryName = secndEntry.getValue();
                    }
                    if (secndEntry.getKey().equals(ConstantClass.H_ITEM_AMNT)) {
                        itemArryAmount = secndEntry.getValue();
                    }
                    if (secndEntry.getKey().equals(ConstantClass.H_ITEM_COUNT)) {
                        itemArryCount = secndEntry.getValue();
                    }
//                itemArryName = secndEntry.getKey();
//                itemArryAmount = String.valueOf(secndEntry.getValue());
                }

                MyOrderModel myOrderModel = new MyOrderModel(itemArryName, itemArryCount, itemArryAmount);
                homeListModelClassArrayList1.add(myOrderModel);
            }
        }
        bAdapter = new MyOrderAdapter(getContext());
        menuRecycler.setAdapter(bAdapter);


        tvOK = rootview.findViewById(R.id.tv_myorder_ok);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                assert getFragmentManager() != null;
//                getFragmentManager().popBackStack();
                clearStack();
            }
        });

        ibtn_back = rootview.findViewById(R.id.ibtn_back_mordr);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

    }

    public void clearStack() {
//        //Here we are clearing back stack fragment entries
//        int backStackEntry = Objects.requireNonNull(getFragmentManager()).getBackStackEntryCount();
//        if (backStackEntry > 0) {
//            for (int i = 0; i < backStackEntry; i++) {
//                getFragmentManager().popBackStackImmediate();
//            }
//        }

        //Here we are removing all the fragment that are shown here
        if (getFragmentManager().getFragments() != null && getFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }

        Fragment fr = new FragHome();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fr);
        fragmentTransaction.commit();
    }

}
