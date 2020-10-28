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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finwin.brahmagiri.fooddelivery.Activity.ProductListActivity;
import com.finwin.brahmagiri.fooddelivery.Responses.Outlet;
import com.finwin.brahmagiri.fooddelivery.Responses.PreviousSale;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.utilities.LocalPreferences;

import java.util.ArrayList;
import java.util.List;

public class OutletPagAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    Context context;
    private List<Outlet> datset;
    private List<Outlet> mResults;
    private boolean isLoadingAdded = false;

    public OutletPagAdapter(Context context) {
        this.context = context;
        mResults = new ArrayList<>();
    }



    public OutletPagAdapter(Context mainActivityContacts, List<Outlet> datset) {
        this.datset = datset;
        this.context = mainActivityContacts;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView Outletname, Adderss,Streetadderss,Tvtimingstatus,Tvdistance,TvMobile;
        RelativeLayout alphalayt;

        MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            Outletname = (TextView) view.findViewById(R.id.tvoutname);
            alphalayt = (RelativeLayout) view.findViewById(R.id.alphalayt);

            Adderss = (TextView) view.findViewById(R.id.tv_address);
            Streetadderss = (TextView) view.findViewById(R.id.tvstreet);
            Tvtimingstatus=(TextView)view.findViewById(R.id.tv_holiday);
            Tvdistance=(TextView)view.findViewById(R.id.tvdistance);
            TvMobile=(TextView)view.findViewById(R.id.mobile);
        }
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
        View v1 = inflater.inflate(R.layout.layout_outlet, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        switch (getItemViewType(position)) {
            case ITEM:
                final Outlet lists = mResults.get(position);

                final MyViewHolder holders = (MyViewHolder) holder;

                Glide.with(context).load(lists.getOutletImage()).placeholder(R.drawable.nooutlet).into(holders.image);

                String mobile = lists.getOutletMobile();
                if (mobile != null && !mobile.equals("")) {
                    holders.TvMobile.setText(mobile);
                } else {
                    holders.TvMobile.setVisibility(View.GONE);
                }

                if (lists.getHoliday_status()!=null&&!lists.getHoliday_status().equalsIgnoreCase("open")) {
                    holders.Tvtimingstatus.setVisibility(View.VISIBLE);
                    holders.alphalayt.setVisibility(View.VISIBLE);
                }else{
                    holders.Tvtimingstatus.setVisibility(View.GONE);
                  //  holders.image.setAlpha(0.1f);
                    holders.alphalayt.setVisibility(View.GONE);

                }
                holders.Tvdistance.setText("" + lists.getDistance() + " KM AWAY");
                holders.Outletname.setText(lists.getOutletName());
                holders.Adderss.setText(lists.getOutletAddress() + " " + lists.getOutletStreet());
                holders.Streetadderss.setText(lists.getOutletStreet());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lists.getHoliday_status().equalsIgnoreCase("open")) {
                            LocalPreferences.storeStringPreference(context, "cartoutid", String.valueOf(lists.getOutlet()));

                            context.startActivity(new Intent(context, ProductListActivity.class).putExtra("outletid", String.valueOf(lists.getOutlet()))
                                    .putExtra("outletmobile", lists.getOutletMobile()));

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
    public int getScreenWidth() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public void add(Outlet r) {
        mResults.add(r);
        notifyItemInserted(mResults.size() - 1);
    }

    public void addAll(List<Outlet> moveResults) {
        for (Outlet result : moveResults) {
            add(result);
        }
    }

    public void remove(Outlet r) {
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
        add(new Outlet());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mResults.size() - 1;
        Outlet result = getItem(position);

        if (result != null) {
            mResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Outlet getItem(int position) {
        return mResults.get(position);
    }




    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
