package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.Table;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;

import java.util.ArrayList;
import java.util.List;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapCartItem;

public class BestSellingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
 public static final int ITEM = 0;
    public static final int LOADING = 1;
    Context context;
    private List<Table> dataset;
    boolean showingFirst = false;
    private boolean isLoadingAdded = false;
    showhide mshowhide;


    public BestSellingAdapter(Context context, ArrayList<Table> dataset) {
        this.context = context;
        this.dataset = dataset;
    }
    public BestSellingAdapter(Context context,showhide mshowhide) {
        this.context = context;
        dataset=new ArrayList<>();
        this.mshowhide=mshowhide;

    }
    public List<Table> getItem() {
        return dataset;
    }
    public void setItem(List<Table> datasets) {
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
        View v1 = inflater.inflate(R.layout.item_list_view, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        Table result = dataset.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final ViewHolder itemVH = (ViewHolder) holder;
           //  holder1.image.setImageResource(result.get(position).getImage());
                Glide.with(context).load("https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(itemVH.image);
              //  itemVH.imageLike.setImageResource(result.get(position).getImageLike());
              //  itemVH.tvOffer.setText(itemListModelArrayList.get(position).getItemoffer());
                itemVH.tvPrice.setText(""+result.getSellingRate());
                itemVH.cutprice.setText(""+result.getMRP());
                itemVH.tvName.setText(result.getItemName());
                itemVH.tv_description.setText(result.getImageDescription());
                int mcartvalue=result.getCartValue();
                if (mcartvalue!=0){
                    itemVH.btnAdd.setVisibility(View.INVISIBLE);
                    itemVH.btnElgntCount.setNumber(String.valueOf(mcartvalue), true);
                    mshowhide.show("show");


                }

                itemVH.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemVH.btnAdd.setVisibility(View.INVISIBLE);
                        itemVH.btnElgntCount.setNumber("1", true);
                    }
                });
                itemVH.btnElgntCount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        Log.e("Count", "onValueChange: "+newValue );
                        Log.e("Count", "onValueChange: "+oldValue );
                        String itemcode=dataset.get(position).getItemCode();


                        mshowhide.clicked(newValue,itemcode);

                        if (newValue < 1) {
                            itemVH.btnAdd.setVisibility(View.VISIBLE);
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
        ImageView image, imageLike;
        TextView tvOffer, tvPrice, tvName,cutprice,tv_description;
        TextView btnAdd;
        ElegantNumberButton btnElgntCount;

        public ViewHolder(View itemView) {
            super(itemView);
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

    public void add(Table r) {
        dataset.add(r);
        notifyItemInserted(dataset.size() - 1);
    }


    public void addAll(List<Table> moveResults) {
        for (Table result : moveResults) {
            add(result);
        }
    }
    public void remove(Table r) {
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
        add(new Table());
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataset.size() - 1;
        Table result = getItem(position);

        if (result != null) {
            dataset.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Table getItem(int position) {
        return dataset.get(position);
    }
    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
