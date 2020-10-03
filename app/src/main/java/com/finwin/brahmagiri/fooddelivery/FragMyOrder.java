package com.finwin.brahmagiri.fooddelivery;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.MyOrderAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.MyOrdersAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.PreviousSale;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseMyOrder;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.PaginationScrollListener;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class FragMyOrder extends Fragment {


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.my_orders_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = rootview.findViewById(R.id.emptyorder);
        OrderRecycler = (RecyclerView) rootview.findViewById(R.id.menuRecycler);
        progressBar = rootview.findViewById(R.id.progressbars);
        bAdapter = new MyOrdersAdapter(getActivity());
        partnerid = LocalPreferences.retrieveStringPreferences(getActivity(), "partnerid");
        mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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


        ibtn_back = rootview.findViewById(R.id.ibtn_back_mordr);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
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
                    Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseMyOrder> call, Throwable t) {

            }
        });


    }

    private void loadFirstPage() {
        progressBar.setVisibility(View.VISIBLE);
        String partnerid = LocalPreferences.retrieveStringPreferences(getActivity(), "partnerid");
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");

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
                    Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseMyOrder> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }


}
