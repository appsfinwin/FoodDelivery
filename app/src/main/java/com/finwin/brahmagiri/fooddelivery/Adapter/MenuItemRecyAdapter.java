package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.finwin.brahmagiri.fooddelivery.Activity.ItemListingActivity;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.HomePageCat;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.List;

public class MenuItemRecyAdapter extends RecyclerView.Adapter<MenuItemRecyAdapter.MyViewHolder> {

    Context context;
    private List<HomePageCat> datset;

    public MenuItemRecyAdapter(Context mainActivityContacts, List<HomePageCat> datset) {
        this.datset = datset;
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
        itemView.getLayoutParams().width = (int) (getScreenWidth() / 4); /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS



        return new MyViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final HomePageCat lists = datset.get(position);
        Glide.with(context).load(lists.getImage()).into(holder.image);
        holder.foodName.setText(lists.getCatName());
        holder.totalRest.setText(lists.getDecrip());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* String itemId = lists.getItemID();
//                Log.e("MenuItemRecyAdapter:",itemId);
                Bundle bundle = new Bundle();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                Fragment myFragment = new FragMenuTab();
                bundle.putString(ConstantClass.MENU_TPYE,itemId);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(
                        R.id.frame_layout, myFragment).addToBackStack(null).commit();*/
               context.startActivity(new Intent(context, ItemListingActivity.class).putExtra("cat_id",lists.getCatId().toString()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return datset.size();
    }
    public int getScreenWidth() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

}
