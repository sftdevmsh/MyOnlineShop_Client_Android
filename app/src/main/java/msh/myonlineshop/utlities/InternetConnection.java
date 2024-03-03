package msh.myonlineshop.utlities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class InternetConnection {
    public static Boolean isNetworkAvailable(Application application) {
        Boolean bln ;
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) bln = false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            bln = (actNw != null
                    && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
//                        || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
//                        || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                    ));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            bln = (nwInfo != null
                    && (nwInfo.getType() == ConnectivityManager.TYPE_WIFI
                    || nwInfo.getType() == ConnectivityManager.TYPE_MOBILE
                    || nwInfo.getType() == ConnectivityManager.TYPE_VPN
//                    || nwInfo.getType() == ConnectivityManager.TYPE_ETHERNET
//                    || nwInfo.getType() == ConnectivityManager.TYPE_BLUETOOTH
                    ));

//            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//            if (networkInfo != null) {
//                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                    Log.d("INTERNET_CONNECTION","TYPE_WIFI");
//                    return true;
//                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                    Log.d("INTERNET_CONNECTION","TYPE_MOBILE");
//                    return true;
//                }
//            }
//            Log.d("INTERNET_CONNECTION","NO_CONNECTION");
        }
        return bln;
    }
}
