package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.finwin.brahmagiri.fooddelivery.Activity.Product;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.database.DatabaseHandler;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;

import java.util.List;

public class ItemlistingBrahmaAdapter extends RecyclerView.Adapter<ItemlistingBrahmaAdapter.MyViewHolder> {
    Context context;
    private List<Product> dataset;
    showhide mshowhide;
    DatabaseHandler db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image, imageLike;
        TextView tvOffer, tvPrice, tvName,cutprice,tv_description;
        TextView btnAdd;
        ElegantNumberButton btnElgntCount;

        public MyViewHolder(View view) {
            super(view);
            image = itemView.findViewById(R.id.img_bg);
            imageLike = itemView.findViewById(R.id.img_like);
            tvOffer = itemView.findViewById(R.id.tv_offer);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvName = itemView.findViewById(R.id.tvName);
            tv_description=itemView.findViewById(R.id.tvdesc);
            cutprice=itemView.findViewById(R.id.textcutprice);
            btnAdd = (TextView) itemView.findViewById(R.id.btn_add_ofr);
            btnElgntCount = (ElegantNumberButton) itemView.findViewById(R.id.btn_elgnt_count_ofr);
        }
    }

    public ItemlistingBrahmaAdapter(Context context, List<Product> dataset, showhide mshowhide) {
        this.dataset = dataset;
        this.context = context;
        this.mshowhide=mshowhide;
        db=new DatabaseHandler(context);

    }

    @Override
    public ItemlistingBrahmaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_view, parent, false);
        return new ItemlistingBrahmaAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ItemlistingBrahmaAdapter.MyViewHolder holder, final int position) {
        //  holder1.image.setImageResource(result.get(position).getImage());
        Glide.with(context)
                .load("https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                .placeholder(R.drawable.placeholder)
                .into(holder.image);

        Product result=dataset.get(position);
       /* String addedq=   db.getFromDb(result.getProductId().toString());
        if(!addedq.equals("0")){
            holder.btnAdd.setVisibility(View.INVISIBLE);
            holder.btnElgntCount.setNumber(addedq, true);
            mshowhide.show("show");
        }
      //  holder.btnElgntCount.setNumber(addedq, true);
        Log.d("fetchquantity", ": "+addedq);*/

       // Glide.with(context).load("https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(holder.image);
        //  itemVH.imageLike.setImageResource(result.get(position).getImageLike());
        //  itemVH.tvOffer.setText(itemListModelArrayList.get(position).getItemoffer());
        holder.tvPrice.setText(""+result.getPrice());
        holder.tvName.setText(result.getProductName());
        holder.tv_description.setText("Available Quantity :"+result.getAvlQty());
      // holder.tv_description.setText(result.get);
      //  holder.cutprice.setText(""+result.getMRP());

        /*int mcartvalue=result.getCartValue();
        if (mcartvalue!=0){
            holder.btnAdd.setVisibility(View.INVISIBLE);
            itemVH.btnElgntCount.setNumber(String.valueOf(mcartvalue), true);
            mshowhide.show("show");


        }*/

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnAdd.setVisibility(View.INVISIBLE);
                holder.btnElgntCount.setNumber("1", true);

            }
        });
        int avq=(int) Math.round(dataset.get(position).getAvlQty());  ;
        holder.btnElgntCount.setRange(0,avq);
        holder.btnElgntCount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
           Log.e("Count", "onValueChange: "+newValue );
                Log.e("Count", "onValueChange: "+oldValue );
                LocalPreferences.storeStringPreference(context,"cartoutid",dataset.get(position).getOutId());
             String itemcode=dataset.get(position).getProductId().toString();
                String price=dataset.get(position).getOutId().toString();
                String pname=dataset.get(position).getProductName().toString();
                Double avq=dataset.get(position).getAvlQty();
                if (newValue>avq){
                    Toast.makeText(context, "Quantity not available", Toast.LENGTH_SHORT).show();

                }else{
                    mshowhide.clickedpdct(newValue,itemcode,pname,price);

                }






                if (newValue < 1) {
                    holder.btnAdd.setVisibility(View.VISIBLE);
                    //FragForYouItemView.removeFromCart(lists.getItemID());
                } else {
                    // FragForYouItemView.addToCart(lists.getItemID(), lists.getItemName(), lists.getItemPrice(), newValue);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
