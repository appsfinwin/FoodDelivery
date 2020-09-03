package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.Responses.OutStockProduct;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ToCartButtonListener;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;

import java.util.List;

public class OutofstockAdapter extends RecyclerView.Adapter<OutofstockAdapter.MyViewHolder> {
    Context context;
    private List<OutStockProduct> dataset;
    showhide mshowhide;

    ToCartButtonListener toCartBtnLstnr;
    boolean showquantityupdate;


    public OutofstockAdapter(Context mainActivityContacts, List<OutStockProduct> dataset) {

        this.context = mainActivityContacts;
        this.dataset = dataset;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemAmount;
        TextView tvItemCount;
        ImageView imgMinus;
        ImageView imgAdd;
        ImageView imgDelete, ItemImage;

        MyViewHolder(View view) {
            super(view);
            tvItemName = (TextView) view.findViewById(R.id.tv_itmname_cart);

            tvItemCount = (TextView) view.findViewById(R.id.tv_count_cart);



        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public OutofstockAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_itm_outstock, parent, false);
        return new OutofstockAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OutofstockAdapter.MyViewHolder holder, final int position) {
        final OutStockProduct lists = dataset.get(position);
        holder.tvItemName.setText(""+lists.getProductName());




       Double cnt = lists.getAvlQty();
       holder.tvItemCount.setText("Available Qty : "+String.valueOf(cnt));
        // Glide.with(context).load(lists.getImageUrl()).into(holder.ItemImage);



    }


}

