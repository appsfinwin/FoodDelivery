package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.Adapter.CartAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.FinalbillAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;
import com.finwin.brahmagiri.fooddelivery.Responses.Products;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseInvoiceGen;
import com.finwin.brahmagiri.fooddelivery.Responses.Tax;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
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

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.Utilities.Constants.database;

public class PaymentSuccess extends AppCompatActivity {
    DatabaseHandler db;
    List<CartItem> datasetAdd;
    RecyclerView recyclerView;
    Button btninvoice;
    String cartoutid;
    TextView TextViewtotal, TextViewsubtotal, TextViewDcharge, TextViewinvoiceid,Textviewcgstname,Textviewcgstvalue,Textviewsgstname,TextviewSgstvalue;
    ProgressDialog mProgressDialog;
    LinearLayout mainparent;
    String transactionid;
    String paymentmode;
    final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
            { "and", "a", "second", "test" } };
    TextView tvadderss,tvtotalamount2,tvinvoicedate;


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
        tvadderss=findViewById(R.id.tv_address);
        tvinvoicedate=findViewById(R.id.tv_invoicedate);


        tvtotalamount2=findViewById(R.id.total_amt2);
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
        transactionid=getIntent().getStringExtra("trnxnid");
        paymentmode=getIntent().getStringExtra("paymode");



    }

    private void LoadInvoice(List<CartItem> datasetAdd, int billid) {
        String json = "{\"outlet_id\":" + cartoutid +
                ",\"bill_id\":" + billid +
                ",\"transaction_id\":" + transactionid+
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


    }

    private void doGenerateInvoice(JsonObject jsonObject) {
        final String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        final String adderss=LocalPreferences.retrieveStringPreferences(getApplicationContext(),"Address");


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
                            tvadderss.setText(adderss);





                            Toast.makeText(getApplicationContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            String totalamt = response.body().getTotalAmount().toString();
                            String sub_total = response.body().getSubtotal().toString();
                            String delcharge = response.body().getDelivery_charge().toString();

                            for (int i=0;i<dataset.size();i++){
                           String taxname=     dataset.get(i).getTax();
                           if (taxname.equalsIgnoreCase("SGST@2.5%")){
                               Textviewsgstname.setText(dataset.get(i).getTax());
                               TextviewSgstvalue.setText("" + dataset.get(i).getTaxValue());
                               Textviewcgstname.setVisibility(View.GONE);
                               Textviewcgstvalue.setVisibility(View.GONE);
                           }else if (taxname.equalsIgnoreCase("CGST@2.5%")){
                               Textviewcgstname.setText(dataset.get(i).getTax());
                               Textviewcgstvalue.setText("" + dataset.get(i).getTaxValue());
                               Textviewsgstname.setVisibility(View.GONE);
                               TextviewSgstvalue.setVisibility(View.GONE);
                           }





                                }


tvinvoicedate.setText(getDateTime());
                            tvtotalamount2.setText("₹ "+totalamt);
                            TextViewsubtotal.setText("₹ " + sub_total);
                            TextViewtotal.setText("₹ " + totalamt);
                            TextViewDcharge.setText("₹ " + delcharge);
                            TextViewinvoiceid.setText("Invoice No. : " + invoiceid);

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
        String json = "{\"user_id\":" + Integer.parseInt(userid) + ",\"outlet\":" + Integer.parseInt(cartoutid) + "}";
        JsonParser parser = new JsonParser();

        JsonObject jsonObject = (JsonObject) parser.parse(json);
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
}
