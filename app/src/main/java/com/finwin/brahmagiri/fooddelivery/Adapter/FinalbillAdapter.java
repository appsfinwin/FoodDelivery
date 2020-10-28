
package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;
import com.finwin.brahmagiri.fooddelivery.Responses.Products;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.Onclick;

import java.util.List;

public class FinalbillAdapter extends RecyclerView.Adapter<FinalbillAdapter.ProductViewHolder> {

    Onclick mclick;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Products> productList;

    //getting the context and product list with constructor

    public FinalbillAdapter(Context mCtx, List<Products> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    public FinalbillAdapter(Context mCtx, List<Products> productList, Onclick mclick) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mclick = mclick;
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
        Products product = productList.get(position);
        if (position == 0) {
            holder.header.setVisibility(View.VISIBLE);
        } else {
            holder.header.setVisibility(View.GONE);
        }

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getProductName());
        holder.textViewPrice.setText("₹ " + String.format("%.02f", product.getPrice()));
        holder.textViewQnty.setText("" + product.getQuantity());
        Double subtotal = product.getPrice() * product.getQuantity();
        String total = String.format("%.02f", subtotal);
        holder.textViewSubtotal.setText("₹ " + total);
        //holder.textViewRating.setText(String.valueOf(product.getRating()));
        // holder.textViewPrice.setText(String.valueOf(product.getPrice()));

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

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
            header = itemView.findViewById(R.id.billheader);

            // imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

