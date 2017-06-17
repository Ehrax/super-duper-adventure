package de.in.uulm.map.tinder.main.add;

import com.google.android.gms.location.places.Place;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.User;
import de.in.uulm.map.tinder.network.JwtRequest;
import de.in.uulm.map.tinder.network.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, String> params = new HashMap<>();
        params.put("title", mView.getTitle());
        params.put("description", mView.getDescription());
        params.put("timespan", "" + mDuration);
        params.put("maxUsers", "" + mMaxUser);

        final List<String> categories = Arrays.asList(
                mContext.getResources().getStringArray(R.array.categories));

        params.put("category", "" + categories.indexOf(mCategory));
        params.put("latitude", "" + mLocation.getLatLng().latitude);
        params.put("longitude", "" + mLocation.getLatLng().longitude);

        // TODO: put this in extra thread, terrible slow! sad!

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(
                    mContext.getContentResolver().openInputStream(mImageUri));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            byte[] array = out.toByteArray();
            String encoded = Base64.encodeToString(array, Base64.DEFAULT);
            params.put("imagebase64", encoded);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        SharedPreferences accountPrefs = mContext.getSharedPreferences(
        mContext.getString(R.string.store_account),
        Context.MODE_PRIVATE);

        final String token = accountPrefs.getString(
                mContext.getString(R.string.store_token), "");

        String url = mContext.getString(R.string.API_base) +
                mContext.getString(R.string.API_event);

        JwtRequest req = new JwtRequest(
                Request.Method.POST,
                url,
                token,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        Log.d("Test", "Test");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        JwtRequest.showErrorToast(mContext, error);
                    }
                });

        Network.getInstance(mContext).getRequestQueue().add(req);

        // reset everything ...

        mView.showMessage("Event created!");

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
