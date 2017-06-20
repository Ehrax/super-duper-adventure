package de.in.uulm.map.tinder.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import de.in.uulm.map.tinder.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jona on 15.06.2017.
 */

public class ServerRequest extends Request<byte[]> {

    private final Context mContext;

    private final byte[] mBody;

    private final String mToken;

    private final String mUserName;

    private final Response.Listener<byte[]> mListener;

    private final Response.ErrorListener mErrorListener;

    /**
     * This is a little dirty hack to define a default error listener in a
     * static way which can access the non-static mContext. This object
     * functions just as a placeholder and is replaced in the constructor by
     * a non-static implementation, which can then access mContext.
     */
    public final static Response.ErrorListener DEFAULT_ERROR_LISTENER =
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) { }
            };

    public ServerRequest(int method,
                         String url,
                         @Nullable byte[] body,
                         Context context,
                         @Nullable Response.Listener<byte[]> listener,
                         @Nullable Response.ErrorListener errorListener) {

        super(method, url, errorListener);

        mContext = context;
        mListener = listener;
        mBody = body;

        SharedPreferences accountPrefs = mContext.getSharedPreferences(
                mContext.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        mToken = accountPrefs.getString(
                mContext.getString(R.string.store_token), "");
        mUserName = accountPrefs.getString(
                mContext.getString(R.string.store_username), "");

        if (errorListener == DEFAULT_ERROR_LISTENER) {
            mErrorListener = new Response.ErrorListener() {
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
            };
        } else {
            mErrorListener = errorListener;
        }
    }

    @Override
    public Response.ErrorListener getErrorListener() {

        return mErrorListener;
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

        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    public String getRequestingUsersName() {

        return mUserName;
    }
}
