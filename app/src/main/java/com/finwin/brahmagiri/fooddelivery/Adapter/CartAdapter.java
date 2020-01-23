package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ToCartButtonListener;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    private List<CartModel> OfferList;

    ToCartButtonListener toCartBtnLstnr;

    public CartAdapter(Context mainActivityContacts, List<CartModel> offerList,
                       ToCartButtonListener _toCartBtnLstnr) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
        this.toCartBtnLstnr = _toCartBtnLstnr;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemAmount;
        TextView tvItemCount;
        ImageView imgMinus;
        ImageView imgAdd;
        ImageView imgDelete;

        MyViewHolder(View view) {
            super(view);
            tvItemName = (TextView) view.findViewById(R.id.tv_itmname_cart);
            tvItemAmount = (TextView) view.findViewById(R.id.tv_amnt_cart);
            tvItemCount = (TextView) view.findViewById(R.id.tv_count_cart);

            imgMinus = (ImageView) view.findViewById(R.id.img_cart_minus);
            imgAdd = (ImageView) view.findViewById(R.id.img_cart_add);
            imgDelete = (ImageView) view.findViewById(R.id.img_delete);
        }
    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_itm_rcylr, parent, false);
        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
        final CartModel lists = OfferList.get(position);
        holder.tvItemName.setText(lists.getItemName());

        int cnt = Integer.parseInt(lists.getItemCount());
        holder.tvItemCount.setText(String.valueOf(cnt));
        double amnt = Double.parseDouble(lists.getItemAmount());
        double total = cnt * amnt;
        holder.tvItemAmount.setText(String.valueOf(total));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
////                Bundle bundle = new Bundle();
//                Log.e("onClick: ", "oooooooo");
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new FragConfirmOrder();
////              bundle.putString(mstrType, "MOB");
////              myFragment.setArguments(bundle);
//                activity.getSupportFragmentManager().beginTransaction().replace(
//                        R.id.frame_layout,
//                        myFragment).addToBackStack(null).commit();

//                Log.e("addRemitncAmnt: ", String.valueOf(addRemitncAmnt()));
//                Log.e("setTotalItemAmount: ", String.valueOf(setTotalItemAmount()));
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OfferList.remove(position);
                ConstantClass.hMapCartItem.remove(lists.getItemID());
                toCartBtnLstnr.calcSubTotal();
                notifyDataSetChanged();
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lists.setItemCount(String.valueOf(Integer.parseInt(lists.getItemCount()) + 1));

                int cnt = Integer.parseInt(lists.getItemCount());
                holder.tvItemCount.setText(String.valueOf(cnt));

                double amnt = Double.parseDouble(lists.getItemAmount());
                double total = cnt * amnt;
                holder.tvItemAmount.setText(String.valueOf(total));

                Map<String, String> map = ConstantClass.hMapCartItem.get(lists.getItemID());
                Objects.requireNonNull(map).put(ConstantClass.H_ITEM_AMNT, String.valueOf(total));
//                Log.e("map.get:",map.get(ConstantClass.H_ITEM_AMNT) );

                toCartBtnLstnr.calcSubTotal();

//               Log.e("onClick: ", String.valueOf(((Map) ConstantClass.hMapCartItem.get(lists.getItemID())).get(ConstantClass.H_ITEM_AMNT)));
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("onClick: imgMinus",lists.getItemCount() );
                if (Integer.parseInt(lists.getItemCount()) > 1) {
                    lists.setItemCount(String.valueOf(Integer.parseInt(lists.getItemCount()) - 1));

                    int cnt = Integer.parseInt(lists.getItemCount());
                    holder.tvItemCount.setText(String.valueOf(cnt));
                    double amnt = Double.parseDouble(lists.getItemAmount());
                    double total = cnt * amnt;
                    holder.tvItemAmount.setText(String.valueOf(total));

                    Map<String, String> map = ConstantClass.hMapCartItem.get(lists.getItemID());
                    Objects.requireNonNull(map).put(ConstantClass.H_ITEM_AMNT, String.valueOf(total));

                    toCartBtnLstnr.calcSubTotal();
                }
            }
        });

//        Log.e("onBindViewHolder:", "RUNNNNN........!!!");
    }




}

