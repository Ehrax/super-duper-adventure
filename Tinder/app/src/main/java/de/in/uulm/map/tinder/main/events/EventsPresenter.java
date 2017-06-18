package de.in.uulm.map.tinder.main.events;

import com.google.gson.Gson;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.User;
import de.in.uulm.map.tinder.filter.FilterPresenter;
import de.in.uulm.map.tinder.network.JwtRequest;
import de.in.uulm.map.tinder.network.Network;
import de.in.uulm.map.tinder.util.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexanderrasputin on 03.05.17.
 */

public class EventsPresenter implements EventsContract.EventsPresenter {

    private EventsContract.EventsView mNearbyView;

    private EventsContract.EventsView mJoinedView;

    private EventsContract.EventsView mCreatedView;

    private final Context mContext;

    public EventsPresenter(Context context) {

        mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadEvents() {

        SharedPreferences accountPrefs = mContext.getSharedPreferences(
                mContext.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        final String token = accountPrefs.getString(
                mContext.getString(R.string.store_token), "");
        final String userName = accountPrefs.getString(
                mContext.getString(R.string.store_username), "");

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        String category = prefs.getString(FilterPresenter.EVENT_CATEGORY, "all");
        if (category.equals("Keine")) {
            category = "all";
        } else {
            category = category.toLowerCase();
        }

        int radius = prefs.getInt(FilterPresenter.EVENT_RADIUS, 1);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);

        // TODO: trigger real location updates here and
        // replace null test and default coordinates

        Location location = locationManager.
                getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // FORE coordinates as default coordinates, cause why not?
        double latitude = 48.4222129;
        double longitude = 9.9575566;

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        String url = mContext.getString(R.string.API_base);
        url += mContext.getString(R.string.API_event);
        url += "?category=" + category;
        url += "&distance=" + radius;
        url += "&latitude=" + latitude;
        url += "&longitude=" + longitude;

        final JwtRequest req = new JwtRequest(
                Request.Method.GET,
                url,
                token,
                null,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        String s = new String(response);

                        Gson gson = new Gson();

                        List<Event> events = Arrays.asList(gson.fromJson(
                                new String(response), Event[].class));

                        ArrayList<Event> nearbyEvents = new ArrayList<>();
                        ArrayList<Event> joinedEvents = new ArrayList<>();
                        ArrayList<Event> createdEvents = new ArrayList<>();

                        for (Event e : events) {
                            boolean currentUserParticipates = false;

                            for (User u : e.participants) {
                                if (u.name.equals(userName)) {
                                    currentUserParticipates = true;
                                    break;
                                }
                            }

                            if (!currentUserParticipates) {
                                nearbyEvents.add(e);
                            } else if (!e.creator.name.equals(userName)) {
                                joinedEvents.add(e);
                            } else {
                                createdEvents.add(e);
                            }
                        }

                        mNearbyView.getAdapter().setEvents(nearbyEvents);
                        mJoinedView.getAdapter().setEvents(joinedEvents);
                        mCreatedView.getAdapter().setEvents(createdEvents);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        JwtRequest.showErrorToast(mContext, error);
                    }
                });

        Network.getInstance(mContext).getRequestQueue().add(req);
    }

    @Override
    public void onDeleteClicked(Event e) {

        SharedPreferences accountPrefs = mContext.getSharedPreferences(
                mContext.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        final String token = accountPrefs.getString(
                mContext.getString(R.string.store_token), "");

        String url = mContext.getString(R.string.API_base);
        url += mContext.getString(R.string.API_event);
        url += "/" + e.id;

        JwtRequest req = new JwtRequest(Request.Method.DELETE,
                url,
                token,
                null,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        loadEvents();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        JwtRequest.showErrorToast(mContext, error);
                    }
                });

        Network.getInstance(mContext).getRequestQueue().add(req);
    }

    @Override
    public void onJoinClicked(Event e) {

    }

    @Override
    public void onLeaveClicked(Event e) {

    }

    public void setNearbyView(EventsContract.EventsView view) {

        mNearbyView = view;
    }

    public void setJoinedView(EventsContract.EventsView view) {

        mJoinedView = view;
    }

    public void setCreatedView(EventsContract.EventsView view) {

        mCreatedView = view;
    }

}
