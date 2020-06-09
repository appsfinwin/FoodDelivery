package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finwin.brahmagiri.fooddelivery.Adapter.MenuItemInListAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.MenuItemInListModel;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapItemsToCart;

public class FragMenuItem extends Fragment {
    //    private ArrayList<HashMap<String, String>> homeListModelClassArrayList1;
    private ArrayList<MenuItemInListModel> homeListModelClassArrayList1;
    private RecyclerView menuRecycler;
    private MenuItemInListAdapter bAdapter;

    String foodName[] = {"Daal Makhni", "Makhni", "Daal", "beef", "Chicken"};
    String foodType1[] = {"North indian, Punjabi ,chinessem Sea Food,Thai, Italian", "North indian, Punjabi ,chinessem Sea Food,Thai, Italian", "North indian, Punjabi ,chinessem Sea Food,Thai, Italian", "North indian, Punjabi ,chinessem Sea Food,Thai, Italian", "North indian, Punjabi ,chinessem Sea Food,Thai, Italian"};
    String rupees[] = {"300", "300", "300", "300", "300"};

    View rootview;
    String StrItemId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu_view_frag_itm, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hMapItemsToCart = new HashMap<String, String>();

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            StrItemId = bundle.getString(ConstantClass.MENU_ITEM_ID, "");
//        }
//        Log.e("StrItemId SELECT", StrItemId);


        menuRecycler = view.findViewById(R.id.menuRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        menuRecycler.setLayoutManager(layoutManager);
        menuRecycler.setItemAnimator(new DefaultItemAnimator());

        homeListModelClassArrayList1 = new ArrayList<>();
        for (int i = 0; i < foodName.length; i++) {
            MenuItemInListModel menuItemInListModel = new MenuItemInListModel(String.valueOf(300 + i), FragHome.image[0],
                    foodName[i], foodType1[i], rupees[i]);
            homeListModelClassArrayList1.add(menuItemInListModel);
        }

        bAdapter = new MenuItemInListAdapter(getContext(), homeListModelClassArrayList1, new FragMenuTab());
        menuRecycler.setAdapter(bAdapter);


//        int idd = 0;
//        try {
//            idd = Integer.parseInt(StrItemId);
//        } catch (Exception e) {
//        }
//
//        for (int i = 0; i < 5; i++) {
//            MenuItemInListModelSELECT menuItemInListModel = new MenuItemInListModelSELECT(
//                    String.valueOf(i), FragHome.image[idd], FragHome.foodName[idd], foodType1[idd], rupees[idd]);
//            homeListModelClassArrayList1.add(menuItemInListModel);
//        }
//
//        bAdapter = new MenuItemInListAdapterSELECT(getContext(), homeListModelClassArrayList1, new FragMenuTab());
//        menuRecycler.setAdapter(bAdapter);


    }

}

