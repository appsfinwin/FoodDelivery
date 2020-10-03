package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.finwin.brahmagiri.fooddelivery.Adapter.OrderdetailAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.LineItem;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCancelOrder;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseOrderDetails;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityOrderDetailsBinding;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class OrderDetails extends AppCompatActivity {
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
        binding.progress.setVisibility(View.VISIBLE);
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


        binding.tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCancelOrder();
            }
        });

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
                binding.progress.setVisibility(View.GONE);
                if (response.body() != null && response.code() == 200) {
                    binding.parentlayt.setVisibility(View.VISIBLE);
                    ResponseOrderDetails responseOrderDetails = response.body();
                    List<LineItem> dataset = responseOrderDetails.getLineItems();
                    binding.tvSubtotal.setText("₹ " + response.body().getSubtotal());
                    binding.totalAmt.setText("₹ " + response.body().getTotalAmount());
                    binding.tvTaxamt.setText("₹ " + response.body().getTaxAmount());
                    binding.invoiceId.setText("Invoice Id : " + response.body().getInvoiceNumber());
                    binding.tvoutname.setText("Outlet Name :" + response.body().getOutletName());
                    binding.tvoutmobile.setText("Outlet Mobile No. :" + response.body().getOutletMobile());
                    binding.tvCustname.setText("Customer Name : " + response.body().getConsumer_name());
                    binding.date.setText("Date and Time : " + response.body().getDate_time());

                    if (response.body().getDelivery_charges().equals("")) {
                        binding.tvDeliverychargess.setText("₹ 0");
                    } else {
                        binding.tvDeliverychargess.setText("₹ " + response.body().getDelivery_charges());
                    }
                    String paymode = response.body().getPayment_mode();
                    binding.paymentmode.setText("Payment Mode: " + paymode);
                    String collectionmode = response.body().getCollecting_option();
                    binding.collection.setText("Collection Option : " + collectionmode);
                    binding.recyvmyorderdetails.setAdapter(new OrderdetailAdapter(getApplicationContext(), dataset));
                    if (!response.body().getStatus().equalsIgnoreCase("cancel") && !response.body().getRemaining_time().equalsIgnoreCase("0")) {
                        binding.tvTimer.setVisibility(View.VISIBLE);
                    }
                    long remainingtime = Long.parseLong(response.body().getRemaining_time());
                    CountDownTimer countDownTimer = new CountDownTimer(remainingtime, 1000) {
                        public void onTick(long millisUntilFinished) {
                            long millis = millisUntilFinished;
                            //Convert milliseconds into hour,minute and seconds
                            String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                            binding.tvTimer.setText("Cancel  Order in \n " + hms + " mins");//set text
                        }

                        public void onFinish() {
                            binding.tvTimer.setVisibility(View.GONE); //On finish change timer text
                        }
                    }.start();


                }

            }

            @Override
            public void onFailure(Call<ResponseOrderDetails> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);

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


    public void doCancelOrder() {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        JsonObject assign = new JsonObject();
        final String mbillid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "billid");

        progressDialog.show();
        assign.addProperty("bill_id", Integer.parseInt(mbillid));
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseCancelOrder> call = apiService.docancelOrder(mAccesstoken, database, assign);
        call.enqueue(new Callback<ResponseCancelOrder>() {
            @Override
            public void onResponse(Call<ResponseCancelOrder> call, Response<ResponseCancelOrder> response) {
                progressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    binding.tvTimer.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "ORDER CANCELLED", Toast.LENGTH_SHORT).show();

                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("stock_return_main")) {
                       /* startActivity(new Intent(getApplicationContext(), AllOrderActivity.class));
                        finish()*/
                        ;

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseCancelOrder> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }


}

