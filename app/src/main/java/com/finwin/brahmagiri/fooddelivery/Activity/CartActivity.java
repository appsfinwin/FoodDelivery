package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.CartAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.ResponseAddtoCart;
import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.TableCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableSummaryCart;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityCartBinding;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements showhide {
    ActivityCartBinding binding;
    CartAdapter mCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_cart);
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dofetchcartSummary(0,"","CART_SUMMARY");

    }

    public void GotoPayment(View view) {
        startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
    }
    private void dofetchcartSummary(int value, String code, String flag) {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchCart> call = apiService.doCartSummary("", "", flag, 0, "string", code, String.valueOf(value), "44402");
        call.enqueue(new Callback<ResponseFetchCart>() {
            @Override
            public void onResponse(Call<ResponseFetchCart> call, Response<ResponseFetchCart> response) {
                if (response.body() != null && response.code() == 200) {

                    ResponseFetchCart responseAddtoCart = response.body();
                    List<TableSummaryCart> dataset = responseAddtoCart.getData().getTable1();
                    List<TableFetchCart>datasetcartlist=responseAddtoCart.getData().getTable();
                    mCartAdapter=new CartAdapter(getApplication(), datasetcartlist, CartActivity.this,true);

                        binding.cartRecycler.setAdapter(mCartAdapter);
                    Log.d("cartsummary", "onFailure: "+mCartAdapter.getItemCount());
                    if (mCartAdapter.getItemCount()==0){
                        binding.emptyCart.setVisibility(View.VISIBLE);
                        binding.parent.setVisibility(View.GONE);
                        binding.lnrlayConfmpay.setVisibility(View.GONE);


                    }else{
                        binding.emptyCart.setVisibility(View.GONE);
                        binding.parent.setVisibility(View.VISIBLE);
                        binding.lnrlayConfmpay.setVisibility(View.VISIBLE);
                    }

                    binding.tvCartChrg.setText("" + dataset.get(0).getDeliveryCharge());
                        binding.tvCartGst.setText("" + dataset.get(0).getTax());
                        binding.tvCartSubtotal.setText("" + dataset.get(0).getTotal());
                        binding.tvCartTotal.setText("₹ " + dataset.get(0).getGrandTotal());
                       /* binding.parent.setVisibility(View.GONE);
                        binding.lnrlayConfmpay.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();*/






                    //Double total = dataset.get(0).getTotal();
                    // int count=dataset.get(0).getQuantity();
                    //   Log.d("cartsummary", "onFailure: "+count);




                }
            }

            @Override
            public void onFailure(Call<ResponseFetchCart> call, Throwable t) {


            }
        });
    }
    private void doUpdateCart(int value, String code, String flag) {
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseAddtoCart> call = apiService.doCartManagment("", "", flag, 0, "", code, String.valueOf(value), "44402","");
        call.enqueue(new Callback<ResponseAddtoCart>() {
            @Override
            public void onResponse(Call<ResponseAddtoCart> call, Response<ResponseAddtoCart> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseAddtoCart responseAddtoCart = response.body();
                    List<TableCart> dataset = responseAddtoCart.getData().getTable1();
                    String status = dataset.get(0).getReturnStatus();
                    Toast.makeText(CartActivity.this, "" + dataset.get(0).getReturnMessage(), Toast.LENGTH_SHORT).show();
                    dofetchcartSummary(0,"","CART_SUMMARY");
                }
            }

            @Override
            public void onFailure(Call<ResponseAddtoCart> call, Throwable t) {
                Log.d("cartsummary", "onFailure: "+t.getMessage());

            }
        });
    }

    @Override
    public void clicked(int value, String code) {
        doUpdateCart(value, code, "INSERT");


    }

    @Override
    public void show(String show) {

    }

    @Override
    public void delete(String code) {
        doUpdateCart(0, code, "DELETE");


    }
}
