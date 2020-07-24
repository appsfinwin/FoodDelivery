package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.finwin.brahmagiri.fooddelivery.Responses.CartItem;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ToCartButtonListener;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.finwin.brahmagiri.fooddelivery.interfaces.showhide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    private List<CartItem> dataset;
    showhide mshowhide;

    ToCartButtonListener toCartBtnLstnr;
    boolean showquantityupdate;


    public CartAdapter(Context mainActivityContacts, List<CartItem> dataset, showhide mshowhide, boolean showquantityupdate) {
        this.mshowhide = mshowhide;
        this.context = mainActivityContacts;
        this.dataset = dataset;
        this.showquantityupdate = showquantityupdate;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemAmount;
        TextView tvItemCount;
        ImageView imgMinus;
        ImageView imgAdd;
        ImageView imgDelete, ItemImage;

        MyViewHolder(View view) {
            super(view);
            tvItemName = (TextView) view.findViewById(R.id.tv_itmname_cart);
            tvItemAmount = (TextView) view.findViewById(R.id.tv_amnt_cart);
            tvItemCount = (TextView) view.findViewById(R.id.tv_count_cart);
            imgMinus = (ImageView) view.findViewById(R.id.img_cart_minus);
            imgAdd = (ImageView) view.findViewById(R.id.img_cart_add);
            imgDelete = (ImageView) view.findViewById(R.id.img_delete);
            ItemImage = view.findViewById(R.id.product_image);
            if (!showquantityupdate) {
                imgMinus.setVisibility(View.GONE);
                imgAdd.setVisibility(View.GONE);
                imgDelete.setVisibility(View.VISIBLE);

            }else{
                imgMinus.setVisibility(View.GONE);
                imgAdd.setVisibility(View.GONE);
                imgDelete.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_itm_rcylr, parent, false);
        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
        final CartItem lists = dataset.get(position);
        holder.tvItemName.setText(""+lists.getProductName());


        Glide.with(context)
                .load(lists.getProduct_image_url())
                .placeholder(R.drawable.noimage)
                .into(holder.ItemImage);

       int cnt = lists.getQuantity();
       holder.tvItemCount.setText("Qty : "+String.valueOf(cnt));
        // Glide.with(context).load(lists.getImageUrl()).into(holder.ItemImage);

      holder.tvItemAmount.setText("₹ " + lists.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
////                Bundle bundle = new Bundle();
//                Log.e("onClick: ", "oooooooo");
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new FragConfirmOrder();
////              bundle.putString(mstrType, "MOB");
////              myFragment.setArguments(bundle);
//                activity.getSupportFragmentManager().beginTransaction().replace(
//                        R.id.frame_layout,
//                        myFragment).addToBackStack(null).commit();

//                Log.e("addRemitncAmnt: ", String.valueOf(addRemitncAmnt()));
//                Log.e("setTotalItemAmount: ", String.valueOf(setTotalItemAmount()));
            }
        });
       // int avq=(int) Math.round(dataset.get(position).getAv);  ;

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  OfferList.remove(position);
                ConstantClass.hMapCartItem.remove(lists.getItemID());
                toCartBtnLstnr.calcSubTotal();
                notifyDataSetChanged();*/
                mshowhide.delete(String.valueOf(dataset.get(position).getProductId()));
                dataset.remove(position);
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.tvItemCount.getText().toString());
                count = count + 1;
                holder.tvItemCount.setText(String.valueOf(count));
                mshowhide.clicked(count, String.valueOf(dataset.get(position).getProductId()));


               /* lists.setItemCount(String.valueOf(Integer.parseInt(lists.getItemCount()) + 1));

                int cnt = Integer.parseInt(lists.getItemCount());
                holder.tvItemCount.setText(String.valueOf(cnt));

                double amnt = Double.parseDouble(lists.getItemAmount());
                double total = cnt * amnt;
                holder.tvItemAmount.setText(String.valueOf(total));

                Map<String, String> map = ConstantClass.hMapCartItem.get(lists.getItemID());
                Objects.requireNonNull(map).put(ConstantClass.H_ITEM_AMNT, String.valueOf(total));
//                Log.e("map.get:",map.get(ConstantClass.H_ITEM_AMNT) );

                toCartBtnLstnr.calcSubTotal();*/

//               Log.e("onClick: ", String.valueOf(((Map) ConstantClass.hMapCartItem.get(lists.getItemID())).get(ConstantClass.H_ITEM_AMNT)));
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.tvItemCount.getText().toString());
                if (count >= 1) {
                    count = count - 1;

                    if (count > 0) {
                        holder.tvItemCount.setText(String.valueOf(count));
                        mshowhide.clicked(count, String.valueOf(dataset.get(position).getProductId()));
                    } else {
                        mshowhide.delete(String .valueOf(dataset.get(position).getProductId()));

                    }


                }


//                Log.e("onClick: imgMinus",lists.getItemCount() );
              /*  if (Integer.parseInt(lists.getItemCount()) > 1) {
                    lists.setItemCount(String.valueOf(Integer.parseInt(lists.getItemCount()) - 1));

                    int cnt = Integer.parseInt(lists.getItemCount());
                    holder.tvItemCount.setText(String.valueOf(cnt));
                    double amnt = Double.parseDouble(lists.getItemAmount());
                    double total = cnt * amnt;
                    holder.tvItemAmount.setText(String.valueOf(total));

                    Map<String, String> map = ConstantClass.hMapCartItem.get(lists.getItemID());
                    Objects.requireNonNull(map).put(ConstantClass.H_ITEM_AMNT, String.valueOf(total));

                    toCartBtnLstnr.calcSubTotal();
                }*/
            }
        });

//        Log.e("onBindViewHolder:", "RUNNNNN........!!!");
    }


}

