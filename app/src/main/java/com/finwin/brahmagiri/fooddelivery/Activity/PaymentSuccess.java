package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.Adapter.FinalbillAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.Responses.Products;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCancelOrder;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseInvoiceGen;
import com.finwin.brahmagiri.fooddelivery.Responses.Tax;
import com.finwin.brahmagiri.fooddelivery.utilities.AppUtility;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class PaymentSuccess extends AppCompatActivity {
    DatabaseHandler db;
    List<CartItem> datasetAdd;
    RecyclerView recyclerView;
    Button btninvoice;
    String cartoutid;
    TextView TextViewtotal, TextViewsubtotal, TextViewDcharge, TextViewinvoiceid, Textviewcgstname, Textviewcgstvalue, Textviewsgstname, TextviewSgstvalue;
    ProgressDialog mProgressDialog;
    LinearLayout mainparent;
    String transactionid;
    Double finalsum=0.0;
    String paymentmode;
    ProgressDialog progressDialog;
    final String[][] DATA_TO_SHOW = {{"This", "is", "a", "test"},
            {"and", "a", "second", "test"}};
    TextView tvadderss, tvtotalamount2, tvinvoicedate, tnxidno;
    NestedScrollView bills;
    TextView outletname, outletphoneno, outletaddress;
    Button Tvtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success2);
        recyclerView = findViewById(R.id.recyvfinal);
        TextViewtotal = findViewById(R.id.total_amt);
        TextViewinvoiceid = findViewById(R.id.invoice_id);
        TextViewsubtotal = findViewById(R.id.tv_subtotal);
        Textviewcgstname = findViewById(R.id.tv_gstname1);
        Textviewcgstvalue = findViewById(R.id.tv_gstvalue1);
        Textviewsgstname = findViewById(R.id.tv_gstname2);
        TextviewSgstvalue = findViewById(R.id.tv_gstvalue2);
        Tvtimer = findViewById(R.id.tvtimer);
        tvadderss = findViewById(R.id.tv_address);
        tvinvoicedate = findViewById(R.id.tv_invoicedate);
        tnxidno = findViewById(R.id.tnxn);
        bills = findViewById(R.id.billparent);
        outletaddress = findViewById(R.id.outletaddress);
        outletname = findViewById(R.id.outletname);
        outletphoneno = findViewById(R.id.outletphone);
        tvtotalamount2 = findViewById(R.id.total_amt2);
        TextViewDcharge = findViewById(R.id.tv_deliverycharges);
        mainparent = findViewById(R.id.mainparent);
        mProgressDialog = new ProgressDialog(PaymentSuccess.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PaymentSuccess.this);
        recyclerView.setLayoutManager(linearLayoutManager);
       /* DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);*/
        datasetAdd = new ArrayList<>();
        fetchCart();
        //  datasetAdd = db.getAllContacts();
        transactionid = getIntent().getStringExtra("trnxnid");
        paymentmode = getIntent().getStringExtra("paymode");
        if (!transactionid.equalsIgnoreCase("null")) {
            tnxidno.setVisibility(View.VISIBLE);
            tnxidno.setText("Transaction Reference No ." + transactionid);
        } else {
            tnxidno.setVisibility(View.GONE);
        }


    }

    private void LoadInvoice(List<CartItem> datasetAdd, int billid) {
        String json = "{\"outlet_id\":" + cartoutid +
                ",\"bill_id\":" + billid +
                ",\"transaction_id\":" + transactionid +
                ",\"payment_mode\":" + paymentmode +
                " ,\"productlist\": [";

        for (int i = 0; i < datasetAdd.size(); i++) {
            json += "{ \"Product\": " + datasetAdd.get(i).getProductId() + ", \"Price\": " +
                    datasetAdd.get(i).getPrice() + " ,\"Quantity\": " + datasetAdd.get(i).getQuantity() + "}";
            if (i != datasetAdd.size() - 1) json += ",";
        }

        json += "]}";
        try {
            JsonParser parser = new JsonParser();

            JsonObject jsonObject = (JsonObject) parser.parse(json);
            doGenerateInvoice(jsonObject);
            // adapter.update();

            Log.d("jsons", "Load: " + jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Tvtimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new SweetAlertDialog(PaymentSuccess.this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("\nCancel Order!!")
                        .setContentText("Are you sure want cancel ?")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                doCancelOrder();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();


            }
        });


    }

    private void doGenerateInvoice(JsonObject jsonObject) {
        final String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        final String adderss = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Address");


        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseInvoiceGen> call = apiService.dogenerateinvoice(mAccesstoken, database, jsonObject);
        call.enqueue(new Callback<ResponseInvoiceGen>() {
            @Override
            public void onResponse(Call<ResponseInvoiceGen> call, Response<ResponseInvoiceGen> response) {
                mProgressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    ResponseInvoiceGen responseInvoiceGen = response.body();
                    String invoiceid = response.body().getInvoiceNo().toString();
                    if (invoiceid != null) {
                        List<Products> datasets = responseInvoiceGen.getProducts();

                        List<Tax> dataset = responseInvoiceGen.getTax();
                        if (!dataset.isEmpty() && dataset.size() != 0) {
                            FinalbillAdapter productAdapter = new FinalbillAdapter(PaymentSuccess.this, datasets);
                            recyclerView.setAdapter(productAdapter);
                            tvadderss.setText(response.body().getConsumer_delivery_loc());
                            bills.setVisibility(View.VISIBLE);

                            if (paymentmode.equalsIgnoreCase("cod")) {
                                Tvtimer.setVisibility(View.VISIBLE);
                                long remainingtime = Long.parseLong(response.body().getRemaining_time());
                                CountDownTimer countDownTimer = new CountDownTimer(remainingtime, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                        long millis = millisUntilFinished;
                                        //Convert milliseconds into hour,minute and seconds
                                        String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                                        Tvtimer.setText(" CANCEL ORDER " + hms + " mins");//set text
                                    }

                                    public void onFinish() {
                                        Tvtimer.setVisibility(View.GONE); //On finish change timer text
                                    }
                                }.start();
                            }


                            Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            String totalamt = response.body().getTotalAmount().toString();
                            String vatotalamt = String.format("%.02f", response.body().getTotalAmount());




                            String sub_total = response.body().getSubtotal().toString();
                            String delcharge = String.format("%.02f", response.body().getDelivery_charge());


                            String vasubtotal = String.format("%.02f", response.body().getSubtotal());


                            if (dataset.size() == 1) {
                                Textviewsgstname.setText(dataset.get(0).getTax());
                                TextviewSgstvalue.setText("" + String.format("%.02f", dataset.get(0).getTaxValue()));
                            }
                            if (dataset.size() == 2) {
                                Textviewsgstname.setText(dataset.get(0).getTax());
                                TextviewSgstvalue.setText("" + String.format("%.02f", dataset.get(0).getTaxValue()));
                                Textviewcgstname.setText(dataset.get(1).getTax());
                                Textviewcgstvalue.setText("" +String.format("%.02f", dataset.get(1).getTaxValue()));
                            }


                            tvinvoicedate.setText("Date & Time : " + response.body().getDate_time());
                            tvtotalamount2.setText("₹ " + vatotalamt);
                            TextViewsubtotal.setText("₹ " + vasubtotal);
                            TextViewtotal.setText("₹ " + vatotalamt);
                            TextViewDcharge.setText("₹ " + delcharge);
                            TextViewinvoiceid.setText("Invoice No. : " + invoiceid);
                            outletaddress.setText("" + response.body().getOutlet_addr());
                            outletphoneno.setText("Phone : " + response.body().getOutlet_mobile());
                            outletname.setText("Outlet : " + response.body().getOutlet_name());


                            //  LocalPreferences.clearPreferences(getApplicationContext());
                            LocalPreferences.storeStringPreference(getApplicationContext(), "Accesstoken", mAccesstoken);
                            // new DatabaseHandler(getApplicationContext()).removeAll();

                        }
                    }


                } else {
                    mainparent.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<ResponseInvoiceGen> call, Throwable t) {
                mProgressDialog.dismiss();

                if (new AppUtility(PaymentSuccess.this).checkInternet()) {

                } else {
                    Toast.makeText(PaymentSuccess.this, "NO INTERNET", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void fetchCart() {
        final String mbillid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "billid");
        cartoutid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "cartoutid");
        //  datasetcartlist = db.getAllContacts();
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
                if (response.body() != null && response.code() == 200) {
                    ResponseBrahmaCart responseBrahmaCart = response.body();

                    datasetAdd = responseBrahmaCart.getCartItems();
                    LoadInvoice(datasetAdd, Integer.parseInt(mbillid));


                }
            }

            @Override
            public void onFailure(Call<ResponseBrahmaCart> call, Throwable t) {
                Toast.makeText(PaymentSuccess.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });

        // totallist = new ArrayList<>();
//        mCartAdapter = new CartAdapter(getApplication(), datasetcartlist, PaymentActivity.this, true);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
        finishAffinity();
    }

    public void onBack(View view) {
        onBackPressed();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy ");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void doCancelOrder() {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        JsonObject assign = new JsonObject();
        final String mbillid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "billid");

        mProgressDialog.show();
        assign.addProperty("bill_id", Integer.parseInt(mbillid));
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseCancelOrder> call = apiService.docancelOrder(mAccesstoken, database, assign);
        call.enqueue(new Callback<ResponseCancelOrder>() {
            @Override
            public void onResponse(Call<ResponseCancelOrder> call, Response<ResponseCancelOrder> response) {
                mProgressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    Tvtimer.setVisibility(View.GONE);
                    String status = response.body().getStatus();
                    if (status.equalsIgnoreCase("done")) {
                        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                        finish();

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseCancelOrder> call, Throwable t) {
                mProgressDialog.dismiss();

            }
        });
    }
}
