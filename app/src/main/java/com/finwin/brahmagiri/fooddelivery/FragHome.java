package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.FoodForYouModel;
import com.finwin.brahmagiri.fooddelivery.Adapter.FoodForYouRecyAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.MenuItemModel;
import com.finwin.brahmagiri.fooddelivery.Adapter.MenuItemRecyAdapter;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.Objects;

public class FragHome extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    TextView tvViewall;
    View rootview;

    private ArrayList<MenuItemModel> homeListModelClassArrayList;
    private RecyclerView recyclerView;
    private MenuItemRecyAdapter mAdapter;

    private ArrayList<FoodForYouModel> homeListModelClassArrayList2;
    private RecyclerView recyclerView1;
    private FoodForYouRecyAdapter rAdapter;

    public static Integer image[] = {R.drawable.food2, R.drawable.food1, R.drawable.food3, R.drawable.food4};
    public static String foodName[] = {"Beef items", "Mutton items", "Chicken items", "Meat products"};
    String totalRest[] = {"fillet, steak etc.", "jowl,Spare ribs etc", "halal,Boneless etc.", "chicken,boneless.."};

    Integer foodImage[] = {R.drawable.food5, R.drawable.food6, R.drawable.food7, R.drawable.food5};
    String ratings[] = {"4.5", "4.2", "4.3", "4.5"};
    String restaurantName[] = {"Beef items", "Pork items", "Chicken", "Chicken products"};
    String restaurantCusines[] = {"Beef skeletal muscle,fillet mignon,sirloin steak,rump steak",
            "Pork belly,Pork jowl,Spare ribs",
            "Halal Cut,Boneless,Leg Meat",
            "Chicken,Breat,sliced boneless skinless"};
    String deliveryTime[] = {"20-30 min", "10-15 min", "40-45 min", "30-35 min"};
    String amount[] = {"300 Rs", "250 Rs", "280 Rs", "320 Rs"};
    String paymentMode[] = {"Online & COD", "Online & COD", "Online & COD", "Online & COD",};


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ///==========================================================================
        drawer = (DrawerLayout) rootview.findViewById(R.id.drawer_layou);
        navigationView = (NavigationView) rootview.findViewById(R.id.navigation_view);
        setToolbar();
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        ///==========================================================================

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView);
        homeListModelClassArrayList = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            MenuItemModel menuItem_Model = new MenuItemModel(String.valueOf(i),image[i], foodName[i], totalRest[i]);
            homeListModelClassArrayList.add(menuItem_Model);
        }
        mAdapter = new MenuItemRecyAdapter(getContext(), homeListModelClassArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        tvViewall = (TextView) rootview.findViewById(R.id.tv_viewall);
        tvViewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantClass.MENU_TPYE, ConstantClass.VIEW_ALL);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FragMenuTab();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        myFragment).addToBackStack(null).commit();
            }
        });

        ///===============

        recyclerView1 = (RecyclerView) rootview.findViewById(R.id.recyclerView1);
        homeListModelClassArrayList2 = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            FoodForYouModel beanClassForRecyclerView_contacts = new FoodForYouModel(ratings[i], restaurantName[i],
                    restaurantCusines[i], deliveryTime[i], amount[i], paymentMode[i], foodImage[i]);

            homeListModelClassArrayList2.add(beanClassForRecyclerView_contacts);
        }
        rAdapter = new FoodForYouRecyAdapter(getContext(), homeListModelClassArrayList2);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(rLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(rAdapter);

    }

    ///==========================================================================

    private void setToolbar() {
        toolbar = (Toolbar) rootview.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setTitle("");

        toolbar.findViewById(R.id.btn_drwmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click", "setToolbar menu");
                if (drawer.isDrawerOpen(navigationView)) {
                    drawer.closeDrawer(navigationView);
                } else {
                    drawer.openDrawer(navigationView);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    ///==========================================================================


}
