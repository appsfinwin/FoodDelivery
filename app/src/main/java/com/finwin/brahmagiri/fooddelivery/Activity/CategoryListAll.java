package com.finwin.brahmagiri.fooddelivery.Activity;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.AllCategoryAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.BestSellingAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category.ItemCat;
import com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category.ResponseFetchCategory;
import com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category.Tablecattotal;
import com.finwin.brahmagiri.fooddelivery.Utilities.PaginationScrollListener;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListAll extends AppCompatActivity {
    RecyclerView recyclerView;
    AllCategoryAdapter adapter;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES ;
    private int currentPage = PAGE_START;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_all);
        recyclerView = findViewById(R.id.item_catlisting);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        TextView tv_toolbar=findViewById(R.id.toolbartext);
        tv_toolbar.setText("Categories");
        adapter = new AllCategoryAdapter(this);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)){
                    case BestSellingAdapter.ITEM:
                        return 1;
                    case BestSellingAdapter.LOADING:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doFetchListingNextPage();                    }
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
        doFetchListingFirstPage();
    }

    private void doFetchListingNextPage( ) {
        ApiService apiService= APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchCategory> call=apiService.doFetchCategory("0","","SELECT_CATEGORY",currentPage);
        call.enqueue(new Callback<ResponseFetchCategory>() {
            @Override
            public void onResponse(Call<ResponseFetchCategory> call, Response<ResponseFetchCategory> response) {
                Log.d("nextpagetotal", "doFetchListingNextPage: "+TOTAL_PAGES);

                if (response.body()!=null&&response.code()==200){
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    List<ItemCat> dataset=response.body().getData().getTable();


                    adapter.addAll(dataset);

                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;




                }

            }

            @Override
            public void onFailure(Call<ResponseFetchCategory> call, Throwable t) {
                Log.d("nextpagetotal", "doFetchListingNextPage: "+t.getMessage());


            }
        });

    }
    private void doFetchListingFirstPage() {
        ApiService apiService= APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchCategory> call=apiService.doFetchCategory("0","","SELECT_CATEGORY",currentPage);
        call.enqueue(new Callback<ResponseFetchCategory>() {
            @Override
            public void onResponse(Call<ResponseFetchCategory> call, Response<ResponseFetchCategory> response) {
                if (response.body()!=null&&response.code()==200){
                    List<Tablecattotal>pagelist=response.body().getData().getTable1();
                    TOTAL_PAGES=pagelist.get(0).getNoOfPages();
                    Log.d("totalpage", "onResponse: "+TOTAL_PAGES);
                    List<ItemCat>dataset=response.body().getData().getTable();
                    progressBar.setVisibility(View.GONE);


                    adapter.addAll(dataset);
                    if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;



                }

            }

            @Override
            public void onFailure(Call<ResponseFetchCategory> call, Throwable t) {

            }
        });

    }

}
