package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.billdesk.sdk.PaymentOptions;
import com.finwin.brahmagiri.fooddelivery.Adapter.CartAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ConfirmOrderAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ConfirmOrderModel;
import com.finwin.brahmagiri.fooddelivery.Adapter.OutofstockAdapter;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.Responses.OutStockProduct;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCreateBill;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProfile;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponsePay;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.COD;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.PAYTM;
import static com.finwin.brahmagiri.fooddelivery.utilities.Constants.database;

public class PaymentActivity extends AppCompatActivity implements showhide {
    private List<ConfirmOrderModel> homeListModelClassArrayList;
    List<CartItem> datasetcartlist;
    DatabaseHandler db;
    boolean cod = true;
    private RecyclerView menuRecycler;
    private ConfirmOrderAdapter bAdapter;
    String cartoutid;
    String name;
    String mobile;
    String totalamtwithdelcharge;
    String email, address, street, city, pincode, landmark;
    View rootview;
    Double totalamt;
    TextView tvTotal_co, tvCheckout, tvEditAddrs, tvAddressName, tvAddress, tvChngPymnt;
    ImageButton ibtn_back;
    ImageView imgChngPymnt;
    LinearLayout linrChngPymnt;
    CartAdapter mCartAdapter;
    String total, getewaytotal;


    String itemArryId, itemArryName, itemArryCount, itemArryAmount,
            StrBndlTotal = "";
    private RadioGroup collection;
    private RadioButton radioButton;
    String deliveryoption, deliverycharge;
    LinearLayout laytdelcharge, layttotamt;
    TextView header, delcharges, totamamount;
    String paymentmode = "cod";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        tvTotal_co = findViewById(R.id.tv_total_co);
        tvAddressName = findViewById(R.id.tv_addressName);
        tvAddress = findViewById(R.id.tv_address);
        header = findViewById(R.id.tvheader);
        delcharges = findViewById(R.id.tv_delivery_charge);
        totamamount = findViewById(R.id.tv_totalamt);
        collection = (RadioGroup) findViewById(R.id.radiogrp);


        laytdelcharge = findViewById(R.id.laytdeliverycharge);
        layttotamt = findViewById(R.id.layout_totalamt);

        progressDialog=new ProgressDialog(PaymentActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        tvCheckout = findViewById(R.id.tv_checkout);
        ibtn_back = findViewById(R.id.ibtn_back_co);
        tvEditAddrs = findViewById(R.id.tv_edit_addrs);

        linrChngPymnt = findViewById(R.id.linr_chng_pymnt);
        imgChngPymnt = findViewById(R.id.img_chng_pymnt);
        tvChngPymnt = findViewById(R.id.tv_chng_pymnt);
        total = getIntent().getStringExtra("total");
        double d = Double.parseDouble(total);
        int roundOff = (int) d * 100;
        getewaytotal = String.valueOf(roundOff);
        datasetcartlist = new ArrayList<>();
        db = new DatabaseHandler(getApplicationContext());

        tvTotal_co.setText("" + total);
        delcharges.setText("0");
        deliveryoption = "by_customer";
        collection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = collection.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().equals("Collect from outlet")) {
                    Log.d("onCheckedChanged", "out: ");
                    deliveryoption = "by_customer";
                    tvTotal_co.setText("" + total);
                    delcharges.setText("0");

                } else if (radioButton.getText().equals("Home Delivery")) {
                    deliveryoption = "take_away";
                    tvTotal_co.setText("" + totalamtwithdelcharge);
                    delcharges.setText("" + deliverycharge);
                    Log.d("onCheckedChanged", "home: ");

                } else {
                    deliveryoption = "by_customer";
                }
            }
        });


        menuRecycler = (RecyclerView) findViewById(R.id.menuRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        menuRecycler.setLayoutManager(layoutManager);
        menuRecycler.setItemAnimator(new DefaultItemAnimator());
        if (cod) {
            tvCheckout.setText("Place Order");

        } else {
            tvCheckout.setText("Confirm & Checkout");
        }
        fetchCart();

        homeListModelClassArrayList = new ArrayList<>();

        //===========================================================
        if (TextUtils.isEmpty(StrBndlTotal)) {
            StrBndlTotal = "";
        }
        totamamount.setText(total);

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cod) {
                    Load(datasetcartlist, 0);

                } else {
                    Load(datasetcartlist, 0);

                }

            }
        });

        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvEditAddrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAddresses("Address");

            }
        });

        linrChngPymnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePayment();
            }
        });

    }

    private void fetchCart() {
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
                    totalamtwithdelcharge = String.valueOf(response.body().getTotalAmnt());
                    deliverycharge = String.valueOf(response.body().getDeliveryCharge());
                    //   delcharges.setText(""+deliverycharge);

                    datasetcartlist = responseBrahmaCart.getCartItems();

                    mCartAdapter = new CartAdapter(getApplication(), datasetcartlist, PaymentActivity.this, true);
                    menuRecycler.setAdapter(mCartAdapter);


                }
            }

            @Override
            public void onFailure(Call<ResponseBrahmaCart> call, Throwable t) {

            }
        });

        // totallist = new ArrayList<>();
//        mCartAdapter = new CartAdapter(getApplication(), datasetcartlist, PaymentActivity.this, true);


    }
    //========

    private void ChangePayment() {
        AlertDialog.Builder b = new AlertDialog.Builder(Objects.requireNonNull(PaymentActivity.this));
        b.setTitle("Select Payment Method");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_payment_view, null);
        b.setView(dialogView);

        LinearLayout linCOD = (LinearLayout) dialogView.findViewById(R.id.linr_cod);
        LinearLayout linPaytm = (LinearLayout) dialogView.findViewById(R.id.linr_paytm);

        final AlertDialog alertDialog = b.create();
        linCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                setPaymentMode(COD);
            }
        });

        linPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                setPaymentMode(PAYTM);
            }
        });
        alertDialog.show();
    }

    private void setPaymentMode(String type) {
        if (type.equals(PAYTM)) {
            imgChngPymnt.setImageResource(R.drawable.credit_card);
            tvChngPymnt.setText(R.string.s_paytm);
            cod = false;
            paymentmode = "op";
        } else if (type.equals(COD)) {
            imgChngPymnt.setImageResource(R.drawable.cash);
            tvChngPymnt.setText(R.string.s_cod);
            cod = true;
            paymentmode = "cod";
        }
    }

    //========



    private void setAddress(String head, String address) {
        tvAddressName.setText(head);
        tvAddress.setText(address);
    }



    @Override
    public void clicked(int value, String code) {

    }

    @Override
    public void clickedpdct(int value, String code, String pname, String price) {

    }

    @Override
    public void show(String show) {

    }

    @Override
    public void delete(String code) {

    }

    private void Load(List<CartItem> datasetAdd, int billid) {
        String partnerid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");

        String json = "{\"outlet_id\":" + cartoutid +
                ",\"consumer_id\":" + partnerid +
                ",\"collecting_option\":" + deliveryoption +
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
            doGenerateBill(jsonObject);
            // adapter.update();

            Log.d("jsons", "Load: " + jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void doGenerateBill(JsonObject jsonObject) {
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseCreateBill> call = apiService.postRawJSON(mAccesstoken, database, jsonObject);
        call.enqueue(new Callback<ResponseCreateBill>() {
            @Override
            public void onResponse(Call<ResponseCreateBill> call, Response<ResponseCreateBill> response) {
                if (response.body() != null && response.code() == 200) {

                    if (response.body().getBillId() != 0) {
                        String mbillid = response.body().getBillId().toString();
                        if (cod) {

                            LocalPreferences.storeStringPreference(getApplicationContext(), "billid", mbillid);
                            startActivity(new Intent(getApplicationContext(), PaymentSuccess.class).putExtra("trnxnid", "null").putExtra("paymode", "cod"));
                        } else {
                            Log.e("onResponse", "onResponse: " + getewaytotal);


                            payNowCalled(mbillid);
                            LocalPreferences.storeStringPreference(getApplicationContext(), "billid", mbillid);

                        }
                        // LoadInvoice(datasetAdd,Integer.parseInt(mbillid));
                    }else{


                        ResponseCreateBill responseCreateBill=response.body();

                        List <OutStockProduct>dataset=responseCreateBill.getProducts();
                        showpopup(dataset);
                        Toast.makeText(getApplicationContext(),""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }


                }

            }


            @Override
            public void onFailure(Call<ResponseCreateBill> call, Throwable t) {

            }
        });
    }

    private void showpopup(List<OutStockProduct> dataset) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final RecyclerView recyclerView = alertLayout.findViewById(R.id.recyvoutstock);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new OutofstockAdapter(getApplicationContext(),dataset));



        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Your cart has quantity less than available quantity please choose quantity less for below products");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
    /*    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    private void ChangeAddresses(String addrsType) {
        AlertDialog.Builder b = new AlertDialog.Builder(PaymentActivity.this);
        String addTest = "Change " + addrsType + " Address";
        b.setTitle(addTest);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_chng_address, null);
        final EditText edname = dialogView.findViewById(R.id.ed_name);
        final EditText edmobile = dialogView.findViewById(R.id.ed_mobile);
        final EditText edlandmark = dialogView.findViewById(R.id.ed_landmark);
        final EditText edaddress = dialogView.findViewById(R.id.ed_address);
        final EditText edstreet = dialogView.findViewById(R.id.ed_street);
        final EditText edcity = dialogView.findViewById(R.id.ed_city);
        final EditText edpin = dialogView.findViewById(R.id.ed_pin);
        final EditText edemail = dialogView.findViewById(R.id.ed_email);

        edname.setText(name);
        edmobile.setText(mobile);
        edlandmark.setText(landmark);
        edaddress.setText(address);
        edstreet.setText(street);
        edcity.setText(city);
        edpin.setText(pincode);
        edemail.setText(email);
        b.setView(dialogView);
        b.setCancelable(false);
        b.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("onClick: ", "+++++");
                doUpdateProfile(edname.getText().toString(), edmobile.getText().toString(), edlandmark.getText().toString(),
                        edaddress.getText().toString(), edstreet.getText().toString(), edcity.getText().toString(), edemail.getText().toString(), edpin.getText().toString());
            }
        });

        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("onClick: ", "-----");
            }
        });

//        LinearLayout linCOD = (LinearLayout) dialogView.findViewById(R.id.linr_cod);
//        LinearLayout linPaytm = (LinearLayout) dialogView.findViewById(R.id.linr_paytm);
//
        final AlertDialog alertDialog = b.create();
//        linCOD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        linPaytm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
        alertDialog.show();
    }

    private void doUpdateProfile(String edname, String edmobile, String edlandmark,
                                 String edaddress, String edstreet, String edcity, String edemail, String edpin) {
        String userid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("partner_id", Integer.parseInt(userid));
        jsonObject.addProperty("name", edname);
        jsonObject.addProperty("mobile", edmobile);
        jsonObject.addProperty("landmark", edlandmark);
        jsonObject.addProperty("address", edaddress);
        jsonObject.addProperty("street", edstreet);
        jsonObject.addProperty("city", edcity);
        jsonObject.addProperty("email", edemail);
        jsonObject.addProperty("pincode", edpin);


        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<JsonObject> call = apiService.doUpdateProfile(database, mAccesstoken, jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null && response.code() == 200) {
                    doFetch();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void doFetch() {
        progressDialog.show();
        String userid = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "partnerid");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("partner_id", Integer.parseInt(userid));
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseFetchProfile> call = apiService.doFetchProfile(database, mAccesstoken, jsonObject);
        call.enqueue(new Callback<ResponseFetchProfile>() {
            @Override
            public void onResponse(Call<ResponseFetchProfile> call, Response<ResponseFetchProfile> response) {
                progressDialog.dismiss();
                if (response.body() != null && response.code() == 200) {
                    name = response.body().getName();
                    mobile = response.body().getMobile();
                    email = response.body().getEmail();
                    address = response.body().getHouseNo();
                    street = response.body().getStreet();
                    city = response.body().getCity();
                    landmark = response.body().getLandmark();
                    pincode = response.body().getPincode().toString();

                    String address = name + "\n" + response.body().getHouseNo() + " ," + response.body().getStreet() + " ," + response.body().getCity() + "  ,"
                            + response.body().getDistrict() + " ,"
                            + response.body().getState() + " ," + "\n" + "Landmark - " + response.body().getLandmark() + "\n" + "Pincode - " + response.body().getPincode();
                    LocalPreferences.storeStringPreference(getApplicationContext(), "Address", address);
                    // binding.address1.setText(address);
                    tvAddress.setText(address);
                }
            }

            @Override
            public void onFailure(Call<ResponseFetchProfile> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResumepayment", ": onResumepayment");
        doFetch();
        String address = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Address");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void payNowCalled(String Billid) {
        // call BillDesk SDK
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getApplicationContext(), "Accesstoken");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("bill_id", Integer.parseInt(Billid));
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponsePay> call = apiService.doFetchpayment(database, mAccesstoken, jsonObject);
        call.enqueue(new Callback<ResponsePay>() {
            @Override
            public void onResponse(Call<ResponsePay> call, Response<ResponsePay> response) {
              SampleCallBack objSampleCallBack = new SampleCallBack();
                Intent sdkIntent = new Intent(getApplicationContext(), PaymentOptions.class);
                String strPGMsg = response.body().getStringStrPGMsg();
                sdkIntent.putExtra("msg", strPGMsg);
                String strTokenMsg = null;
                if (strTokenMsg != null && strTokenMsg.length() > strPGMsg.length()) {

                    sdkIntent.putExtra("token", strTokenMsg);
                }
                sdkIntent.putExtra("user-email", email);
                sdkIntent.putExtra("user-mobile", mobile);
                sdkIntent.putExtra("callback", objSampleCallBack);
                startActivity(sdkIntent);
            }

            @Override
            public void onFailure(Call<ResponsePay> call, Throwable t) {

            }
        });


    }

    public static String checkSumSHA256(String plaintext) {

        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance("SHA-256"); // step 2

            md.update(plaintext.getBytes("UTF-8")); // step 3

        } catch (Exception e) {

            md = null;

        }


        StringBuffer ls_sb = new StringBuffer();

        byte raw[] = md.digest(); // step 4

        for (int i = 0; i < raw.length; i++)

            ls_sb.append(char2hex(raw[i]));

        return ls_sb.toString(); // step 6

    }

    public static String char2hex(byte x) {

        char arr[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',

                'B', 'C', 'D', 'E', 'F'};


        char c[] = {arr[(x & 0xF0) >> 4], arr[x & 0x0F]};

        return (new String(c));

    }


}



