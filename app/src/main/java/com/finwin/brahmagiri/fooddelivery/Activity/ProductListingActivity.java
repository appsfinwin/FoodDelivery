package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.BestSellingAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ItemlistingBrahmaAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableSummaryCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProducts;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListingActivity extends Fragment implements showhide, NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    BestSellingAdapter adapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    GridLayoutManager gridLayoutManager;
    ProgressBar pbardialog;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    RelativeLayout msummarylayout;
    TextView mtotalcount, mrupee;
    String searchkey, Flag;
    DatabaseHandler db;
    View rootview;
    ProductEntryModel productEntryModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.activity_item_listing, container, false);


        productEntryModel = new ProductEntryModel();
        db = new DatabaseHandler(getActivity());
        drawer = (DrawerLayout) rootview.findViewById(R.id.drawer_layou);
        navigationView = (NavigationView) rootview.findViewById(R.id.navigation_view);

        setToolbar();
       // Log.e("onCreate", "onCreate: " + catid);
        recyclerView = rootview.findViewById(R.id.item_listing);
        mtotalcount =  rootview.findViewById(R.id.tv_itemcount);
        mrupee =  rootview.findViewById(R.id.totalamt);


        pbardialog = (ProgressBar) rootview. findViewById(R.id.main_progress);
        msummarylayout = (RelativeLayout)  rootview.findViewById(R.id.summary_layout);
        //   adapter = new BestSellingAdapter(this, this);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);

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
        doFetchProducts();
   /*     gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case BestSellingAdapter.ITEM:
                        return 1;
                    case BestSellingAdapter.LOADING:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });*/
        //dofetchcartSummary(0, "", "CART_SUMMARY");

        recyclerView.setLayoutManager(gridLayoutManager);
        // recyclerView.setAdapter(adapter);


        // recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


    /*    recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //doFetchListingNextPage(finalCatid,Flag);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }


        });*/
        // doFetchListingFirstPage(catid,Flag);
        return rootview;
    }

    /*private void doFetchListingNextPage(String cat_id,String Flag) {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchitem> call = apiService.doFetchItembyCategory("0", "", Flag, cat_id, currentPage, "44402",searchkey);
        call.enqueue(new Callback<ResponseFetchitem>() {
            @Override
            public void onResponse(Call<ResponseFetchitem> call, Response<ResponseFetchitem> response) {
                Log.d("nextpagetotal", "doFetchListingNextPage: " + TOTAL_PAGES);

                if (response.body() != null && response.code() == 200) {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    List<Table> dataset = response.body().getData().getTable();


                    adapter.addAll(dataset);

                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;


                }

            }

            @Override
            public void onFailure(Call<ResponseFetchitem> call, Throwable t) {
                Log.d("nextpagetotal", "doFetchListingNextPage: " + t.getMessage());


            }
        });

    }

    private void doFetchListingFirstPage(String catid,String Flag) {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchitem> call = apiService.doFetchItembyCategory("0", "", Flag, catid, currentPage, "44402",searchkey);
        call.enqueue(new Callback<ResponseFetchitem>() {
            @Override
            public void onResponse(Call<ResponseFetchitem> call, Response<ResponseFetchitem> response) {
                if (response.body() != null && response.code() == 200) {
                    List<Table1> pagelist = response.body().getData().getTable1();
                    TOTAL_PAGES = pagelist.get(0).getNoOfPages();
                    Log.d("totalpage", "onResponse: " + TOTAL_PAGES);
                    List<Table> dataset = response.body().getData().getTable();
                    progressBar.setVisibility(View.GONE);


                    adapter.addAll(dataset);
                    if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;


                }

            }

            @Override
            public void onFailure(Call<ResponseFetchitem> call, Throwable t) {

            }
        });

    }*/

    @Override
    public void clickedpdct(int value, String code, String pname, String price) {
        msummarylayout.setVisibility(View.VISIBLE);

        if (value > 0) {

            if (!db.rowIdExists(Integer.parseInt(code))) {
                productEntryModel.setId(Integer.parseInt(code));
                productEntryModel.setProductname(pname);
                productEntryModel.setPrice(Double.valueOf(price));
                productEntryModel.setQuantity(value);
                db.addContact(productEntryModel);
            } else {
                Log.d("clicked", "update: " + code);
                db.updateContact(value, Integer.parseInt(code));

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

    @Override
    public void clicked(int value, String code) {

    }


    @Override
    public void show(String show) {
        if (show.equals("show")) {
            // msummarylayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void delete(String code) {

    }

    private void dofetchcartSummary(int value, String code, String flag) {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchCart> call = apiService.doCartSummary("", "", flag, 0, "string", code, String.valueOf(value), "44402");
        call.enqueue(new Callback<ResponseFetchCart>() {
            @Override
            public void onResponse(Call<ResponseFetchCart> call, Response<ResponseFetchCart> response) {
                if (response.body() != null && response.code() == 200) {
                    Log.d("cartsummary", "onFailure: " + new Gson().toJson(response.body()));

                    ResponseFetchCart responseAddtoCart = response.body();
                    List<TableSummaryCart> dataset = responseAddtoCart.getData().getTable1();
                    //Double total = dataset.get(0).getTotal();
                    // int count=dataset.get(0).getQuantity();
                    //   Log.d("cartsummary", "onFailure: "+count);

                    if (dataset.get(0).getQuantity() != null) {
                        if (dataset.get(0).getQuantity() > 0) {
                            msummarylayout.setVisibility(View.VISIBLE);

                        }


                    } else {
                        msummarylayout.setVisibility(View.GONE);

                    }


                    mtotalcount.setText("" + dataset.get(0).getQuantity() + " Items");
                    mrupee.setText("₹ " + dataset.get(0).getTotal());


                }
            }

            @Override
            public void onFailure(Call<ResponseFetchCart> call, Throwable t) {


            }
        });
    }


    public void gotocart(View view) {
        startActivity(new Intent(getActivity(), CartActivity.class));

    }

   /* @Override
    protected void onResume() {
        super.onResume();
        // dofetchcartSummary(0, "", "CART_SUMMARY");
        doFetchProducts();

    }*/

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

                    ItemlistingBrahmaAdapter adapter = new ItemlistingBrahmaAdapter(getActivity(), dataSet, ProductListingActivity.this);
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProducts> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
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
}

