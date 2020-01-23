package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finwin.brahmagiri.fooddelivery.FragForYouItemView;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.List;

public class FoodForYouRecyAdapter extends RecyclerView.Adapter<FoodForYouRecyAdapter.MyViewHolder> {
    Context context;
    private List<FoodForYouModel> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView ratings, restaurantName, restaurantCusine, deliveryTime, amount, paymentMode;

        public MyViewHolder(View view) {
            super(view);
            foodImage = (ImageView) view.findViewById(R.id.foodImage);
            ratings = (TextView) view.findViewById(R.id.ratings);
            restaurantName = (TextView) view.findViewById(R.id.restaurantName);
            restaurantCusine = (TextView) view.findViewById(R.id.restaurantCuisine);
            deliveryTime = (TextView) view.findViewById(R.id.deliveryTime);
            amount = (TextView) view.findViewById(R.id.amount);
            paymentMode = (TextView) view.findViewById(R.id.paymentMode);
        }
    }

    public FoodForYouRecyAdapter(Context mainActivityContacts, List<FoodForYouModel> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public FoodForYouRecyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_recycler, parent, false);
        return new FoodForYouRecyAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final FoodForYouRecyAdapter.MyViewHolder holder, int position) {
        FoodForYouModel lists = OfferList.get(position);
        holder.foodImage.setImageResource(lists.getFoodImage());
        holder.ratings.setText(lists.getRatings());
        holder.restaurantName.setText(lists.getRestaurantName());
        holder.restaurantCusine.setText(lists.getRestaurantCuisine());
        holder.amount.setText(lists.getAmount());
        holder.paymentMode.setText(lists.getPaymentMode());
        holder.deliveryTime.setText(lists.getDliveryTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                Log.e("onClick: ", "oooooooo");
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new FragForYouItemView();
//                    bundle.putString(mstrType, "MOB");
//                    myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(
                        R.id.frame_layout,
                        myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}
