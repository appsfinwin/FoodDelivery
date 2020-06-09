package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Adapter.ConfirmOrderAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.ConfirmOrderModel;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.COD;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.PAYTM;

public class FragConfirmOrder extends Fragment {

    private ArrayList<ConfirmOrderModel> homeListModelClassArrayList;
    private RecyclerView menuRecycler;
    private ConfirmOrderAdapter bAdapter;
    View rootview;
    TextView tvTotal_co, tvCheckout, tvEditAddrs, tvAddressName, tvAddress, tvChngPymnt;
    ImageButton ibtn_back;
    ImageView imgChngPymnt;
    LinearLayout linrChngPymnt;

    String itemArryId, itemArryName, itemArryCount, itemArryAmount,
            StrBndlTotal = "";
//    String foodName[] = {"Beef items", "Chicken"};
//    String quantity[] = {"Quantity 1", "Quantity 1"};
//    String rupees[] = {"Rs 300", "Rs 300"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.frag_confirm_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTotal_co = rootview.findViewById(R.id.tv_total_co);
        tvAddressName = rootview.findViewById(R.id.tv_addressName);
        tvAddress = rootview.findViewById(R.id.tv_address);
        tvCheckout = rootview.findViewById(R.id.tv_checkout);
        ibtn_back = rootview.findViewById(R.id.ibtn_back_co);
        tvEditAddrs = rootview.findViewById(R.id.tv_edit_addrs);

        linrChngPymnt = rootview.findViewById(R.id.linr_chng_pymnt);
        imgChngPymnt = rootview.findViewById(R.id.img_chng_pymnt);
        tvChngPymnt = rootview.findViewById(R.id.tv_chng_pymnt);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            StrBndlTotal = bundle.getString(ConstantClass.TOTAL_AMNT, "");
        }

        menuRecycler = (RecyclerView) rootview.findViewById(R.id.menuRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        menuRecycler.setLayoutManager(layoutManager);
        menuRecycler.setItemAnimator(new DefaultItemAnimator());

        homeListModelClassArrayList = new ArrayList<>();

//        for (int i = 0; i < foodName.length; i++) {
//            ConfirmOrderModel beanClassForRecyclerView_contacts = new ConfirmOrderModel(foodName[i], quantity[i], rupees[i]);
//            homeListModelClassArrayList.add(beanClassForRecyclerView_contacts);
//        }

        for (Map.Entry<String, Map<String, String>> entry : ConstantClass.hMapCartItem.entrySet()) {
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
        }

        bAdapter = new ConfirmOrderAdapter(getActivity(), homeListModelClassArrayList);
        menuRecycler.setAdapter(bAdapter);

        //===========================================================
        if (TextUtils.isEmpty(StrBndlTotal)) {
            StrBndlTotal = "";
        }
        tvTotal_co.setText(StrBndlTotal);

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new FragMyOrder();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fm).beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
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
        AlertDialog.Builder b = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
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
        AlertDialog.Builder b = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
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


}
