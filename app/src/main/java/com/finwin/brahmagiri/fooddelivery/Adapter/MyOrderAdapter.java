package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.Activity.OrderDetails;
import com.finwin.brahmagiri.fooddelivery.Responses.PreviousSale;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    Context context;
    private List<PreviousSale> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView invoicenumber, tvdate, outletname, tvamt, tvOrderstats;

        public MyViewHolder(View view) {
            super(view);
            invoicenumber = view.findViewById(R.id.invoice_no);
            tvdate = view.findViewById(R.id.invoicedate);
            outletname = view.findViewById(R.id.outletname);
            tvamt = view.findViewById(R.id.totalamt);
            tvOrderstats = view.findViewById(R.id.Orderstats);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String id = OfferList.get(pos).getInvoice_id().toString();
                    String status = OfferList.get(pos).getStatus();
                    context.startActivity(new Intent(context, OrderDetails.class).putExtra("id", id)

                            //  .putExtra("custname",OfferList.get(pos).getc)
                            .putExtra("status", status));
                }
            });
        }

    }

    public MyOrderAdapter(Context context) {
        this.context = context;
    }

    public MyOrderAdapter(Context mainActivityContacts, List<PreviousSale> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myorder_recycler2, parent, false);
        return new MyOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrderAdapter.MyViewHolder holder, int position) {
        PreviousSale lists = OfferList.get(position);
        holder.invoicenumber.setText("Invoice number : " + lists.getInvoiceNo());
        holder.tvdate.setText("Date : " + lists.getInvoiceDate());
        holder.tvamt.setText("₹ " + lists.getTotalAmount());
        holder.outletname.setText("Outlet name : " + lists.getOutletName());
        String status = lists.getStatus();
        if (status.equalsIgnoreCase("done")) {
            holder.tvOrderstats.setText("Order Status : Invoice Generated");
        }else if(status.equalsIgnoreCase("assigned")){
            holder.tvOrderstats.setText("Order Status : Assigned to Delivery Boy");
        }else if (status.equalsIgnoreCase("shipped")){
            holder.tvOrderstats.setText("Order Status : Order is on the Way");
        }else if(status.equalsIgnoreCase("cash_received_by_boy")){
            holder.tvOrderstats.setText("Order Status : Order Delivered");
        }else if(status.equalsIgnoreCase("delivered")){
            holder.tvOrderstats.setText("Order Status : Order Delivered");
        }


    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}

