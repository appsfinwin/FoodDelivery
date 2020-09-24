package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finwin.brahmagiri.fooddelivery.Activity.ItemListingActivity;
import com.finwin.brahmagiri.fooddelivery.Activity.ProductListActivity;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.HomePageCat;
import com.finwin.brahmagiri.fooddelivery.Responses.Outlet;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;
import com.github.siyamed.shapeimageview.ShapeImageView;

import java.util.List;

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.MyViewHolder> {

    Context context;
    private List<Outlet> datset;

    public OutletAdapter(Context mainActivityContacts, List<Outlet> datset) {
        this.datset = datset;
        this.context = mainActivityContacts;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView Outletname, Adderss,Streetadderss,Tvtimingstatus,Tvdistance,TvMobile;

        MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            Outletname = (TextView) view.findViewById(R.id.tvoutname);
            Adderss = (TextView) view.findViewById(R.id.tv_address);
            Streetadderss = (TextView) view.findViewById(R.id.tvstreet);
            Tvtimingstatus=(TextView)view.findViewById(R.id.tv_holiday);
            Tvdistance=(TextView)view.findViewById(R.id.tvdistance);
            TvMobile=(TextView)view.findViewById(R.id.mobile);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_outlet, parent, false);
    //    itemView.getLayoutParams().width = (int) (getScreenWidth() / 4); /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS



        return new MyViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Outlet lists = datset.get(position);
        Glide.with(context).load(lists.getOutletImage()).placeholder(R.drawable.nooutlet).into(holder.image);

        String mobile=lists.getOutletMobile();
        if (mobile!=null&&!mobile.equals("")){
            holder.TvMobile.setText(mobile);
        }else{
            holder.TvMobile.setVisibility(View.GONE);
        }

        if (!lists.getHoliday_status().equalsIgnoreCase("open")){
            holder.Tvtimingstatus.setVisibility(View.VISIBLE);
            holder.image.setAlpha(.3f);
        }
        holder.Tvdistance.setText(""+lists.getDistance()+" KM AWAY");
        holder.Outletname.setText(lists.getOutletName());
        holder.Adderss.setText(lists.getOutletAddress()+" "+lists.getOutletStreet());
        holder.Streetadderss.setText(lists.getOutletStreet());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lists.getHoliday_status().equalsIgnoreCase("open")){
                    LocalPreferences.storeStringPreference(context, "cartoutid", String.valueOf(lists.getOutlet()));

                    context.startActivity(new Intent(context, ProductListActivity.class).putExtra("outletid",String.valueOf(lists.getOutlet()))
                    .putExtra("outletmobile",lists.getOutletMobile()));

                }

               /* String itemId = lists.getItemID();
//                Log.e("MenuItemRecyAdapter:",itemId);
                Bundle bundle = new Bundle();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                Fragment myFragment = new FragMenuTab();
                bundle.putString(ConstantClass.MENU_TPYE,itemId);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(
                        R.id.frame_layout, myFragment).addToBackStack(null).commit();*/

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
