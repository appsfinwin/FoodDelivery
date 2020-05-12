package com.finwin.brahmagiri.fooddelivery.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.CartAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ConfirmOrderAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ConfirmOrderModel;
import com.finwin.brahmagiri.fooddelivery.FragMyOrder;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.TableSummaryCart;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.databinding.ActivityPaymentBinding;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.COD;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.PAYTM;

public class PaymentActivity extends AppCompatActivity implements showhide {
    private ArrayList<ConfirmOrderModel> homeListModelClassArrayList;
    private RecyclerView menuRecycler;
    private ConfirmOrderAdapter bAdapter;
    ActivityPaymentBinding binding;
    View rootview;
    Double totalamt;
    TextView tvTotal_co, tvCheckout, tvEditAddrs, tvAddressName, tvAddress, tvChngPymnt;
    ImageButton ibtn_back;
    ImageView imgChngPymnt;
    LinearLayout linrChngPymnt;
    CartAdapter mCartAdapter;

    String itemArryId, itemArryName, itemArryCount, itemArryAmount,
            StrBndlTotal = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tvTotal_co = findViewById(R.id.tv_total_co);
        tvAddressName = findViewById(R.id.tv_addressName);
        tvAddress = findViewById(R.id.tv_address);

        tvCheckout = findViewById(R.id.tv_checkout);
        ibtn_back = findViewById(R.id.ibtn_back_co);
        tvEditAddrs = findViewById(R.id.tv_edit_addrs);

        linrChngPymnt = findViewById(R.id.linr_chng_pymnt);
        imgChngPymnt = findViewById(R.id.img_chng_pymnt);
        tvChngPymnt = findViewById(R.id.tv_chng_pymnt);

       /* Bundle bundle = this.getArguments();
        if (bundle != null) {
            StrBndlTotal = bundle.getString(ConstantClass.TOTAL_AMNT, "");
        }*/
        dofetchcartSummary(0,"","CART_SUMMARY");

        menuRecycler = (RecyclerView) findViewById(R.id.menuRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        menuRecycler.setLayoutManager(layoutManager);
        menuRecycler.setItemAnimator(new DefaultItemAnimator());

        homeListModelClassArrayList = new ArrayList<>();

//        for (int i = 0; i < foodName.length; i++) {
//            ConfirmOrderModel beanClassForRecyclerView_contacts = new ConfirmOrderModel(foodName[i], quantity[i], rupees[i]);
//            homeListModelClassArrayList.add(beanClassForRecyclerView_contacts);
//        }

       /* for (Map.Entry<String, Map<String, String>> entry : ConstantClass.hMapCartItem.entrySet()) {
            itemArryId = entry.getKey();
            for (Map.Entry<String, String> secndEntry : entry.getValue().entrySet()) {
                if (secndEntry.getKey().equals(ConstantClass.H_ITEM_NAME)) {
                    itemArryName = secndEntry.getValue();
                }
                if (secndEntry.getKey().equals(ConstantClass.H_ITEM_AMNT)) {
                    itemArryAmount = secndEntry.getValue();
                }
                if (secndEntry.getKey().equals(ConstantClass.H_ITEM_COUNT)) {
                    itemArryCount = secndEntry.getValue();
                }
//                itemArryName = secndEntry.getKey();
//                itemArryAmount = String.valueOf(secndEntry.getValue());
            }

            ConfirmOrderModel cnfrmOrderModel = new ConfirmOrderModel(itemArryName, itemArryCount, itemArryAmount);
            homeListModelClassArrayList.add(cnfrmOrderModel);
//
        }*/

       /* bAdapter = new ConfirmOrderAdapter(getApplicationContext(), homeListModelClassArrayList);
        menuRecycler.setAdapter(bAdapter);*/

        //===========================================================
        if (TextUtils.isEmpty(StrBndlTotal)) {
            StrBndlTotal = "";
        }
        tvTotal_co.setText(StrBndlTotal);

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is =new Intent(getApplicationContext(),PayMentGateWay.class);
                is.putExtra("total",totalamt.toString());
                startActivity(is);}
        });

        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        tvEditAddrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAddress();
            }
        });

        linrChngPymnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePayment();
            }
        });

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
            imgChngPymnt.setImageResource(R.drawable.paytm);
            tvChngPymnt.setText(R.string.s_paytm);
        } else if (type.equals(COD)) {
            imgChngPymnt.setImageResource(R.drawable.cash);
            tvChngPymnt.setText(R.string.s_cod);
        }
    }

    //========

    public void ChangeAddress() {
        AlertDialog.Builder b = new AlertDialog.Builder(Objects.requireNonNull(getApplicationContext()));
        b.setTitle("Select Delivery Address");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_address_view, null);
        b.setView(dialogView);

        final TextView tvAddrs = (TextView) dialogView.findViewById(R.id.tv_addrs);
        TextView tvAddrsOfc = (TextView) dialogView.findViewById(R.id.tv_adds_ofc);
        LinearLayout linHome = (LinearLayout) dialogView.findViewById(R.id.linrlay_home);
        LinearLayout linOffice = (LinearLayout) dialogView.findViewById(R.id.linrlay_ofc);

        final String Strhome = "KPM Complex 30/1356A1, Opposite The Hindu, 30/1356A1, National Hwy Byepass, Opposite The Hindu";
        final String StrOffice = "S02, India bulls mega mall, near dinesh mill, Jetalpur Road, Vadodara, 390020";

        tvAddrs.setText(Strhome);
        tvAddrsOfc.setText(StrOffice);

        final AlertDialog alertDialog = b.create();

        linHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                setAddress("Home", Strhome);
            }
        });

        linOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                setAddress("Office", StrOffice);
            }
        });


        alertDialog.show();

//        final String[] types = {"By Zip", "By Table"};
//        b.setItems(types, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                switch (which) {
//                    case 0:
//                        Log.e("onClick: ", String.valueOf(types));
//                        break;
//                    case 1:
//                        Log.e("onClick: ", String.valueOf(types));
//                        break;
//                }
//            }
//        });
//        b.show();
    }

    private void setAddress(String head, String address) {
        tvAddressName.setText(head);
        tvAddress.setText(address);
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
                    mCartAdapter=new CartAdapter(getApplication(), datasetcartlist, PaymentActivity.this,false);

                    menuRecycler.setAdapter(mCartAdapter);
                    Log.d("cartsummary", "onFailure: "+mCartAdapter.getItemCount());
                    tvTotal_co.setText(""+dataset.get(0).getGrandTotal());
                    totalamt=dataset.get(0).getGrandTotal();
                   /* if (mCartAdapter.getItemCount()==0){
                        binding.emptyCart.setVisibility(View.VISIBLE);
                        binding.parent.setVisibility(View.GONE);
                        binding.lnrlayConfmpay.setVisibility(View.GONE);


                    }else{
                        binding.emptyCart.setVisibility(View.GONE);
                        binding.parent.setVisibility(View.VISIBLE);
                        binding.lnrlayConfmpay.setVisibility(View.VISIBLE);
                    }
*/
                 //   binding.tvCartChrg.setText("" + dataset.get(0).getDeliveryCharge());
                 //   binding.tvCartGst.setText("" + dataset.get(0).getTax());
                 //   binding.tvCartSubtotal.setText("" + dataset.get(0).getTotal());
                  //  binding.tvCartTotal.setText("₹ " + dataset.get(0).getGrandTotal());
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


    @Override
    public void clicked(int value, String code) {

    }

    @Override
    public void show(String show) {

    }

    @Override
    public void delete(String code) {

    }
}
