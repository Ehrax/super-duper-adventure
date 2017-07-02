package de.in.uulm.map.tinder.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import de.in.uulm.map.tinder.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jona on 28.06.17.
 */

public abstract class AuthRequest<T> extends Request<T> {

    private final String mToken;

    public AuthRequest(int method,
                       String url,
                       Context context,
                       Response.ErrorListener listener) {

        super(method, url, listener);

        SharedPreferences accountPrefs = context.getSharedPreferences(
                context.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        mToken = accountPrefs.getString(
                context.getString(R.string.store_token), "");
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> mHeaders = new HashMap<>();
        mHeaders.put("Authorization", "Bearer " + mToken);

        return mHeaders;
    }
}
