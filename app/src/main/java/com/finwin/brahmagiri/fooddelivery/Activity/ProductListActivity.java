package com.finwin.brahmagiri.fooddelivery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.ItemlistingBrahmaAdapter;
import com.finwin.brahmagiri.fooddelivery.FragHome;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseAddcart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProducts;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseRemove;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityProductListBinding;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class ProductListActivity extends AppCompatActivity implements showhide {
    RecyclerView recyclerView;
    ActivityProductListBinding binding;
    ItemlistingBrahmaAdapter adapter;
    List<CartItem> totallist;
    String outletid;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_product_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list);
        totallist = new ArrayList<>();
        String outletid = getIntent().getStringExtra("outletid");
        String outletmobile = getIntent().getStringExtra("outletmobile");
        if (outletmobile != null && !outletmobile.equals("")) {
            binding.tvcontact.setText("Do You Want to Talk to us . Call us on " + outletmobile);

        } else {
            binding.helplayt.setVisibility(View.GONE);

            binding.horizntalline.setVisibility(View.GONE);
        }
        binding.tvcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             doCAlloutlet(outletmobile);
            }
        });
        binding.recyvprdcts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        progressDialog = new ProgressDialog(ProductListActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait...");
        doFetchProducts(outletid);

        binding.tvViewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
    }

    private void doCAlloutlet(String outletmobile) {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+outletmobile));

                        if (ActivityCompat.checkSelfPermission(ProductListActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void doFetchProducts(String outlet_id) {
        binding.progressbars.setVisibility(View.VISIBLE);

        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProducts> call = apiService.fetchproducts(outlet_id, mAccesstoken, database);
        call.enqueue(new Callback<ResponseFetchProducts>() {
            @Override
            public void onResponse(Call<ResponseFetchProducts> call, Response<ResponseFetchProducts> response) {
                binding.progressbars.setVisibility(View.GONE);
                if (response != null && response.code() == 200) {
                    ResponseFetchProducts responseFetchProducts = response.body();

                    // dataSet=response.body().getProducts();
                    List<Product> dataSet = responseFetchProducts.getProducts();

                    adapter = new ItemlistingBrahmaAdapter(getApplicationContext(), dataSet, ProductListActivity.this);
                    binding.recyvprdcts.setAdapter(adapter);
                    binding.recyvprdcts.setVisibility(View.VISIBLE);

                    if (adapter.getItemCount() == 0) {
                        // Toast.makeText(getActivity(), "No Products found for the selected outlet", Toast.LENGTH_SHORT).show();
                        binding.emptyLayout.setVisibility(View.VISIBLE);
                        binding.recyvprdcts.setVisibility(View.INVISIBLE);
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProducts> call, Throwable t) {
                binding.progressbars.setVisibility(View.GONE);

            }
        });

    }

    private void doAddmyCart(int value, String code, String pname, String outid) {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        String userid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "userid");
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


    private void fetchCartfromServer(String cartoutid) {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        String userid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "userid");
        Log.d("fetchCartfromServer", "fetchCartfromServer: " + cartoutid);

        String latitude = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "latitude");
        String longitude = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "longitude");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", Integer.parseInt(userid));
        jsonObject.addProperty("outlet", Integer.parseInt(cartoutid));
        jsonObject.addProperty("longitude", longitude);
        jsonObject.addProperty("latitude", latitude);

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseBrahmaCart> cartCall = apiService.FetchCart(mAccesstoken, database, jsonObject);
        cartCall.enqueue(new Callback<ResponseBrahmaCart>() {
            @Override
            public void onResponse(Call<ResponseBrahmaCart> call, Response<ResponseBrahmaCart> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (response.body() != null && response.code() == 200) {
                    ResponseBrahmaCart responseBrahmaCart = response.body();

                    List<CartItem> cartItemList = responseBrahmaCart.getCartItems();
                    if (cartItemList.size() == 0) {
                        binding.summaryLayout.setVisibility(View.GONE);
                    } else {
                        binding.summaryLayout.setVisibility(View.VISIBLE);

                    }
                    Log.d("calculatetotal", "calculatetotal: " + cartItemList.size());

                    totallist = response.body().getCartItems();
                    calculatetotal();


                }
            }

            @Override
            public void onFailure(Call<ResponseBrahmaCart> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });

    }

    public void calculatetotal() {
        double sum = 0.0;
        if (totallist.size() == 0) {
            binding.summaryLayout.setVisibility(View.GONE);
        }

        for (int i = 0; i < totallist.size(); i++) {
            double price = totallist.get(i).getPrice() * totallist.get(i).getQuantity();
            sum = sum + price;
            binding.totalamt.setText("" + sum);
            binding.tvItemcount.setText(" " + totallist.size());
        }
        //totalsum=sum;


    }


    @Override
    public void clicked(int value, String code) {

    }

    @Override
    public void clickedpdct(int value, String code, String pname, String outid) {
        Log.d("TAG", "clickedpdct: " + value + code + pname + outid);

        // binding.summaryLayout.setVisibility(View.VISIBLE);
        if (value == 0) {
            deletefromServer(outid, Integer.parseInt(code));
        }

        if (value > 0) {
            doAddmyCart(value, code, pname, outid);
            outletid = outid;


            //  db.rowIdExists(Integer.parseInt(code));


            //  doUpdateCart(value, code, "INSERT");

        } else {

            // doUpdateCart(0, code, "DELETE");
        }


    }

    @Override
    public void show(String show) {

    }

    @Override
    public void delete(String code) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (outletid != null) {
            fetchCartfromServer(outletid);
        }


    }

    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void deletefromServer(final String cartoutid, int productcode) {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        String userid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "userid");
        Log.d("fetchCartfromServer", "fetchCartfromServer: " + cartoutid);

        String json = "{\"user_id\":" + Integer.parseInt(userid) + ",\"product_id\":" + productcode + ",\"outlet\":" + Integer.parseInt(cartoutid) + "}";
        JsonParser parser = new JsonParser();

        JsonObject jsonObject = (JsonObject) parser.parse(json);

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseRemove> cartCall = apiService.doremove(mAccesstoken, database, jsonObject);
        cartCall.enqueue(new Callback<ResponseRemove>() {
            @Override
            public void onResponse(Call<ResponseRemove> call, Response<ResponseRemove> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseRemove responseBrahmaCart = response.body();

                    fetchCartfromServer(cartoutid);


                }
            }

            @Override
            public void onFailure(Call<ResponseRemove> call, Throwable t) {

            }
        });

    }


}