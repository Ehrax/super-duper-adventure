package de.in.uulm.map.tinder.network;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

/**
 * Created by Jona on 15.06.2017.
 */

public class ServerRequest extends AuthRequest<byte[]> {

    private final byte[] mBody;

    private final Response.Listener<byte[]> mListener;

    public ServerRequest(int method,
                         String url,
                         @Nullable byte[] body,
                         Context context,
                         @Nullable Response.Listener<byte[]> listener,
                         @Nullable Response.ErrorListener errorListener) {

        super(method, url, context, errorListener);

        mListener = listener;
        mBody = body;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        return mBody;
    }

    @Override
    public String getBodyContentType() {

        return "application/json";
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        return Response.success(response.data, getCacheEntry());
    }

    @Override
    protected void deliverResponse(byte[] response) {

        if (mListener != null) {
            mListener.onResponse(response);
        }
    }
}
