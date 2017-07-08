package de.in.uulm.map.tinder.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Jona on 18.06.2017.
 *
 * This class should be used to decode base64 encoded images and place them in
 * an image view.
 */
public class AsyncImageDecoder extends AsyncTask<Void, Void, Bitmap> {

    final private String mEncoded;

    final private WeakReference<ImageView> mView;

    /**
     * This constructor will set all required member variables.
     *
     * @param encoded the base64 encoded image
     * @param view the view in which the image should be loaded
     */
    public AsyncImageDecoder(@Nullable String encoded,
                             WeakReference<ImageView> view) {

        this.mEncoded = encoded;
        this.mView = view;

        if(encoded == null) {
            return;
        }

        if(view.get().getTag() != null && view.get().getTag().equals(mEncoded)) {
            return;
        }

        view.get().setTag(mEncoded);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        if(mEncoded == null) {
            return null;
        }

        byte[] bytes = Base64.decode(mEncoded, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (this.isCancelled()) {
            return;
        }

        if (bitmap != null && mView != null) {
            final ImageView img_view = mView.get();
            if (img_view != null && img_view.getTag().equals(mEncoded)) {
                img_view.setImageBitmap(bitmap);
            }
        }
    }
}
