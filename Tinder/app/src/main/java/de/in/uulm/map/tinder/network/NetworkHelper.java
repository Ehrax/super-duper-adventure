package de.in.uulm.map.tinder.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by maxka on 05.06.2017.
 */

public class NetworkHelper {

    /**
     * Use this method to check if we are connected to the internet or not.
     *
     * @param context
     * @return true if network is online, false if not or if the given
     * context was null
     */
    public static boolean isNetworkOnline(Context context) {

        if (context == null) {
            return false;
        }

        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        return false;
    }
}
