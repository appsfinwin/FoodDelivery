package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.FragMenuTab;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.List;

public class MenuItemRecyAdapter extends RecyclerView.Adapter<MenuItemRecyAdapter.MyViewHolder> {

    Context context;
    private List<MenuItemModel> OfferList;

    public MenuItemRecyAdapter(Context mainActivityContacts, List<MenuItemModel> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView foodName, totalRest;

        MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            foodName = (TextView) view.findViewById(R.id.foodName);
            totalRest = (TextView) view.findViewById(R.id.restNo);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_recycler, parent, false);
        return new MyViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MenuItemModel lists = OfferList.get(position);
        holder.image.setImageResource(lists.getImage());
        holder.foodName.setText(lists.getFoodName());
        holder.totalRest.setText(lists.getTotalRes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemId = lists.getItemID();
//                Log.e("MenuItemRecyAdapter:",itemId);
                Bundle bundle = new Bundle();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                Fragment myFragment = new FragMenuTab();
                bundle.putString(ConstantClass.MENU_TPYE,itemId);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(
                        R.id.frame_layout, myFragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}
