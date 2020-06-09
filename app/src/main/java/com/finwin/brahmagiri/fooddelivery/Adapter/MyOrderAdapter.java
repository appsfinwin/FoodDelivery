package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    Context context;
    private List<MyOrderModel> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, rupees, quantity;

        public MyViewHolder(View view) {
            super(view);

        }
    }
    public MyOrderAdapter(Context context) {
        this.context = context;
    }
    public MyOrderAdapter(Context mainActivityContacts, List<MyOrderModel> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myorder_recycler, parent, false);
        return new MyOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrderAdapter.MyViewHolder holder, int position) {
      /*  MyOrderModel lists = OfferList.get(position);
        holder.dishName.setText(lists.getDishName());
        holder.quantity.setText(lists.getQuantity());
        holder.rupees.setText(lists.getRupees());*/
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}

