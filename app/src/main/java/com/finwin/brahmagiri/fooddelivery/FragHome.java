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

import com.finwin.brahmagiri.fooddelivery.Adapter.CartAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.Responses.Outlet;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchOutlet;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProfile;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.Utilities.Constants.database;

public class FragHome extends Fragment implements NavigationView.OnNavigationItemSelectedListener, showhide {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    TextView tvViewall;
    View rootview;
    ArrayAdapter<Zone> adapters;
    ArrayAdapter<Outlet> adapteroutlet;
    EditText msearch_edit;
    RelativeLayout msummarylayout;
    ProductEntryModel productEntryModel;
    private ArrayList<MenuItemModel> homeListModelClassArrayList;
    private RecyclerView recyclerView;
    private MenuItemRecyAdapter mAdapter;
    List<CartItem> totallist;
    private ArrayList<FoodForYouModel> homeListModelClassArrayList2;
    private RecyclerView recyclerView1;
    private BestSellingAdapter rAdapter;
    ViewPager mPager;
    TextView total, count;
    ItemlistingBrahmaAdapter adapter;
    int selectedindex;
    String outletid;
    FrameLayout mprogres;


    List<Zone> dataset;
    List<Outlet> datasetout;
    LinearLayout frameLayout;

    MaterialSpinner spinnerzone;
    MaterialSpinner spinneroutlet;
    private Boolean mIsZoneSpinnerFirstCall = true;
    private Boolean mIsOutletSpinnerFirstCall = true;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.home_fragment, container, false);
        mprogres = rootview.findViewById(R.id.progressbars);
        LoadZone();
        doFetch();
        ///==========================================================================
        drawer = (DrawerLayout) rootview.findViewById(R.id.drawer_layou);
        msummarylayout = (RelativeLayout) rootview.findViewById(R.id.summary_layout);
        spinnerzone = (MaterialSpinner) rootview.findViewById(R.id.spinner);
        spinneroutlet = (MaterialSpinner) rootview.findViewById(R.id.spinner2);
        frameLayout = rootview.findViewById(R.id.empty_layout);
        dataset = new ArrayList<>();
        datasetout = new ArrayList<>();


        String pos = LocalPreferences.retrieveStringPreferences(getActivity(), "zonepos");
        if (pos != null && !pos.equals("")) {
            spinnerzone.setSelection(Integer.parseInt(pos));
        }

        spinnerzone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    fechoutletbumyZOne(dataset.get(position).getId().toString());
                    LocalPreferences.storeStringPreference(getActivity(), "zonepos", String.valueOf(position));


                    // spinnerzone.setSelection(3);
                }
                mIsZoneSpinnerFirstCall = false;
                //    Zone user = (Zone) parent.getSelectedItem();
                // displayUserData(user);
                /*if (!mIsSpinnerFirstCall) {
                    // Your code goes gere
                    Toast.makeText(getActivity(), "executing"+dataset.get(position).getName(), Toast.LENGTH_SHORT).show();

                }
                mIsSpinnerFirstCall = false;*/
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String posout = LocalPreferences.retrieveStringPreferences(getActivity(), "outletpos");
        if (posout != null && !posout.equals("")) {
            //spinneroutlet.setSelection(Integer.parseInt(pos));
        }

        spinneroutlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != -1) {
                    // Your code goes gere
                    String outid = datasetout.get(i).getOutlet().toString();
                    doFetchProducts(outid);
                    //spinneroutlet.setSelection(i);
                    LocalPreferences.storeStringPreference(getActivity(), "outletpos", String.valueOf(i));

/*
                LocalPreferences.storeStringPreference("lastpos");
*/


                }
                mIsOutletSpinnerFirstCall = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        TextView gocart = rootview.findViewById(R.id.tv_viewcart);


        // spinnerzone.setAdapter(adapter);


        gocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        navigationView = (NavigationView) rootview.findViewById(R.id.navigation_view);

        count = rootview.findViewById(R.id.tv_itemcount);
        total = rootview.findViewById(R.id.totalamt);
        totallist = new ArrayList<>();


        setToolbar();
        productEntryModel = new ProductEntryModel();

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




        return rootview;
    }


    private void LoadZone() {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");


        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<Signup_Zone> call = apiService.fetchzonesignup(database);
        call.enqueue(new Callback<Signup_Zone>() {
            @Override
            public void onResponse(Call<Signup_Zone> call, Response<Signup_Zone> response) {
                if (response.body() != null && response.code() == 200) {
                    Signup_Zone responseFetchZone = response.body();
                    dataset = responseFetchZone.getZones();
                    String firstzone = dataset.get(0).getId().toString();
//                    LocalPreferences.storeStringPreference(getActivity(), "firstzone", firstzone);

                    adapters = new ArrayAdapter<Zone>(Objects.requireNonNull(getActivity()), R.layout.zone_spinner_items, dataset);
                    adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerzone.setAdapter(adapters);
                    String zones = LocalPreferences.retrieveStringPreferences(getActivity(), "zone");
                    for (int i = 0; i < dataset.size(); i++) {
                        int id = dataset.get(i).getId();
                        if (id == Integer.parseInt(zones)) {
                            Log.e("zoneindex", "onCreateView: " + i + zones);
                            spinnerzone.setSelection(i + 1);
                            fechoutletbumyZOne(dataset.get(i).getId().toString());
                        }
                    }


                    //fechoutletbumyZOne(firstzone);

                } else {
                    Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Signup_Zone> call, Throwable t) {

            }
        });
    }

    private void fechoutletbumyZOne(String firstzone) {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchOutlet> call = apiService.fetchoutletbuzone(mAccesstoken, database, firstzone);
        call.enqueue(new Callback<ResponseFetchOutlet>() {
            @Override
            public void onResponse(Call<ResponseFetchOutlet> call, Response<ResponseFetchOutlet> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseFetchOutlet responseFetchOutlet = response.body();
                    datasetout = responseFetchOutlet.getOutlets();
                    adapteroutlet = new ArrayAdapter<Outlet>(getActivity(), R.layout.zone_spinner_items, datasetout);
                    adapteroutlet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinneroutlet.setAdapter(adapteroutlet);
                    String posout = LocalPreferences.retrieveStringPreferences(getActivity(), "outletpos");
                    Log.d("onResponse", "outletposition: " + posout);
                    if (posout != null && !posout.equals("")) {
                        // spinneroutlet.setSelection(Integer.parseInt(posout));
                    } else {
                        //  spinneroutlet.setSelection(1);

                    }

                } else {
                    spinneroutlet.setError("Invalid id");
                    Toast.makeText(getActivity(), "Session Expired logging out", Toast.LENGTH_LONG).show();
                   LocalPreferences.clearPreferences(getActivity());
                    startActivity(new Intent(getActivity(), ActivityInitial.class));
                    getActivity().finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<ResponseFetchOutlet> call, Throwable t) {
                Toast.makeText(getActivity(), "Invalid Selection", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void performSearch(String searchkey) {
        startActivity(new Intent(getActivity(), ItemListingActivity.class).putExtra("key", searchkey).putExtra("flag", ""));
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
    public void clickedpdct(int value, String code, String pname, String outid) {
        Log.d("TAG", "clickedpdct: " + value + code + pname + outid);
        {
            msummarylayout.setVisibility(View.VISIBLE);

            if (value > 0) {
                doAddmyCart(value, code, pname, outid);
                outletid = outid;


                //  db.rowIdExists(Integer.parseInt(code));


                //  doUpdateCart(value, code, "INSERT");

            } else {
                msummarylayout.setVisibility(View.GONE);
                // doUpdateCart(0, code, "DELETE");
            }

        }

    }

    private void doAddmyCart(int value, String code, String pname, String outid) {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        String userid = LocalPreferences.retrieveStringPreferences(getActivity(), "userid");
        String status = "y";

        String time = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String json = "{\"user_id\":" + Integer.parseInt(userid) + ",\"product_id\":" + Integer.parseInt(code) + ",\"outlet\":" + Integer.parseInt(outid) + ",\"quantity\":" + value +
                ",\"status\":" + status + ",\"time\":" + time + "}";
        JsonParser parser = new JsonParser();

        JsonObject jsonObject = (JsonObject) parser.parse(json);


        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseAddcart> call = apiService.doAddtiocart(mAccesstoken, database, jsonObject);
        call.enqueue(new Callback<ResponseAddcart>() {
            @Override
            public void onResponse(Call<ResponseAddcart> call, Response<ResponseAddcart> response) {
                if (response.body() != null && response.code() == 200) {
                    fetchCartfromServer(outletid);

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
        // db.deleteEntry(Integer.parseInt(code));
        adapter.notifyDataSetChanged();
        //calculatetotal();
    }

    ///==========================================================================
    private void doFetchProducts(String outlet_id) {
        mprogres.setVisibility(View.VISIBLE);

        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProducts> call = apiService.fetchproducts(outlet_id, mAccesstoken, database);
        call.enqueue(new Callback<ResponseFetchProducts>() {
            @Override
            public void onResponse(Call<ResponseFetchProducts> call, Response<ResponseFetchProducts> response) {
                mprogres.setVisibility(View.GONE);
                if (response != null && response.code() == 200) {
                    ResponseFetchProducts responseFetchProducts = response.body();

                    // dataSet=response.body().getProducts();
                    List<Product> dataSet = responseFetchProducts.getProducts();

                    adapter = new ItemlistingBrahmaAdapter(getActivity(), dataSet, FragHome.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    if (adapter.getItemCount() == 0) {
                        // Toast.makeText(getActivity(), "No Products found for the selected outlet", Toast.LENGTH_SHORT).show();
                        frameLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProducts> call, Throwable t) {
                mprogres.setVisibility(View.GONE);

            }
        });

    }

    private void fetchCartfromServer(String cartoutid) {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        String userid = LocalPreferences.retrieveStringPreferences(getActivity(), "userid");
        Log.d("fetchCartfromServer", "fetchCartfromServer: " + cartoutid);

        String json = "{\"user_id\":" + Integer.parseInt(userid) + ",\"outlet\":" + Integer.parseInt(cartoutid) + "}";
        JsonParser parser = new JsonParser();

        JsonObject jsonObject = (JsonObject) parser.parse(json);

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseBrahmaCart> cartCall = apiService.FetchCart(mAccesstoken, database, jsonObject);
        cartCall.enqueue(new Callback<ResponseBrahmaCart>() {
            @Override
            public void onResponse(Call<ResponseBrahmaCart> call, Response<ResponseBrahmaCart> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseBrahmaCart responseBrahmaCart = response.body();

                    List<CartItem> cartItemList = responseBrahmaCart.getCartItems();
                    Log.d("calculatetotal", "calculatetotal: " + cartItemList.size());

                    totallist = response.body().getCartItems();
                    calculatetotal();


                }
            }

            @Override
            public void onFailure(Call<ResponseBrahmaCart> call, Throwable t) {

            }
        });

    }

    public void calculatetotal() {
        double sum = 0.0;
        if (totallist.size() == 0) {
            msummarylayout.setVisibility(View.GONE);
        }

        for (int i = 0; i < totallist.size(); i++) {
            double price = totallist.get(i).getPrice() * totallist.get(i).getQuantity();
            sum = sum + price;
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

    private void doFetch() {
        String userid = LocalPreferences.retrieveStringPreferences(getActivity(), "partnerid");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("partner_id", Integer.parseInt(userid));
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProfile> call = apiService.doFetchProfile(database, mAccesstoken, jsonObject);
        call.enqueue(new Callback<ResponseFetchProfile>() {
            @Override
            public void onResponse(Call<ResponseFetchProfile> call, Response<ResponseFetchProfile> response) {
                if (response.body() != null && response.code() == 200) {
                    String name = response.body().getName();
                    String mobile = response.body().getMobile();
                    String email = response.body().getEmail();

                    String address = name + " \n " + mobile + " , " + email + "\n" + response.body().getHouseNo() + " ," + response.body().getStreet() + " ," + response.body().getCity() + "  ,"
                            + response.body().getDistrict() + " ,"
                            + response.body().getState() + " ," + "\n" + "Landmark - " + response.body().getLandmark() + "\n" + "Pincode - " + response.body().getPincode();

                    LocalPreferences.storeStringPreference(getActivity(), "Address", address);
                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProfile> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (outletid != null) {
            fetchCartfromServer(outletid);
        }


    }
}
