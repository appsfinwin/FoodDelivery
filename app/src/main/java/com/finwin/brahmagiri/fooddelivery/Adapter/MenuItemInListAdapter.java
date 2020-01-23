package com.finwin.brahmagiri.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.finwin.brahmagiri.fooddelivery.FragMenuTab;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ToCartButtonListener;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.H_CART_ITM_AMOUNT;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.H_CART_ITM_ID;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.H_CART_ITM_IMAGE;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.H_CART_ITM_NAME;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.H_CART_ITM_TYPE;
import static com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass.hMapCartItem;

public class MenuItemInListAdapter extends RecyclerView.Adapter<MenuItemInListAdapter.MyViewHolder> {
    Context context;
    private List<MenuItemInListModel> OfferList;
    //    public ArrayList<HashMap<String, String>> OfferList;
    ToCartButtonListener toCartBtnLstnr;

//    public MenuItemInListAdapter(Context mainActivityContacts, ArrayList<HashMap<String, String>> offerList,
//                                 ToCartButtonListener _toCartBtnLstnr) {

    public MenuItemInListAdapter(Context mainActivityContacts, List<MenuItemInListModel> offerList,
                                 ToCartButtonListener _toCartBtnLstnr) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
        this.toCartBtnLstnr = _toCartBtnLstnr;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemDescr;
        TextView tvItemAmnt;
        TextView btnAdd;
        PorterShapeImageView imgItem;
        ElegantNumberButton btnElgntCount;

        MyViewHolder(View view) {
            super(view);
//            hMapCartItem = new HashMap<String, Map<String, String>>();
            imgItem = view.findViewById(R.id.img_item);
            tvItemName = (TextView) view.findViewById(R.id.tv_itm_name);
            tvItemDescr = (TextView) view.findViewById(R.id.tv_itm_descr);
            tvItemAmnt = (TextView) view.findViewById(R.id.tv_itm_amnt);
            btnAdd = (TextView) view.findViewById(R.id.btn_add);
            btnElgntCount = (ElegantNumberButton) view.findViewById(R.id.btn_elgnt_count);
        }
    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

    @Override
    public MenuItemInListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item_recycle, parent, false);
        return new MenuItemInListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MenuItemInListModel lists = OfferList.get(position);
        holder.imgItem.setImageResource(lists.getImage());
        holder.tvItemName.setText(lists.getItemName());
        holder.tvItemDescr.setText(lists.getItemDescrptn());
        holder.tvItemAmnt.setText(lists.getItemAmount());

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
                    FragMenuTab.removeFromCart(lists.getMenuItemID());
                } else {
                    FragMenuTab.addToCart(lists.getMenuItemID(), lists.getItemName(), lists.getItemAmount(), newValue);
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
