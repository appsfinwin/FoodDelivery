package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.ActivityMain;
import com.finwin.brahmagiri.fooddelivery.Adapter.FinalbillAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseInvoiceGen;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentSuccess extends AppCompatActivity  {
    DatabaseHandler db;
    List<ProductEntryModel> datasetAdd;
    RecyclerView recyclerView;
    Button btninvoice;
    TextView TextViewtotal,TextViewsubtotal,TextViewtax,TextViewinvoiceid;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success2);
        recyclerView=findViewById(R.id.recyvfinal);
        TextViewtotal=findViewById(R.id.total_amt);
        TextViewinvoiceid=findViewById(R.id.invoice_id);
        TextViewsubtotal=findViewById(R.id.tv_subtotal);
        TextViewtax=findViewById(R.id.tv_taxamt);
        mProgressDialog = new ProgressDialog(PaymentSuccess.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        LinearLayoutManager linearLayoutManager=    new LinearLayoutManager(PaymentSuccess.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        datasetAdd=new ArrayList<>();
        db=new DatabaseHandler(getApplicationContext());
        datasetAdd = db.getAllContacts();

        FinalbillAdapter productAdapter = new FinalbillAdapter(PaymentSuccess.this, datasetAdd);
        recyclerView.setAdapter(productAdapter);
        String mbillid= LocalPreferences.retrieveStringPreferences(getApplicationContext(),"billid");
        LoadInvoice(datasetAdd,Integer.parseInt(mbillid));
    }
    private void LoadInvoice(List<ProductEntryModel> datasetAdd, int billid) {
        String json = "{\"outlet_id\":" + "319" +
                ",\"bill_id\":" + billid +
                " ,\"productlist\": [";

        for (int i = 0; i < datasetAdd.size(); i++) {
            json += "{ \"Product\": " + datasetAdd.get(i).getId() + ", \"Price\": " +
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

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseInvoiceGen> call = apiService.dogenerateinvoice(mAccesstoken, "test", jsonObject);
        call.enqueue(new Callback<ResponseInvoiceGen>() {
            @Override
            public void onResponse(Call<ResponseInvoiceGen> call, Response<ResponseInvoiceGen> response) {
                mProgressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    String invoiceid=response.body().getInvoiceId().toString();
                    if (invoiceid!=null){
                        Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String totalamt=response.body().getTotalAmount().toString();
                        String sub_total=response.body().getSubtotal().toString();
                        String taxamt=response.body().getTaxAmount().toString();
                        TextViewsubtotal.setText("₹ "+sub_total);
                        TextViewtotal.setText("₹ "+totalamt);
                        TextViewtax.setText("₹ "+taxamt);
                        TextViewinvoiceid.setText("Invoice Id : "+invoiceid);
                        LocalPreferences.clearPreferences(getApplicationContext());
                        LocalPreferences.storeStringPreference(getApplicationContext(),"Accesstoken",mAccesstoken);
                        new DatabaseHandler(getApplicationContext()).removeAll();

                    }



                }

            }



            @Override
            public void onFailure(Call<ResponseInvoiceGen> call, Throwable t) {
                mProgressDialog.dismiss();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
        finishAffinity();
    }
}
