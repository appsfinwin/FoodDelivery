package com.finwin.brahmagiri.fooddelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityMain extends AppCompatActivity {

    private DrawerLayout drawer;

    Integer image[] = {R.drawable.food2, R.drawable.food1, R.drawable.food3, R.drawable.food4};
    String foodName[] = {"Beef items", "Pork items", "Chicken items", "Meat products"};
    String totalRest[] = {"fillet, steak etc.", "jowl,Spare ribs etc", "halal,Boneless etc.", "chicken,boneless.."};


    Integer foodImage[] = {R.drawable.food5, R.drawable.food6, R.drawable.food7, R.drawable.food5};
    String ratings[] = {"4.5", "4.2", "4.3", "4.5"};
    String restaurantName[] = {"Beef items", "Pork items", "Chicken", "Chicken products"};
    String restaurantCusines[] = {"Beef skeletal muscle,fillet mignon,sirloin steak,rump steak",
            "Pork belly,Pork jowl,Spare ribs",
            "Halal Cut,Boneless,Leg Meat",
            "Chicken,Breat,sliced boneless skinless"};
    String deliveryTime[] = {"20-30 min", "10-15 min", "40-45 min", "30-35 min"};
    String amount[] = {"300 Rs", "250 Rs", "280 Rs", "320 Rs"};
    String paymentMode[] = {"Online & COD", "Online & COD", "Online & COD", "Online & COD",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMain.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment fr = new FragHome();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fr);
        fragmentTransaction.commit();
    }

    //===================================================================================================
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void navClick(View view) {
        switch (view.getId()) {
            case R.id.tv_nav_home:
                Fragment frHome = new FragHome();
                FragmentManager fmHome = getSupportFragmentManager();
                FragmentTransaction fragHome = fmHome.beginTransaction();
                fragHome.replace(R.id.frame_layout, frHome);
                fragHome.addToBackStack(null);
                fragHome.commit();

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.tv_nav_cart:
                Fragment frCart = new FragCart();
                FragmentManager fmCart = getSupportFragmentManager();
                FragmentTransaction fragTransCart = fmCart.beginTransaction();
                fragTransCart.replace(R.id.frame_layout, frCart);
                fragTransCart.addToBackStack(null);
                fragTransCart.commit();

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.tv_nav_myordr:
                Fragment frMyOrder = new FragMyOrder();
                FragmentManager fmMyOrder = getSupportFragmentManager();
                FragmentTransaction fragMyOrder = fmMyOrder.beginTransaction();
                fragMyOrder.replace(R.id.frame_layout, frMyOrder);
                fragMyOrder.addToBackStack(null);
                fragMyOrder.commit();

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.tv_nav_profile:
                Fragment frMyProfile = new FragProfile();
                FragmentManager fmMyProfile = getSupportFragmentManager();
                FragmentTransaction fragMyProfile = fmMyProfile.beginTransaction();
                fragMyProfile.replace(R.id.frame_layout, frMyProfile);
                fragMyProfile.addToBackStack(null);
                fragMyProfile.commit();

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.tv_nav_logout:
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("\nLogout.!!")
                        .setContentText("Are you sure want to logout ?")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                startActivity(new Intent(ActivityMain.this, ActivityInitial.class));
                                finish();
                                sDialog.dismiss();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

            default:
        }
    }

//===================================================================================================


}
