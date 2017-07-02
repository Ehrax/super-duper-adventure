package de.in.uulm.map.tinder.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Jona on 18.06.17.
 *
 * This class will encode (and also compress) an image to base64. The resulting
 * string will be delivered on a callback.
 */

public class AsyncImageEncoder extends AsyncTask<Void, Void, String> {

    private Uri mUri;

    private Context mContext;

    private OnFinishedListener mListner;

    public interface OnFinishedListener {

        void onFinished(String encoded);
    }

    /**
     * This constructor will set all required member variables.
     *
     * @param uri     the uri of the image
     * @param context the current context.
     */
    public AsyncImageEncoder(Uri uri, Context context, OnFinishedListener listener) {

        mUri = uri;
        mContext = context;
        mListner = listener;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 8;

            InputStream in = mContext.getContentResolver().openInputStream(mUri);
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            byte[] array = out.toByteArray();
            return Base64.encodeToString(array, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String encoded) {

        mListner.onFinished(encoded);
    }
}
