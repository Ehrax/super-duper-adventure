package de.in.uulm.map.tinder.main.event;

import com.google.android.gms.location.places.Place;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.content.Intent;

import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.login.LoginActivity;
import de.in.uulm.map.tinder.network.DefaultErrorListener;
import de.in.uulm.map.tinder.network.EventRequest;
import de.in.uulm.map.tinder.network.Network;
import de.in.uulm.map.tinder.network.ServerRequest;
import de.in.uulm.map.tinder.util.AsyncImageEncoder;
import de.in.uulm.map.tinder.util.FirebaseHelper;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Jona on 21.05.2017.
 */

public class EventPresenter implements EventContract.Presenter {

    private final EventContract.View mView;

    private final EventContract.Backend mBackend;

    private final AppCompatActivity mContext;

    private final boolean mCreateMode;

    private Event mEvent;

    public EventPresenter(AppCompatActivity context,
                          EventContract.View view,
                          EventContract.Backend backend,
                          Event event) {

        mContext = context;
        mView = view;
        mBackend = backend;
        mEvent = event;
        mCreateMode = event == null;
    }

    @Override
    public void start() {

        if (mEvent == null) {
            mEvent = new Event();
            mEvent.has_image = false;
            mEvent.title = "";
            mEvent.description = "";
            mEvent.setStartDate(new Date());
            mEvent.longitude = -181;
            mEvent.latitude = -90;
            mEvent.location = "";
            mEvent.category = "0";
            mEvent.max_user_count = 5;
        }

        mView.showEvent(mEvent);
    }

    public void checkEnableSubmitButton() {

        boolean enabled = true;
        enabled &= mEvent.title != null && !mEvent.title.isEmpty();
        enabled &= mEvent.title != null && !mEvent.title.isEmpty();
        enabled &= mEvent.category != null && !mEvent.category.isEmpty();
        enabled &= mEvent.getStartDate() != null;
        enabled &= Math.abs(mEvent.latitude) < 181;
        enabled &= Math.abs(mEvent.longitude) < 91;
        enabled &= mEvent.location != null && !mEvent.location.isEmpty();
        enabled &= mEvent.max_user_count != -1;
        mView.setEnableSubmitButton(enabled);
    }

    @Override
    public void onImageSelected(Uri imageUri) {

        mEvent.has_image = true;
        mView.showImage(imageUri.toString());
        checkEnableSubmitButton();
    }

    @Override
    public void onTitleChanged(String s) {

        mEvent.title = s;
        checkEnableSubmitButton();
    }

    @Override
    public void onDescriptionChanged(String s) {

        mEvent.description = s;
        checkEnableSubmitButton();
    }

    @Override
    public void setImage(String category, ImageView view) {

        switch (category) {
            case "Sport": {
                view.setImageResource(R.drawable.sport_category);
                break;
            }

            case "Ausgehen": {
                view.setImageResource(R.drawable.party_category);
                break;
            }

            case "Erholung": {
                view.setImageResource(R.drawable.rest_category);
                break;
            }

            case "Kultur": {
                view.setImageResource(R.drawable.culture_category);
                break;
            }

            case "Sonstiges": {
                view.setImageResource(R.drawable.other_category);
                break;
            }
        }
    }

    @Override
    public void onDurationSelected(long time) {

        mEvent.setStartDate(new Date(time));
        mView.showStartDate(time);
        checkEnableSubmitButton();
    }

    @Override
    public void onLocationSelected(Place location) {

        mEvent.latitude = location.getLatLng().latitude;
        mEvent.longitude = location.getLatLng().longitude;


        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());

        try {
            List<Address> addresses = gcd.getFromLocation(mEvent.latitude, mEvent
                    .longitude, 1);
            if (addresses.size() > 0) {
                mEvent.location = addresses.get(0).getLocality();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            String name = location.getName() == null ? "" :
                    location.getName().toString();
            String address = location.getAddress() == null ? "" :
                    ", " + location.getAddress().toString();
            mEvent.location = name + address;
        }


        if (mEvent.location.isEmpty()) {
            mEvent.location = "" + mEvent.latitude + ", " + mEvent.longitude;
        }

        mView.showLocation(mEvent.location);

        checkEnableSubmitButton();
    }

    @Override
    public void onMaxUserSelected(int maxUser) {

        mEvent.max_user_count = maxUser;
        mView.showMaxUser(maxUser);
        checkEnableSubmitButton();
    }

    @Override
    public void onCategorySelected(String category) {

        mEvent.category = category;
        mView.showCategory(category);
        checkEnableSubmitButton();
    }

    @Override
    public void onSubmitClicked() {

        mView.setProgressBarVisible(true);

        final JsonObject obj = (JsonObject) new Gson().toJsonTree(mEvent);

        List<String> categories = Arrays.asList(
                mContext.getResources().getStringArray(R.array.categories));
        obj.addProperty("Category",
                categories.indexOf(obj.get("Category").getAsString()));

        if (mView.getImageUri() == null || !mView.getImageUri().contains("content://")) {
            sendEvent(obj);
            return;
        }

        new AsyncImageEncoder(
                Uri.parse(mView.getImageUri()),
                mContext,
                new AsyncImageEncoder.OnFinishedListener() {
                    @Override
                    public void onFinished(String encoded) {

                        obj.addProperty("ImageBase64", encoded);
                        sendEvent(obj);
                    }
                }).execute();
    }

    private void sendEvent(final JsonObject obj) {

        String url = mContext.getString(R.string.API_base);
        url += mContext.getString(R.string.API_event);

        int method = mCreateMode ? Request.Method.POST : Request.Method.PUT;

        ServerRequest req = new ServerRequest(
                method,
                url,
                obj.toString().getBytes(),
                mContext,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        createFirebaseGroupChat(obj);
                        mBackend.setResult(Activity.RESULT_OK, new Intent());
                        mBackend.finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        mView.setProgressBarVisible(false);
                        new DefaultErrorListener(mContext).onErrorResponse(error);
                    }
                });

        Network network = Network.getInstance(mContext.getApplicationContext());
        network.getRequestQueue().add(req);

        // Quite hardcore, my dear!
        if (!mCreateMode) {
            network.getBitmapCache().evictAll();
        }
    }

    // TODO remove this method after i get an id from the server
    private void createFirebaseGroupChat(JsonObject obj) {

        Gson gson = new Gson();
        final Event createdEvent = gson.fromJson(obj, Event.class);

        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

        EventRequest createdEvents = EventRequest.newInstance(
                "Joined",
                mContext,
                new Response.Listener<List<Event>>() {
                    @Override
                    public void onResponse(List<Event> response) {

                        for (Event e : response) {
                            if (e.category.equals(createdEvent.category) && e
                                    .getStartDate().equals(createdEvent
                                            .getStartDate()) || e.longitude
                                    == createdEvent.longitude || e.latitude
                                    == createdEvent.latitude || e.title
                                    .equals(createdEvent.title)) {

                                FirebaseHelper helper = new FirebaseHelper();
                                FirebaseGroupChat chat = new FirebaseGroupChat();
                                chat.eventId = e.id;
                                chat.img = e.has_image;
                                chat.chatName = e.title;
                                helper.createGroup(chat);
                            }
                        }
                    }
                },
                new DefaultErrorListener(mContext));


        Network.getInstance(mContext.getApplicationContext())
                .getRequestQueue().add(createdEvents);
    }
}