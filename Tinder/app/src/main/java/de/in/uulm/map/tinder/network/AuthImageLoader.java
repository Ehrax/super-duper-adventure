package de.in.uulm.map.tinder.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;

import de.in.uulm.map.tinder.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jona on 28.06.17.
 */

public class AuthImageLoader extends ImageLoader {

    String mToken;

    String mUserName;

    public AuthImageLoader(RequestQueue queue, ImageCache imageCache, Context context) {

        super(queue, imageCache);


        SharedPreferences accountPrefs = context.getSharedPreferences(
                context.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        mToken = accountPrefs.getString(
                context.getString(R.string.store_token), "");
        mUserName = accountPrefs.getString(
                context.getString(R.string.store_username), "");
    }

    /**
     * Slightly modified this method, to add custom auth headers.
     * Nearly identical to the version found in the Volley source (line 250):
     *
     * https://android.googlesource.com/platform/frameworks/volley/+/master/src/
     * main/java/com/android/volley/toolbox/ImageLoader.java
     */
    @Override
    protected Request<Bitmap> makeImageRequest(String requestUrl,
                                               int maxWidth,
                                               int maxHeight,
                                               ImageView.ScaleType scaleType,
                                               final String cacheKey) {

        return new ImageRequest(requestUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                onGetImageSuccess(cacheKey, response);
            }
        }, maxWidth, maxHeight, scaleType, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onGetImageError(cacheKey, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> mHeaders = new HashMap<>();
                mHeaders.put("Authorization", "Bearer " + mToken);

                return mHeaders;
            }
        };
    }
}
