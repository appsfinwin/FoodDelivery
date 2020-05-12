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

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.finwin.brahmagiri.fooddelivery.Activity.ItemListingActivity;
import com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category.ItemCat;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.Table;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.List;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapCartItem;

public class AllCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
 public static final int ITEM = 0;
    public static final int LOADING = 1;
    Context context;
    private List<ItemCat> dataset;
    boolean showingFirst = false;
    private boolean isLoadingAdded = false;


    public AllCategoryAdapter(Context context, ArrayList<ItemCat> dataset) {
        this.context = context;
        this.dataset = dataset;
    }
    public AllCategoryAdapter(Context context) {
        this.context = context;
        dataset=new ArrayList<>();

    }
    public List<ItemCat> getItem() {
        return dataset;
    }
    public void setItem(List<ItemCat> datasets) {
        this.dataset = datasets;
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
        View v1 = inflater.inflate(R.layout.food_item_recycler, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ItemCat result = dataset.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final ViewHolder itemVH = (ViewHolder) holder;
                itemVH.foodName.setText(result.getCatName());
                itemVH.totalRest.setText(result.getDecrip());
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
                        context.startActivity(new Intent(context, ItemListingActivity.class).putExtra("cat_id",result.getCatId().toString()));

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
        return dataset == null ? 0 : dataset.size();
    }
    @Override
    public int getItemViewType(int position) {
        return (position == dataset.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

   /* @NonNull
    @Override
    public BestSellingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view, viewGroup, false);
        return new ViewHolder(view);
    }
*/
    public class ViewHolder extends RecyclerView.ViewHolder {
       ImageView image;
       TextView foodName, totalRest;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            foodName = (TextView) itemView.findViewById(R.id.foodName);
            totalRest = (TextView) itemView.findViewById(R.id.restNo);
        }
    }

  /*  @Override
    public void onBindViewHolder(@NonNull final BestSellingAdapter.ViewHolder holder, int position) {
//        final ForYouListViewModel lists = itemListModelArrayList.get(position);
      //  holder.image.setImageResource(itemListModelArrayList.get(position).getImage());
     //   holder.imageLike.setImageResource(itemListModelArrayList.get(position).getImageLike());
      //  holder.tvOffer.setText(itemListModelArrayList.get(position).getItemoffer());
      //  holder.tvPrice.setText(itemListModelArrayList.get(position).getItemPrice());
      //  holder.tvName.setText(itemListModelArrayList.get(position).getItemName());

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
                Log.e("Count", "onValueChange: "+newValue );

                if (newValue < 1) {
                    holder.btnAdd.setVisibility(View.VISIBLE);
                    //FragForYouItemView.removeFromCart(lists.getItemID());
                } else {
                   // FragForYouItemView.addToCart(lists.getItemID(), lists.getItemName(), lists.getItemPrice(), newValue);
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


    }*/

    public void add(ItemCat r) {
        dataset.add(r);
        notifyItemInserted(dataset.size() - 1);
    }


    public void addAll(List<ItemCat> moveResults) {
        for (ItemCat result : moveResults) {
            add(result);
        }
    }
    public void remove(ItemCat r) {
        int position = dataset.indexOf(r);
        if (position > -1) {
            dataset.remove(position);
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
        add(new ItemCat());
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataset.size() - 1;
        ItemCat result = getItem(position);

        if (result != null) {
            dataset.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ItemCat getItem(int position) {
        return dataset.get(position);
    }
    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
