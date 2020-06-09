package com.finwin.brahmagiri.fooddelivery;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.finwin.brahmagiri.fooddelivery.Responses.Signup_Zone;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Activity.CartActivity;
import com.finwin.brahmagiri.fooddelivery.Activity.CategoryListAll;
import com.finwin.brahmagiri.fooddelivery.Activity.ItemListingActivity;
import com.finwin.brahmagiri.fooddelivery.Activity.Product;
import com.finwin.brahmagiri.fooddelivery.Adapter.BestSellingAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.FoodForYouModel;
import com.finwin.brahmagiri.fooddelivery.Adapter.ItemlistingBrahmaAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.MenuItemModel;
import com.finwin.brahmagiri.fooddelivery.Adapter.MenuItemRecyAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.TopSellingAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.HomePageCat;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.HomeTopselling;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.ResponseHomePage;
import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseAddcart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProducts;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchZone;
import com.finwin.brahmagiri.fooddelivery.Responses.Zone;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragHome extends Fragment implements NavigationView.OnNavigationItemSelectedListener, showhide {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    TextView tvViewall;
    View rootview;
    ArrayAdapter<Zone> adapters;
    EditText msearch_edit;
    RelativeLayout msummarylayout;
    ProductEntryModel productEntryModel;
    private ArrayList<MenuItemModel> homeListModelClassArrayList;
    private RecyclerView recyclerView;
    private MenuItemRecyAdapter mAdapter;
    List<ProductEntryModel> totallist;
    private ArrayList<FoodForYouModel> homeListModelClassArrayList2;
    private RecyclerView recyclerView1;
    private BestSellingAdapter rAdapter;
    ViewPager mPager;
    DatabaseHandler db;
    TextView total, count;
    ItemlistingBrahmaAdapter adapter;


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
    List<Zone> dataset;

    MaterialSpinner spinnerzone;
    private Boolean mIsSpinnerFirstCall = true;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.home_fragment, container, false);


        ///==========================================================================
        drawer = (DrawerLayout) rootview.findViewById(R.id.drawer_layou);
        msummarylayout = (RelativeLayout) rootview.findViewById(R.id.summary_layout);
        db = new DatabaseHandler(getActivity());
        spinnerzone = (MaterialSpinner) rootview.findViewById(R.id.spinner);
        dataset = new ArrayList<>();
        LoadZone();


        spinnerzone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //    Zone user = (Zone) parent.getSelectedItem();
                // displayUserData(user);
                if(!mIsSpinnerFirstCall) {
                    // Your code goes gere
                    Toast.makeText(getActivity(),"executing",Toast.LENGTH_SHORT).show();
                }
                mIsSpinnerFirstCall = false;
            }





            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        TextView gocart = rootview.findViewById(R.id.tv_viewcart);
        String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(R.layout.zone_spinner_items);


        // spinnerzone.setAdapter(adapter);
        String[] ITEMS2 = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.zone_spinner_items, ITEMS2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaterialSpinner spinner2 = (MaterialSpinner) rootview.findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "haaai", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        navigationView = (NavigationView) rootview.findViewById(R.id.navigation_view);
        mPager = (ViewPager) rootview.findViewById(R.id.pager);
        msearch_edit = rootview.findViewById(R.id.ed_search);
        count = rootview.findViewById(R.id.tv_itemcount);
        total = rootview.findViewById(R.id.totalamt);
        totallist = new ArrayList<>();

        msearch_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchkey = msearch_edit.getText().toString();
                    performSearch(searchkey);
                    return true;
                }
                return false;
            }
        });

        setToolbar();
        productEntryModel = new ProductEntryModel();
        doFetchProducts();
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

        //doFetchBestSelling();
        //doFetchCactegory();

        ///==========================================================================

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeListModelClassArrayList = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            MenuItemModel menuItem_Model = new MenuItemModel(String.valueOf(i), image[i], foodName[i], totalRest[i]);
            homeListModelClassArrayList.add(menuItem_Model);
        }
        /*mAdapter = new MenuItemRecyAdapter(getContext(), homeListModelClassArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/

        tvViewall = (TextView) rootview.findViewById(R.id.tv_viewall);
        tvViewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CategoryListAll.class));
                /*Bundle bundle = new Bundle();
                bundle.putString(ConstantClass.MENU_TPYE, ConstantClass.VIEW_ALL);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FragMenuTab();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        myFragment).addToBackStack(null).commit();*/
            }
        });

        ///===============

        recyclerView1 = (RecyclerView) rootview.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeListModelClassArrayList2 = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            FoodForYouModel beanClassForRecyclerView_contacts = new FoodForYouModel(ratings[i], restaurantName[i],
                    restaurantCusines[i], deliveryTime[i], amount[i], paymentMode[i], foodImage[i]);

            homeListModelClassArrayList2.add(beanClassForRecyclerView_contacts);
        }
      /*  rAdapter = new BestSellingAdapter(getContext());
        RecyclerView.LayoutManager rLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView1.setLayoutManager(rLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(rAdapter);*/


        return rootview;
    }

  /*  @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ///==========================================================================
        drawer = (DrawerLayout) rootview.findViewById(R.id.drawer_layou);
        msummarylayout = (RelativeLayout) rootview.findViewById(R.id.summary_layout);
        db=new DatabaseHandler(getActivity());
        spinnerzone = (MaterialSpinner) rootview.findViewById(R.id.spinner);
        dataset=new ArrayList<>();
        LoadZone();

        spinnerzone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //    Zone user = (Zone) parent.getSelectedItem();
               // displayUserData(user);
                String item = parent.getItemAtPosition(position).toString();


                if (dataset!=null){
                    Toast.makeText(getActivity(), ""+item,Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });        TextView gocart=rootview.findViewById(R.id.tv_viewcart);
        String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(R.layout.zone_spinner_items);




       // spinnerzone.setAdapter(adapter);
        String[] ITEMS2 = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),R.layout.zone_spinner_items, ITEMS2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaterialSpinner  spinner2 = (MaterialSpinner) rootview.findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "haaai",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        navigationView = (NavigationView) rootview.findViewById(R.id.navigation_view);
        mPager = (ViewPager)rootview. findViewById(R.id.pager);
        msearch_edit=rootview.findViewById(R.id.ed_search);
        count=rootview.findViewById(R.id.tv_itemcount);
        total=rootview.findViewById(R.id.totalamt);
        totallist=new ArrayList<>();

        msearch_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchkey=msearch_edit.getText().toString();
                    performSearch(searchkey);
                    return true;
                }
                return false;
            }
        });

        setToolbar();
        productEntryModel=new ProductEntryModel();
        doFetchProducts();
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

        //doFetchBestSelling();
        //doFetchCactegory();

        ///==========================================================================

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        homeListModelClassArrayList = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            MenuItemModel menuItem_Model = new MenuItemModel(String.valueOf(i), image[i], foodName[i], totalRest[i]);
            homeListModelClassArrayList.add(menuItem_Model);
        }
        *//*mAdapter = new MenuItemRecyAdapter(getContext(), homeListModelClassArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*//*

        tvViewall = (TextView) rootview.findViewById(R.id.tv_viewall);
        tvViewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CategoryListAll.class));
                *//*Bundle bundle = new Bundle();
                bundle.putString(ConstantClass.MENU_TPYE, ConstantClass.VIEW_ALL);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FragMenuTab();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        myFragment).addToBackStack(null).commit();*//*
            }
        });

        ///===============

        recyclerView1 = (RecyclerView) rootview.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeListModelClassArrayList2 = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            FoodForYouModel beanClassForRecyclerView_contacts = new FoodForYouModel(ratings[i], restaurantName[i],
                    restaurantCusines[i], deliveryTime[i], amount[i], paymentMode[i], foodImage[i]);

            homeListModelClassArrayList2.add(beanClassForRecyclerView_contacts);
        }
      *//*  rAdapter = new BestSellingAdapter(getContext());
        RecyclerView.LayoutManager rLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView1.setLayoutManager(rLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(rAdapter);*//*

    }*/

    private void LoadZone() {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<Signup_Zone> call = apiService.fetchzonesignup( "test");
        call.enqueue(new Callback<Signup_Zone>() {
            @Override
            public void onResponse(Call<Signup_Zone> call, Response<Signup_Zone> response) {
                if (response.body() != null && response.code() == 200) {
                    Signup_Zone responseFetchZone = response.body();
                    dataset = responseFetchZone.getZones();
                    adapters = new ArrayAdapter<Zone>(getActivity(), R.layout.zone_spinner_items, dataset);
                    adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerzone.setAdapter(adapters);

                }
            }

            @Override
            public void onFailure(Call<Signup_Zone> call, Throwable t) {

            }
        });
    }

    private void performSearch(String searchkey) {
        startActivity(new Intent(getActivity(), ItemListingActivity.class).putExtra("key", searchkey).putExtra("flag", ""));
    }

    private void doFetchCactegory() {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseHomePage> call = apiService.doFetchhomePage("", "", "HOME_VALUES", 0, "", "", "", "44402", "");
        call.enqueue(new Callback<ResponseHomePage>() {
            @Override
            public void onResponse(Call<ResponseHomePage> call, Response<ResponseHomePage> response) {
                Log.d("catresponse", "onResponse: " + new Gson().toJson(response.body()));
                if (response.body() != null && response.code() == 200) {
                    List<HomePageCat> dataset = response.body().getData().getTable2();
                    List<HomeTopselling> datasettopselling = response.body().getData().getTable1();
                    recyclerView1.setAdapter(new TopSellingAdapter(getActivity(), datasettopselling, FragHome.this));


                    mAdapter = new MenuItemRecyAdapter(getContext(), dataset);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);

                }

            }

            @Override
            public void onFailure(Call<ResponseHomePage> call, Throwable t) {
                Log.d("catresponse", "onResponse: " + t.getMessage());

            }
        });
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

    @Override
    public void clicked(int value, String code) {

    }

    @Override
    public void clickedpdct(int value, String code, String pname, String price) {
        {
            msummarylayout.setVisibility(View.VISIBLE);

            if (value > 0) {
                doAddmyCart(value,code,pname,price);

                if (!db.rowIdExists(Integer.parseInt(code))) {
                    productEntryModel.setId(Integer.parseInt(code));
                    productEntryModel.setProductname(pname);
                    productEntryModel.setPrice(Double.valueOf(price));
                    productEntryModel.setQuantity(value);
                    db.addContact(productEntryModel);
                    calculatetotal();
                } else {
                    Log.d("clicked", "update: " + code + "value " + value);
                    db.updateContact(value, Integer.parseInt(code));
                    calculatetotal();

                }

                //  db.rowIdExists(Integer.parseInt(code));
                Log.d("clicked", "clicked: " + db.rowIdExists(Integer.parseInt(code)));
                Log.d("clicked", "clicked: " + code);


                //  doUpdateCart(value, code, "INSERT");

            } else {
                msummarylayout.setVisibility(View.GONE);
                // doUpdateCart(0, code, "DELETE");
            }

        }

    }

    private void doAddmyCart(int value, String code, String pname, String price) {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        String userid=LocalPreferences.retrieveStringPreferences(getActivity(),"userid");

        ApiService apiService=APIClient.getClient().create(ApiService.class);
        Call<ResponseAddcart>call=apiService.doAddtiocart(mAccesstoken,"test",Integer.parseInt(userid),Integer.parseInt(code),319,value,"y","2020-06-06T10:34");
        call.enqueue(new Callback<ResponseAddcart>() {
            @Override
            public void onResponse(Call<ResponseAddcart> call, Response<ResponseAddcart> response) {
                if (response.body()!=null&&response.code()==200){

                }
            }

            @Override
            public void onFailure(Call<ResponseAddcart> call, Throwable t) {

            }
        });
    }

    @Override
    public void show(String show) {

    }

    @Override
    public void delete(String code) {
        db.deleteEntry(Integer.parseInt(code));
        adapter.notifyDataSetChanged();
        calculatetotal();
    }

    ///==========================================================================
    private void doFetchProducts() {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProducts> call = apiService.fetchproducts("319", mAccesstoken, "test");
        call.enqueue(new Callback<ResponseFetchProducts>() {
            @Override
            public void onResponse(Call<ResponseFetchProducts> call, Response<ResponseFetchProducts> response) {
                if (response != null && response.code() == 200) {
                    ResponseFetchProducts responseFetchProducts = response.body();

                    // dataSet=response.body().getProducts();
                    List<Product> dataSet = responseFetchProducts.getProducts();

                    adapter = new ItemlistingBrahmaAdapter(getActivity(), dataSet, FragHome.this);
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProducts> call, Throwable t) {

            }
        });

    }

    public void calculatetotal() {
        double sum = 0.0;
        totallist.clear();
        totallist = db.getAllContacts();
        for (int i = 0; i < totallist.size(); i++) {
            double price = totallist.get(i).getPrice() * totallist.get(i).getQuantity();
            sum = sum + price;
            Log.d("calculatetotal", "calculatetotal: " + sum);
            total.setText("" + sum);
            count.setText(" " + totallist.size());
        }
        //totalsum=sum;


    }

    public void getSelectedUser(View v) {
        Zone user = (Zone) spinnerzone.getSelectedItem();
        displayUserData(user);
    }

    private void displayUserData(Zone user) {
        String name = user.getName();
        int id = user.getId();


        Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
    }

}
