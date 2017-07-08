package de.in.uulm.map.tinder.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Jona on 28.06.17.
 */

public class DefaultErrorListener implements Response.ErrorListener {

    Context mContext;

    public DefaultErrorListener(Context context) {

        mContext = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        String msg;
        if (error.networkResponse != null &&
                error.networkResponse.data != null) {
            msg = new String(error.networkResponse.data);
        } else {
            msg = error.getMessage();
        }

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
