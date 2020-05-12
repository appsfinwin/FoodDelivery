package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.finwin.brahmagiri.fooddelivery.FragForYouItemView;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapCartItem;

public class ForYouListViewAdapter extends RecyclerView.Adapter<ForYouListViewAdapter.ViewHolder> {

    Context context;
    private ArrayList<ForYouListViewModel> itemListModelArrayList;
    boolean showingFirst = false;

    public ForYouListViewAdapter(Context context, ArrayList<ForYouListViewModel> _itemListModelArrayList) {
        this.context = context;
        this.itemListModelArrayList = _itemListModelArrayList;
    }

    @Override
    public int getItemCount() {
        return itemListModelArrayList.size();
    }

    @NonNull
    @Override
    public ForYouListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, imageLike;
        TextView tvOffer, tvPrice, tvName;
        TextView btnAdd;
        ElegantNumberButton btnElgntCount;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_bg);
            imageLike = itemView.findViewById(R.id.img_like);
            tvOffer = itemView.findViewById(R.id.tv_offer);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvName = itemView.findViewById(R.id.tvName);
            btnAdd = (TextView) itemView.findViewById(R.id.btn_add_ofr);
            btnElgntCount = (ElegantNumberButton) itemView.findViewById(R.id.btn_elgnt_count_ofr);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ForYouListViewAdapter.ViewHolder holder, int position) {
        final ForYouListViewModel lists = itemListModelArrayList.get(position);
        holder.image.setImageResource(itemListModelArrayList.get(position).getImage());
        holder.imageLike.setImageResource(itemListModelArrayList.get(position).getImageLike());
        holder.tvOffer.setText(itemListModelArrayList.get(position).getItemoffer());
        holder.tvPrice.setText(itemListModelArrayList.get(position).getItemPrice());
        holder.tvName.setText(itemListModelArrayList.get(position).getItemName());

        holder.imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("imageLike: ", String.valueOf(showingFirst));
                if (showingFirst) {
                    holder.imageLike.setBackgroundResource(R.drawable.ic_like_uncheck);
                    showingFirst = false;
                } else {
                    holder.imageLike.setImageResource(R.drawable.ic_like_check);
                    showingFirst = true;
                }
            }
        });

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
                if (newValue < 1) {
                    holder.btnAdd.setVisibility(View.VISIBLE);
                    FragForYouItemView.removeFromCart(lists.getItemID());
                } else {
                    FragForYouItemView.addToCart(lists.getItemID(), lists.getItemName(), lists.getItemPrice(), newValue);
                }

                Intent intent = new Intent("tocart_data");
                if (hMapCartItem == null || hMapCartItem.isEmpty()) {
                    intent.putExtra("data", "hide_btn");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    intent.putExtra("data", "show_btn");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });


    }


}
