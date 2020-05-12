package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.MyViewHolder> {

    Context context;
    private List<ConfirmOrderModel> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, rupees, quantity;

        public MyViewHolder(View view) {
            super(view);
            dishName = (TextView) view.findViewById(R.id.dishName);
            rupees = (TextView) view.findViewById(R.id.rupees);
            quantity = (TextView) view.findViewById(R.id.quantity);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public ConfirmOrderAdapter(Context mainActivityContacts, List<ConfirmOrderModel> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public ConfirmOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cnfrm_order_recycler, parent, false);
        return new ConfirmOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConfirmOrderAdapter.MyViewHolder holder, int position) {
      /*  ConfirmOrderModel lists = OfferList.get(position);
        holder.dishName.setText(lists.getDishName());
        holder.quantity.setText(lists.getQuantity());
        holder.rupees.setText(lists.getRupees());*/
    }


}

