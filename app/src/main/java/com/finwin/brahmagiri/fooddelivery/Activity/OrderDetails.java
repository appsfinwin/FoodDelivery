package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.finwin.brahmagiri.fooddelivery.Adapter.OrderdetailAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.LineItem;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseOrderDetails;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityOrderDetailsBinding;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class OrderDetails extends AppCompatActivity  {
    ActivityOrderDetailsBinding binding;

    String selectedid, billid, status;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_order_details);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        binding.recyvmyorderdetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String orderid = getIntent().getStringExtra("id");

        status = getIntent().getStringExtra("status");
        Log.e("TAG", "onCreate: " + status);
        String custname = getIntent().getStringExtra("custname");
   //     binding.tvCustname.setText("Customer Name  :" + custname);


        if (status != null && status.equalsIgnoreCase("assigned")) {


        }
        if (status != null && status.equalsIgnoreCase("shipped")) {


        }
        if (status != null && status.equalsIgnoreCase("cash_received_by_boy")) {


        }


        progressDialog = new ProgressDialog(OrderDetails.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        if (orderid != null) {
            Log.e("NiyasNazar", "onCreate: " + orderid);
            doOrderdetails(orderid);


        }


    }
    private void doOrderdetails(String orderid) {
        JsonObject student1 = new JsonObject();

        student1.addProperty("invoice_id", Integer.parseInt(orderid));
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseOrderDetails> call = apiService.dofetchorderdetails(mAccesstoken, database, student1);
        call.enqueue(new Callback<ResponseOrderDetails>() {
            @Override
            public void onResponse(Call<ResponseOrderDetails> call, Response<ResponseOrderDetails> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseOrderDetails responseOrderDetails = response.body();
                    List<LineItem> dataset = responseOrderDetails.getLineItems();
                    binding.tvSubtotal.setText("₹ " + response.body().getSubtotal());
                    binding.totalAmt.setText("₹ " + response.body().getTotalAmount());
                    binding.tvTaxamt.setText("₹ " + response.body().getTaxAmount());
                    binding.invoiceId.setText("Invoice Id : " + response.body().getInvoiceNumber());

                    if (response.body().getDelivery_charges().equals("")){
                        binding.tvDeliverychargess.setText("₹ 0");
                    }else{
                        binding.tvDeliverychargess.setText("₹ "+response.body().getDelivery_charges());
                    }
                    String paymode = response.body().getPayment_mode();
                    String collectionmode = response.body().getCollecting_option();
                    binding.recyvmyorderdetails.setAdapter(new OrderdetailAdapter(getApplicationContext(), dataset));




                }

            }

            @Override
            public void onFailure(Call<ResponseOrderDetails> call, Throwable t) {

            }
        });
    }







    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void onBack(View view) {
        onBackPressed();
    }
}

