package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.CartAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.CartModel;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ToCartButtonListener;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.TOTAL_AMNT;

public class FragCart extends Fragment implements ToCartButtonListener {

    View rootview;
    TextView tvCartSubtotal, tvCartTotal, tvCharge, tvCartGST;
    LinearLayout linrConfmPay;
    RecyclerView cartRecycler;
    ImageButton ibtn_back;

    private CartAdapter mAdapter;
    private ArrayList<CartModel> homeListModelClassArrayList;
    double delvryCharge = 30,
            total_amnt = 0, gst = 0, gTotal = 0;

    String itemArryId, itemArryName, itemArryCount, itemArryAmount;
    String foodName[] = {"Beef items", "Chicken"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCartSubtotal = (TextView) rootview.findViewById(R.id.tv_cart_subtotal);
        tvCartTotal = (TextView) rootview.findViewById(R.id.tv_cart_total);
        tvCharge = (TextView) rootview.findViewById(R.id.tv_cart_chrg);
        tvCartGST = (TextView) rootview.findViewById(R.id.tv_cart_gst);

        cartRecycler = (RecyclerView) rootview.findViewById(R.id.cartRecycler);
        homeListModelClassArrayList = new ArrayList<>();

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
                }
                CartModel cartModel = new CartModel(itemArryId, itemArryName, itemArryCount, itemArryAmount);
                homeListModelClassArrayList.add(cartModel);
            }
        }

     //   mAdapter = new CartAdapter(getActivity(), homeListModelClassArrayList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        cartRecycler.setLayoutManager(mLayoutManager);
        cartRecycler.setItemAnimator(new DefaultItemAnimator());
        cartRecycler.setAdapter(mAdapter);

        linrConfmPay = rootview.findViewById(R.id.lnrlay_confmpay);
        linrConfmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bndl = new Bundle();
                bndl.putString(TOTAL_AMNT, String.valueOf(gTotal));
                Fragment fr = new FragConfirmOrder();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fm).beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fr);
                fr.setArguments(bndl);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if (ConstantClass.hMapCartItem != null) {
            setSubtotalAmount();
        }

        ibtn_back = rootview.findViewById(R.id.ibtn_back_crt);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    public void setSubtotalAmount() {
        if (ConstantClass.hMapCartItem.isEmpty()) {
            delvryCharge = 0;
        }
//        total_amnt = 0;
        for (Map.Entry<String, Map<String, String>> entry : ConstantClass.hMapCartItem.entrySet()) {
            for (Map.Entry<String, String> secndEntry : entry.getValue().entrySet()) {
                Log.e("secndEntry", String.valueOf(secndEntry));
                if (secndEntry.getKey().equals(ConstantClass.H_ITEM_AMNT)) {
                    total_amnt = total_amnt + Double.parseDouble(secndEntry.getValue());
                }

            }
        }
        gst = ((double) 18 / (double) 100) * total_amnt;
        gTotal = total_amnt + delvryCharge + gst;

        tvCartSubtotal.setText(String.valueOf(total_amnt));
        tvCharge.setText(String.valueOf(delvryCharge));
        tvCartGST.setText(String.valueOf(gst));
        tvCartTotal.setText(String.valueOf(gTotal));

//        Log.e("gst: ", String.valueOf(gst));
    }

    @Override
    public void calcSubTotal() {

        setSubtotalAmount();
    }

    @Override
    public void toCartHide() {

    }

    @Override
    public void toCartShow() {

    }


}
