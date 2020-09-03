package com.finwin.brahmagiri.fooddelivery.utilities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtility {
    private static final int MY_PERMISSIONS_CONSTANT = 12356;
    private Activity activity;
    private static final float BITMAP_SCALE = 1f;
    private static final float BLUR_RADIUS = 25f;

    public AppUtility(Activity activity)
    {
        this.activity = activity;
    }

    //Internet checking
    public boolean checkInternet() {
        if (isInternetConnected())
            return true;
        else {
          //  customToast(activity.getResources().getString(R.string.no_internet));
            return false;
        }
    }

    private boolean isInternetConnected() {



        ConnectivityManager conMgr = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = conMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        /** to get info of mobile N/W : */
        final android.net.NetworkInfo mobile = conMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi.isAvailable() && wifi.isConnected())
                || (mobile.isAvailable() && mobile.isConnected())) {
            return true;
        } else if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
        /*
        ConnectivityManager connectivityManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager != null) {
                Network[] networks = connectivityManager.getAllNetworks();
                NetworkInfo networkInfo;
                for (Network mNetwork : networks) {
                    networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        return true;
                    }
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;*/
    }

  /*  public void customToast(String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_red_background,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.toast_text);
        text.setText(message);
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
*/

    //Mobile or Phone number validation
    public boolean isValidMobile(String phone) {
        return !phone.trim().equals("") && Pattern.matches("\\d+", phone) && !(phone.length() < 10 || phone.length() > 13);
    }

    //email address validation
    public boolean isValidMail(String email) {
        if (email.trim().equals(""))
            return false;
        Pattern p;
        Matcher m;
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);
        m = p.matcher(email);
        return m.matches();
    }






}
