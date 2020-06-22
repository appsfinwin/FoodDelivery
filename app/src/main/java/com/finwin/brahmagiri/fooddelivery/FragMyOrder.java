package com.finwin.brahmagiri.fooddelivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.finwin.brahmagiri.fooddelivery.Adapter.MyOrderAdapter;
import com.finwin.brahmagiri.fooddelivery.Adapter.MyOrderModel;
import com.finwin.brahmagiri.fooddelivery.Responses.PreviousSale;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseMyOrder;
import com.finwin.brahmagiri.fooddelivery.SupportClass.ConstantClass;
import com.finwin.brahmagiri.fooddelivery.Utilities.LocalPreferences;
import com.finwin.brahmagiri.fooddelivery.WebService.APIClient;
import com.finwin.brahmagiri.fooddelivery.WebService.ApiService;
import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragMyOrder extends Fragment {


    private RecyclerView menuRecycler;
    private MyOrderAdapter bAdapter;
    FrameLayout frameLayout;


    ImageButton ibtn_back;
    View rootview;
    TextView tvOK;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootview = inflater.inflate(R.layout.my_orders_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = rootview.findViewById(R.id.emptyorder);
        menuRecycler = (RecyclerView) rootview.findViewById(R.id.menuRecycler);
      LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        menuRecycler.setLayoutManager(layoutManager);
     //   menuRecycler.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuRecycler.getContext(),
                layoutManager.getOrientation());
        menuRecycler.addItemDecoration(dividerItemDecoration);
        String partnerid = LocalPreferences.retrieveStringPreferences(getActivity(), "partnerid");
        String mAccesstoken = LocalPreferences.retrieveStringPreferences(getActivity(), "Accesstoken");

        ApiService apiService = APIClient.getClient().create(ApiService.class);
        Call<ResponseMyOrder> call = apiService.doFetchMyOrder(mAccesstoken, "test", partnerid);
        call.enqueue(new Callback<ResponseMyOrder>() {
            @Override
            public void onResponse(Call<ResponseMyOrder> call, Response<ResponseMyOrder> response) {
                if (response.body() != null && response.code() == 200) {
                    ResponseMyOrder responseMyOrder = response.body();
                    List<PreviousSale> dataset = responseMyOrder.getPreviousSales();
                    bAdapter = new MyOrderAdapter(getContext(), dataset);
                    menuRecycler.setAdapter(bAdapter);
                    if (bAdapter.getItemCount() == 0) {
                        frameLayout.setVisibility(View.VISIBLE);
                        menuRecycler.setVisibility(View.GONE);
                    }
                    frameLayout.setVisibility(View.GONE);
                    menuRecycler.setVisibility(View.VISIBLE);


                }else{
                    Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseMyOrder> call, Throwable t) {

            }
        });

//        for (int i = 0; i < foodName.length; i++) {
//            MyOrderModel beanClassForRecyclerView_contacts = new MyOrderModel(foodName[i], quantity[i], rupees[i]);
//            homeListModelClassArrayList1.add(beanClassForRecyclerView_contacts);
//        }


        ibtn_back = rootview.findViewById(R.id.ibtn_back_mordr);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

    }

    public void clearStack() {
//        //Here we are clearing back stack fragment entries
//        int backStackEntry = Objects.requireNonNull(getFragmentManager()).getBackStackEntryCount();
//        if (backStackEntry > 0) {
//            for (int i = 0; i < backStackEntry; i++) {
//                getFragmentManager().popBackStackImmediate();
//            }
//        }

        //Here we are removing all the fragment that are shown here
        if (getFragmentManager().getFragments() != null && getFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }

        Fragment fr = new FragHome();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fr);
        fragmentTransaction.commit();
    }

}
