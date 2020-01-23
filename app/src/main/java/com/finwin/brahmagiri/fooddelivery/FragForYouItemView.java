package com.finwin.brahmagiri.fooddelivery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.ForYouListViewAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ForYouListViewModel;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapCartItem;

public class FragForYouItemView extends Fragment {

    View rootview;
    ImageButton ibtn_back;
    TextView tvToCart;
    private RecyclerView recyclerview;
    private ArrayList<ForYouListViewModel> itemListModelArrayList;
    private ForYouListViewAdapter itemListVAdapter;

//    Integer image1[] = {R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck};
//    Integer image2[] = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4};
//    String textoffer[] = {"75%", "25%", "30%", "25%"};
//    String textprice[] = {"75", "225", "210", "145"};
//    //    String textshoes[] = {"Ninja high top sneackers", "Ninja high top sneackers", "Ninja high top sneackers", "Ninja high top sneackers"};
//    String textname[] = {"Beef items", "Mutton items", "Chicken items", "Meat products"};

    Integer image1[] = {R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck,
            R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck, R.drawable.ic_like_uncheck};
    Integer image2[] = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4,
            R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4};
    String textoffer[] = {"75%", "25%", "30%", "25%", "75%", "25%", "30%", "25%"};
    String textprice[] = {"100", "200", "300", "400", "500", "600", "700", "800"};
    String textname[] = {"Beef items", "Mutton items", "Chicken items", "Meat products", "Beef items", "Mutton items", "Chicken items", "Meat products"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.item_list_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hMapCartItem = new HashMap<String, Map<String, String>>();

        recyclerview = rootview.findViewById(R.id.recyclerview4);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        itemListModelArrayList = new ArrayList<>();
        for (int i = 0; i < image1.length; i++) {
            ForYouListViewModel lvModel = new ForYouListViewModel(String.valueOf(500 + i), image1[i], image2[i],
                    textoffer[i], textprice[i], textname[i]);
            itemListModelArrayList.add(lvModel);
        }

        itemListVAdapter = new ForYouListViewAdapter(getActivity(), itemListModelArrayList);
        recyclerview.setAdapter(itemListVAdapter);

        ibtn_back = rootview.findViewById(R.id.ibtn_back_ofr);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        tvToCart = rootview.findViewById(R.id.tv_tocart_ofr);
        tvToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new FragCart();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fm).beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fr);
                fragmentTransaction.addToBackStack(null);
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

}
