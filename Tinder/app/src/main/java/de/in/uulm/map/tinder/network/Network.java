package de.in.uulm.map.tinder.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jona on 04.02.2017.
 *
 * This is a singleton class containing important network objects that
 * should only be instantiated once in the application.
 */
public class Network {

    private static Network mInstance;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private LruBitmapCache mBitmapCache;

    /**
     * This will return an instance of the Network class. If there is no
     * current instance present a new instance will be created.
     *
     * @param context the current application context
     * @return an instance of the Network class
     */
    public static synchronized Network getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new Network(context);
        }

        return mInstance;
    }

    /**
     * This constructor will instantiate the RequestQueue and the ImageLoader.
     *
     * @param context the current application context
     */
    private Network(Context context) {

        mRequestQueue = Volley.newRequestQueue(context);

        mBitmapCache = new LruBitmapCache(LruBitmapCache.getCacheSize(context));

        mImageLoader = new AuthImageLoader(
                mRequestQueue,
                mBitmapCache,
                context);
    }

    /**
     * Getter for Request Queue
     *
     * @return request queue for this application context
     */
    public RequestQueue getRequestQueue() {

        return mRequestQueue;
    }

    /**
     * Getter for Image Loader
     *
     * @return image loader for this application context
     */
    public ImageLoader getImageLoader() {

        return mImageLoader;
    }

    /**
     * Getter for Bitmap Cache
     *
     * @return bitmap cache for this application context
     */
    public LruBitmapCache getBitmapCache() {

        return mBitmapCache;
    }
}

