package com.finwin.brahmagiri.fooddelivery.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.billdesk.sdk.LibraryPaymentStatusProtocol;

public class SampleCallBack implements LibraryPaymentStatusProtocol, Parcelable {
    String TAG = "Callback ::: > ";

    public SampleCallBack() {
        Log.v(TAG, "CallBack()....");
    }

    public SampleCallBack(Parcel in) {
        Log.v(TAG, "CallBack(Parcel in)....");
    }

    @Override
    public void paymentStatus(String status, Activity context) {
        Log.v(TAG,
                "paymentStatus(String status, Activity context)....::::status:::::"
                        + status);

        String mStatus[]=status.split("\\|");

        String authstatus=mStatus[14].toString();
        String tnxid=mStatus[1].toString();

        if (authstatus.equals("0300")){

           context. startActivity(new Intent(context, PaymentSuccessActivity.class).putExtra("trnxnid", tnxid).putExtra("paymode", "op"));
        }else  if (authstatus.equals("0399")){
           context. startActivity(new Intent(context, PaymentFailureActivity.class).putExtra("trnxnid", tnxid).putExtra("message", "Failed Transaction"));
        } else if (authstatus.equalsIgnoreCase("NA")){
            context. startActivity(new Intent(context, PaymentFailureActivity.class).putExtra("trnxnid", tnxid).putExtra("message", "Cancel Transaction"));

        }else if(authstatus.equalsIgnoreCase("0002")){
            context. startActivity(new Intent(context, PaymentFailureActivity.class).putExtra("trnxnid", tnxid).putExtra("message", "Pending Transaction"));

        }else if(authstatus.equalsIgnoreCase("0001”")){
            context. startActivity(new Intent(context, PaymentFailureActivity.class).putExtra("trnxnid", tnxid).putExtra("message", "Cancel Transaction"));

        }


      /* Intent mIntent = new Intent(context, StatusActivity.class);
        mIntent.putExtra("status", status);
        context.startActivity(mIntent);
        context.finish();*/
    }

    @Override
    public void tryAgain() {
        Log.d(TAG, "tryAgain() called");
    }

    @Override
    public void onError(Exception e) {
        Log.d(TAG, "onError() called with: e = [" + e + "]");
    }

    @Override
    public void cancelTransaction() {
        Log.d(TAG, "cancelTransaction() called");
    }

    @Override
    public int describeContents() {
        Log.v(TAG, "describeContents()....");
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v(TAG, "writeToParcel(Parcel dest, int flags)....");
        // TODO Auto-generated method stub
    }

    @SuppressWarnings("rawtypes")
    public static final Creator CREATOR = new Creator() {
        String TAG = "Callback --- Parcelable.Creator ::: > ";

        @Override
        public SampleCallBack createFromParcel(Parcel in) {
            Log.v(TAG, "CallBackActivity createFromParcel(Parcel in)....");
            return new SampleCallBack(in);
        }

        @Override
        public Object[] newArray(int size) {
            Log.v(TAG, "Object[] newArray(int size)....");
            return new SampleCallBack[size];
        }
    };
}

