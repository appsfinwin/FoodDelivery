
package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.finwin.brahmagiri.fooddelivery.Responses.LineItem;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.Onclick;

import java.text.DecimalFormat;
import java.util.List;

public class OrderdetailAdapter extends RecyclerView.Adapter<OrderdetailAdapter.ProductViewHolder> {

    Onclick mclick;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<LineItem> productList;

    //getting the context and product list with constructor

    public OrderdetailAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    public OrderdetailAdapter(Context mCtx, List<LineItem> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products_finalbill, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
       LineItem product = productList.get(position);
        if (position==0){
            holder.header.setVisibility(View.VISIBLE);
        }else {
            holder.header.setVisibility(View.GONE);
        }

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getProductName());
        holder.textViewPrice.setText("₹ " + product.getPrice());
        holder.textViewQnty.setText("" +  Math.round(product.getQuantity()));
        Double subtotal = product.getPrice() * product.getQuantity();
        holder.textViewSubtotal.setText("₹ " + (new DecimalFormat("##.##").format(subtotal)));


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewQnty, textViewSubtotal, textViewPrice, textViewDelete;
        LinearLayout header;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tv_prdct_name);
            textViewQnty = itemView.findViewById(R.id.tv_quantity);
            textViewSubtotal = itemView.findViewById(R.id.tv_subtotal);
            textViewPrice = itemView.findViewById(R.id.tv_cost);
            textViewDelete = itemView.findViewById(R.id.tv_delete);
            header=itemView.findViewById(R.id.billheader);
            // imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

