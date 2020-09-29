package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finwin.brahmagiri.fooddelivery.Activity.OrderDetails;
import com.finwin.brahmagiri.fooddelivery.Responses.PreviousSale;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<PreviousSale> mResults;
    private Context context;

    private boolean isLoadingAdded = false;

    public MyOrdersAdapter(Context context) {
        this.context = context;
        mResults = new ArrayList<>();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.myorder_recycler2, parent, false);
        viewHolder = new OrderVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PreviousSale lists = mResults.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final OrderVH orderVH = (OrderVH) holder;


                orderVH.invoicenumber.setText("Invoice number : " + lists.getInvoiceNo());
                orderVH.tvdate.setText("Date : " + lists.getInvoiceDate());
                orderVH.tvamt.setText("₹ " + lists.getTotalAmount());
                orderVH.outletname.setText("Outlet name : " + lists.getOutletName());
                String status = lists.getOrderStatus();
                orderVH.tvOrderstats.setText("Order Status : "+status);




                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(PreviousSale r) {
        mResults.add(r);
        notifyItemInserted(mResults.size() - 1);
    }

    public void addAll(List<PreviousSale> moveResults) {
        for (PreviousSale result : moveResults) {
            add(result);
        }
    }

    public void remove(PreviousSale r) {
        int position = mResults.indexOf(r);
        if (position > -1) {
            mResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new PreviousSale());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mResults.size() - 1;
        PreviousSale result = getItem(position);

        if (result != null) {
            mResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public PreviousSale getItem(int position) {
        return mResults.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class OrderVH extends RecyclerView.ViewHolder {
        TextView invoicenumber, tvdate, outletname, tvamt, tvOrderstats;

        public OrderVH(View view) {

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
                    String id = mResults.get(pos).getInvoiceId().toString();
                    String status = mResults.get(pos).getStatus();
                    context.startActivity(new Intent(context, OrderDetails.class).putExtra("id", id)

                            //  .putExtra("custname",OfferList.get(pos).getc)
                            .putExtra("status", status));
                }
            });
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}