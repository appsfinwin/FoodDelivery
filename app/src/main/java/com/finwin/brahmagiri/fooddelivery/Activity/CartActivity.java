package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.CartAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.ResponseAddtoCart;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.TableCart;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableSummaryCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseRemove;
import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityCartBinding;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class CartActivity extends AppCompatActivity implements showhide {
    ActivityCartBinding binding;
    CartAdapter mCartAdapter;
    DatabaseHandler db;
    List<ProductEntryModel> datasetcartlist;
    List<CartItem> totallist;
    double totalsum = 0.0;
    String cartoutid;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.toolbarLayout.toolbartext.setText("Cart");
        totallist = new ArrayList<>();
        progressDialog = new ProgressDialog(CartActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        datasetcartlist = new ArrayList<>();
        db = new DatabaseHandler(getApplicationContext());
        //db.getquantity("11");
        Log.d("fetchquantity", ": " + db.getFromDb("11"));
        //  fetchCart();
        cartoutid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "cartoutid");
        if (cartoutid != null && !cartoutid.equals("")) {
            fetchCartfromServer(cartoutid);
        } else {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.parent.setVisibility(View.GONE);
            binding.lnrlayConfmpay.setVisibility(View.GONE);

        }


        //calculatetotal();


    }

    private void fetchCartfromServer(String cartoutid) {
        progressDialog.show();
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        String userid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "userid");
        Log.d("fetchCartfromServer", "fetchCartfromServer: " + cartoutid);

        String json = "{\"user_id\":" + Integer.parseInt(userid) + ",\"outlet\":" + Integer.parseInt(cartoutid) + "}";
        JsonParser parser = new JsonParser();
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
                progressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    ResponseBrahmaCart responseBrahmaCart = response.body();

                    List<CartItem> cartItemList = responseBrahmaCart.getCartItems();
                    totallist = response.body().getCartItems();
                    calculatetotal(totallist);
                    mCartAdapter = new CartAdapter(getApplication(), cartItemList, CartActivity.this, false);
                    binding.cartRecycler.setAdapter(mCartAdapter);
                    if (mCartAdapter.getItemCount() == 0) {
                        binding.emptyCart.setVisibility(View.VISIBLE);
                        binding.parent.setVisibility(View.GONE);
                        binding.lnrlayConfmpay.setVisibility(View.GONE);


                    } else {
                        binding.emptyCart.setVisibility(View.GONE);
                        binding.parent.setVisibility(View.VISIBLE);
                        binding.lnrlayConfmpay.setVisibility(View.VISIBLE);
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBrahmaCart> call, Throwable t) {
                progressDialog.dismiss();
                if (new AppUtility(CartActivity.this).checkInternet()) {

                } else {
                    Toast.makeText(CartActivity.this, "NO INTERNET", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void GotoPayment(View view) {
        startActivity(new Intent(getApplicationContext(), PaymentActivity.class).putExtra("total", Double.toString(totalsum)));
    }




    @Override
    public void clicked(int value, String code) {

        //doUpdateCart(value, code, "INSERT");
        //db.updateContact(value, Integer.parseInt(code));
        //calculatetotal();


    }

    @Override
    public void clickedpdct(int value, String code, String pname, String price) {


    }

    @Override
    public void show(String show) {

    }

    @Override
    public void delete(String code) {
        //   doUpdateCart(0, code, "DELETE");
        //db.deleteEntry(Integer.parseInt(code));
        deletefromServer(cartoutid, Integer.parseInt(code));
        mCartAdapter.notifyDataSetChanged();
        //calculatetotal();
        Log.e("delete", "delete: " + mCartAdapter.getItemCount());
       /* if (datasetcartlist.size() == 0) {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.parent.setVisibility(View.GONE);
            binding.lnrlayConfmpay.setVisibility(View.GONE);


        } else {
            binding.emptyCart.setVisibility(View.GONE);
            binding.parent.setVisibility(View.VISIBLE);
            binding.lnrlayConfmpay.setVisibility(View.VISIBLE);
        }*/


    }

    public void calculatetotal(List<CartItem> cartItemList) {
        double sum = 0.0;
        //  totallist.clear();

        for (int i = 0; i < cartItemList.size(); i++) {
            double price = cartItemList.get(i).getPrice() * totallist.get(i).getQuantity();
            sum = sum + price;
            Log.d("calculatetotal", "calculatetotal: " + sum);
            binding.tvCartSubtotal.setText("" + sum);
            binding.tvCartTotal.setText("₹ " + sum);
        }
        totalsum = sum;


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

                    Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    fetchCartfromServer(cartoutid);


                }
            }

            @Override
            public void onFailure(Call<ResponseRemove> call, Throwable t) {
                if (new AppUtility(CartActivity.this).checkInternet()) {

                } else {
                    Toast.makeText(CartActivity.this, "NO INTERNET", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
