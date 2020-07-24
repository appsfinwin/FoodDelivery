package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class BaseActivity extends Activity {
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }
    public void showdialog(){
        if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }
    public void dismissdialog(){
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
