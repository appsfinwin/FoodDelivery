package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.BestSellingAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ItemlistingBrahmaAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.ResponseAddtoCart;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.TableCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableSummaryCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProducts;
import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
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
    DatabaseHandler db;
    ProductEntryModel productEntryModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_listing);
        productEntryModel=new ProductEntryModel();
        db=new DatabaseHandler(getApplicationContext());
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
     //   adapter = new BestSellingAdapter(this, this);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
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

        final String finalCatid = catid;
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
    public void clickedpdct(int value, String code, String pname, String price){
        msummarylayout.setVisibility(View.VISIBLE);

        if (value > 0) {

            if (!db.rowIdExists(Integer.parseInt(code))){
                productEntryModel.setId(Integer.parseInt(code));
                  productEntryModel.setProductname(pname);
                productEntryModel.setPrice(Double.valueOf(price));
                 productEntryModel.setQuantity(value);
                db.addContact(productEntryModel);
            }else{
                Log.d("clicked", "update: " + code);
                db.updateContact(value,Integer.parseInt(code));

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



    public void gotocart(View view) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
       // dofetchcartSummary(0, "", "CART_SUMMARY");
        doFetchProducts();

    }
    private  void doFetchProducts() {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProducts> call = apiService.fetchproducts("319", mAccesstoken, "test");
        call.enqueue(new Callback<ResponseFetchProducts>() {
            @Override
            public void onResponse(Call<ResponseFetchProducts> call, Response<ResponseFetchProducts> response) {
                if (response != null && response.code() == 200) {
                    ResponseFetchProducts responseFetchProducts = response.body();

                    // dataSet=response.body().getProducts();
               List<Product>     dataSet = responseFetchProducts.getProducts();

                ItemlistingBrahmaAdapter adapter = new ItemlistingBrahmaAdapter(ItemListingActivity.this,  dataSet,ItemListingActivity.this);
                  recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProducts> call, Throwable t) {
                if (new AppUtility(ItemListingActivity.this).checkInternet()) {

                } else {
                    Toast.makeText(ItemListingActivity.this, "NO INTERNET", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
