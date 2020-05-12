package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.finwin.brahmagiri.fooddelivery.FragForYouItemView;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.HomeTopselling;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;

import java.util.List;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapCartItem;

public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingAdapter.MyViewHolder> {
    Context context;
    private List<HomeTopselling> dataset;
    showhide mshowhide;

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

    public TopSellingAdapter(Context context, List<HomeTopselling> dataset,showhide mshowhide) {
        this.dataset = dataset;
        this.context = context;
        this.mshowhide=mshowhide;
    }

    @Override
    public TopSellingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_view, parent, false);
        return new TopSellingAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final TopSellingAdapter.MyViewHolder holder, final int position) {
        //  holder1.image.setImageResource(result.get(position).getImage());
        HomeTopselling result=dataset.get(position);
        Glide.with(context).load("https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(holder.image);
        //  itemVH.imageLike.setImageResource(result.get(position).getImageLike());
        //  itemVH.tvOffer.setText(itemListModelArrayList.get(position).getItemoffer());
        holder.tvPrice.setText(""+result.getSellingRate());
        holder.tvName.setText(result.getItemName());
        holder.tv_description.setText(result.getImageDescription());
        holder.cutprice.setText(""+result.getMRP());

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
        holder.btnElgntCount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.e("Count", "onValueChange: "+newValue );
                Log.e("Count", "onValueChange: "+oldValue );
                String itemcode=dataset.get(position).getItemCode();


                mshowhide.clicked(newValue,itemcode);

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
