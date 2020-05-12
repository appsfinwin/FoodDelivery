package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.BestSellingAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.ResponseAddtoCart;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.TableCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableSummaryCart;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.ResponseFetchitem;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.Table;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.Table1;
import com.finwin.brahmagiri.fooddelivery.Utilities.PaginationScrollListener;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListingActivity extends AppCompatActivity implements showhide {
    RecyclerView recyclerView;
    BestSellingAdapter adapter;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    RelativeLayout msummarylayout;
    TextView mtotalcount, mrupee;
    String searchkey,Flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_listing);
        Intent is = getIntent();
      String catid = is.getStringExtra("cat_id");
        if (catid==null){
            catid="";
        }
        Log.e("onCreate", "onCreate: " + catid);
        recyclerView = findViewById(R.id.item_listing);
        mtotalcount = findViewById(R.id.tv_itemcount);
        mrupee = findViewById(R.id.totalamt);
        searchkey=getIntent().getStringExtra("key");
        if (searchkey!=null){
            Flag="ITEM_SEARCH";

        }else{
            Flag="SELECT_ITEM_BYCATEGORY";
        }

        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        msummarylayout = (RelativeLayout) findViewById(R.id.summary_layout);
        adapter = new BestSellingAdapter(this, this);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
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
        });
        dofetchcartSummary(0, "", "CART_SUMMARY");

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        // recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        final String finalCatid = catid;
        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doFetchListingNextPage(finalCatid,Flag);
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


        });
        doFetchListingFirstPage(catid,Flag);
    }

    private void doFetchListingNextPage(String cat_id,String Flag) {
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

    }

    @Override
    public void clicked(int value, String code) {
        if (value > 0) {
            msummarylayout.setVisibility(View.VISIBLE);
            Log.d("clicked", "clicked: " + value);
            doUpdateCart(value, code, "INSERT");

        } else {
            msummarylayout.setVisibility(View.GONE);
            doUpdateCart(0, code, "DELETE");
        }

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


                    }else{
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

    private void doUpdateCart(int value, String code, String flag) {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseAddtoCart> call = apiService.doCartManagment("", "", flag, 0, "", code, String.valueOf(value), "44402", "");
        call.enqueue(new Callback<ResponseAddtoCart>() {
            @Override
            public void onResponse(Call<ResponseAddtoCart> call, Response<ResponseAddtoCart> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseAddtoCart responseAddtoCart = response.body();
                    List<TableCart> dataset = responseAddtoCart.getData().getTable1();
                    String status = dataset.get(0).getReturnStatus();
                    Toast.makeText(ItemListingActivity.this, "" + dataset.get(0).getReturnMessage(), Toast.LENGTH_SHORT).show();
                    dofetchcartSummary(0, "", "CART_SUMMARY");
                }
            }

            @Override
            public void onFailure(Call<ResponseAddtoCart> call, Throwable t) {
                Log.d("cartsummary", "onFailure: " + t.getMessage());

            }
        });
    }

    public void gotocart(View view) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        dofetchcartSummary(0, "", "CART_SUMMARY");

    }
}
