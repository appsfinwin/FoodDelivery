package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.MyOrdersAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.PreviousSale;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseMyOrder;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.utilities.PaginationScrollListener;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class MyOrderActivity extends AppCompatActivity {


    private RecyclerView OrderRecycler;
    private MyOrdersAdapter bAdapter;
    LinearLayoutManager linearLayoutManager;
    FrameLayout frameLayout;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    ImageButton ibtn_back;
    View rootview;
    TextView tvOK;
    ProgressBar progressBar;
    String partnerid;
    String mAccesstoken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders_fragment);
        frameLayout =findViewById(R.id.emptyorder);
        OrderRecycler = (RecyclerView)findViewById(R.id.menuRecycler);
        progressBar = findViewById(R.id.progressbars);
        bAdapter = new MyOrdersAdapter(getApplicationContext());
        partnerid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");
        mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        OrderRecycler.setLayoutManager(linearLayoutManager);

        OrderRecycler.setItemAnimator(new DefaultItemAnimator());

        OrderRecycler.setAdapter(bAdapter);


        OrderRecycler.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
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

        loadFirstPage();


        ibtn_back = findViewById(R.id.ibtn_back_mordr);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
        });

    }

    private void loadNextPage() {


        ApiService apiService = APIClient.getClient().create(ApiService.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("consumer_id", Integer.parseInt(partnerid));
        jsonObject.addProperty("page", currentPage);

        Call<ResponseMyOrder> call = apiService.doFetchMyOrder(mAccesstoken, database, jsonObject);
        call.enqueue(new Callback<ResponseMyOrder>() {
            @Override
            public void onResponse(Call<ResponseMyOrder> call, Response<ResponseMyOrder> response) {

                if (response.body() != null && response.code() == 200) {
                    ResponseMyOrder responseMyOrder = response.body();
                    bAdapter.removeLoadingFooter();
                    isLoading = false;
                    List<PreviousSale> dataset = responseMyOrder.getPreviousSales();
                    bAdapter.addAll(dataset);
                    if (currentPage != TOTAL_PAGES) bAdapter.addLoadingFooter();
                    else isLastPage = true;

                    if (bAdapter.getItemCount() == 0) {
                        frameLayout.setVisibility(View.VISIBLE);
                        OrderRecycler.setVisibility(View.GONE);
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseMyOrder> call, Throwable t) {

            }
        });


    }

    private void loadFirstPage() {
        progressBar.setVisibility(View.VISIBLE);
        String partnerid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("consumer_id", Integer.parseInt(partnerid));
        jsonObject.addProperty("page", PAGE_START);

        Call<ResponseMyOrder> call = apiService.doFetchMyOrder(mAccesstoken, database, jsonObject);
        call.enqueue(new Callback<ResponseMyOrder>() {
            @Override
            public void onResponse(Call<ResponseMyOrder> call, Response<ResponseMyOrder> response) {
                progressBar.setVisibility(View.GONE);

                if (response.body() != null && response.code() == 200) {
                    ResponseMyOrder responseMyOrder = response.body();
                    TOTAL_PAGES = response.body().getTotalPage();
                    List<PreviousSale> dataset = responseMyOrder.getPreviousSales();
                    bAdapter.addAll(dataset);
                    if (currentPage < TOTAL_PAGES) bAdapter.addLoadingFooter();
                    else isLastPage = true;

                    if (bAdapter.getItemCount() == 0) {
                        frameLayout.setVisibility(View.VISIBLE);
                        OrderRecycler.setVisibility(View.GONE);
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseMyOrder> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}