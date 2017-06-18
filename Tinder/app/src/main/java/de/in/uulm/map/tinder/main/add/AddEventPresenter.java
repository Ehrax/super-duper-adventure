package de.in.uulm.map.tinder.main.add;

import com.google.android.gms.location.places.Place;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.network.ServerRequest;
import de.in.uulm.map.tinder.network.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jona on 21.05.2017.
 */

public class AddEventPresenter implements AddEventContract.Presenter {

    private final AddEventContract.View mView;

    private final AddEventContract.Backend mBackend;

    private final Context mContext;

    private Uri mImageUri = null;

    private long mDuration = 3600000 * 2;

    private String mCategory = "Ausgehen";

    private Place mLocation = null;

    private int mMaxUser = 5;

    public AddEventPresenter(Context context,
                             AddEventContract.View view,
                             AddEventContract.Backend backend) {

        mContext = context;
        mView = view;
        mBackend = backend;
    }

    @Override
    public void start() {

        mView.showTitle("");
        mView.showDescription("");
        mView.showImage(mImageUri);
        mView.showDuration(mDuration);
        mView.showCategory(mCategory);
        mView.showMaxUser(mMaxUser);
        mView.showLocation("");
    }

    public void checkEnableCreateButton() {

        boolean enabled = true;
        enabled &= mCategory != null;
        enabled &= mDuration != -1;
        enabled &= mLocation != null;
        enabled &= mMaxUser != -1;
        enabled &= !mView.getDescription().isEmpty();
        enabled &= !mView.getTitle().isEmpty();

        mView.setEnableCreateButton(enabled);
    }

    @Override
    public void onImageClicked() {

        mBackend.selectImage();
    }

    @Override
    public void onDurationClicked() {

        mBackend.selectDuration();
    }

    @Override
    public void onLocationClicked() {

        mBackend.selectLocation();
    }

    @Override
    public void onMaxUserClicked() {

        mBackend.selectMaxUser();
    }

    @Override
    public void onCategoryClicked() {

        mBackend.selectCategory();
    }

    @Override
    public void onImageSelected(Uri imageUri) {

        mImageUri = imageUri;
        mView.showImage(mImageUri);
        checkEnableCreateButton();
    }

    @Override
    public void onDurationSelected(long time) {

        mDuration = time;
        mView.showDuration(mDuration);
        checkEnableCreateButton();
    }

    @Override
    public void onLocationSelected(Place location) {

        mLocation = location;

        String name = mLocation.getName() == null ? "" :
                mLocation.getName().toString();
        String address = mLocation.getAddress() == null ? "" :
                ", " + mLocation.getAddress().toString();

        String string = name + address;

        if (string.isEmpty()) {
            string = "" + location.getLatLng().latitude + ", " +
                    location.getLatLng().longitude;
        }

        mView.showLocation(string);

        checkEnableCreateButton();
    }

    @Override
    public void onMaxUserSelected(int maxUser) {

        mMaxUser = maxUser;
        mView.showMaxUser(mMaxUser);
        checkEnableCreateButton();
    }

    @Override
    public void onCategorySelected(String category) {

        mCategory = category;
        mView.showCategory(mCategory);
        checkEnableCreateButton();
    }

    @Override
    public void onCreateClicked() {

        // TODO: put this in extra thread, terribly slow! sad!

        try {

            JSONObject obj = new JSONObject();

            obj.put("title", mView.getTitle());
            obj.put("description", mView.getDescription());
            obj.put("timespan", mDuration);
            obj.put("maxUsers", mMaxUser);

            final List<String> categories = Arrays.asList(
                    mContext.getResources().getStringArray(R.array.categories));

            obj.put("category", categories.indexOf(mCategory));
            obj.put("latitude", mLocation.getLatLng().latitude);
            obj.put("longitude", mLocation.getLatLng().longitude);

            // encode image

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            InputStream in = mContext.getContentResolver().openInputStream(mImageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

            byte[] array = out.toByteArray();
            String encoded = Base64.encodeToString(array, Base64.DEFAULT);

            obj.put("imagebase64", encoded);

            String url = mContext.getString(R.string.API_base);
            url += mContext.getString(R.string.API_event);

            ServerRequest req = new ServerRequest(
                    Request.Method.POST,
                    url,
                    obj.toString().getBytes(),
                    mContext,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {

                            mView.showMessage("Event created!");
                        }
                    },
                    ServerRequest.DEFAULT_ERROR_LISTENER);

            Network.getInstance(mContext).getRequestQueue().add(req);

        } catch (FileNotFoundException | JSONException e) {
            mView.showMessage("Event not created!");
            e.printStackTrace();
        }

        mImageUri = null;
        mMaxUser = 4;
        mCategory = "Ausgehen";
        mDuration = 3600000 * 2;
        mLocation = null;

        mView.showTitle("");
        mView.showDescription("");
        mView.showImage(mImageUri);
        mView.showDuration(mDuration);
        mView.showCategory(mCategory);
        mView.showMaxUser(mMaxUser);
        mView.showLocation("");
    }
}
