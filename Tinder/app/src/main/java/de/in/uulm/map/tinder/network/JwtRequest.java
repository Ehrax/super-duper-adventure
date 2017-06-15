package de.in.uulm.map.tinder.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jona on 15.06.2017.
 */

public class JwtRequest extends Request<byte[]> {

    private final String mToken;

    private final Response.Listener<byte[]> mListener;

    public JwtRequest(int method,
                      String url,
                      String token,
                      Response.Listener<byte[]> listener,
                      Response.ErrorListener errorListener) {

        super(method, url, errorListener);

        mToken = token;
        mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> mHeaders = new HashMap<>();
        mHeaders.put("Authorization", "Bearer " + mToken);

        return mHeaders;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        return Response.success(response.data, getCacheEntry());
    }

    @Override
    protected void deliverResponse(byte[] response) {

        mListener.onResponse(response);
    }

    public static void showErrorToast(Context context, VolleyError error) {

        String msg;

        if (error.networkResponse != null && error.networkResponse.data != null) {
            msg = new String(error.networkResponse.data);
        } else {
            msg = error.getMessage();
        }

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
