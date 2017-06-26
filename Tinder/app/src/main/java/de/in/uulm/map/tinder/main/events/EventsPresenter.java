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

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.User;
import de.in.uulm.map.tinder.filter.FilterPresenter;
import de.in.uulm.map.tinder.network.Network;
import de.in.uulm.map.tinder.network.ServerRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by alexanderrasputin on 03.05.17.
 */

public class EventsPresenter implements EventsContract.EventsPresenter {

    private final Context mContext;

    private EventsContract.EventsView mNearbyView;

    private EventsContract.EventsView mJoinedView;

    private EventsContract.EventsView mCreatedView;

    public EventsPresenter(Context context) {

        mContext = context;
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

    @Override
    public void start() {

    }

    @Override
    public void loadEvents() {

        SharedPreferences accountPrefs = mContext.getSharedPreferences(
                mContext.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        final String userName = accountPrefs.getString(
                mContext.getString(R.string.store_username), "");

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        int radius = prefs.getInt(FilterPresenter.EVENT_RADIUS, 1);
        String category = prefs.getString(FilterPresenter.EVENT_CATEGORY, "all");
        if (category.equals("Alle")) {
            category = "all";
        } else {
            category = category.toLowerCase();
        }

        // TODO: asking for permissions this way sucks!

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

        final ServerRequest req = new ServerRequest(
                Request.Method.GET,
                url,
                null,
                mContext,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        Gson gson = new Gson();

                        String s = new String(response);

                        List<Event> events = Arrays.asList(gson.fromJson(
                                new String(response), Event[].class));

                        // TODO: it feels like those allocation are somewhat slow
                        // maybe avoid creating the lists here

                        ArrayList<Event> nearbyEvents = new ArrayList<>();
                        ArrayList<Event> joinedEvents = new ArrayList<>();
                        ArrayList<Event> createdEvents = new ArrayList<>();

                        for (Event e : events) {

                            // TODO: remove this and make autodelete working again

                            long end_date = new Date().getTime();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            try {
                                end_date = format.parse(e.start_date).getTime();
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }

                            long left = end_date - new Date().getTime();

                            if(left <= 0) {
                                continue;
                            }

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
                ServerRequest.DEFAULT_ERROR_LISTENER);

        Network.getInstance(mContext.getApplicationContext()).getRequestQueue().add(req);
    }

    @Override
    public void onDeleteClicked(Event e) {

        String url = mContext.getString(R.string.API_base);
        url += mContext.getString(R.string.API_event);
        url += "/" + e.id;

        ServerRequest req = new ServerRequest(
                Request.Method.DELETE,
                url,
                null,
                mContext,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        loadEvents();
                    }
                },
                ServerRequest.DEFAULT_ERROR_LISTENER);

        Network.getInstance(mContext.getApplicationContext()).getRequestQueue().add(req);
    }

    @Override
    public void onJoinClicked(Event e) {

        String url = mContext.getString(R.string.API_base);
        url += mContext.getString(R.string.API_event);
        url += "/" + e.id + "/join";

        ServerRequest req = new ServerRequest(
                Request.Method.PUT,
                url,
                null,
                mContext,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        loadEvents();
                    }
                },
                ServerRequest.DEFAULT_ERROR_LISTENER);

        Network.getInstance(mContext.getApplicationContext()).getRequestQueue().add(req);
    }

    @Override
    public void onLeaveClicked(Event e) {

        String url = mContext.getString(R.string.API_base);
        url += mContext.getString(R.string.API_event);
        url += "/" + e.id + "/leave";

        ServerRequest req = new ServerRequest(
                Request.Method.PUT,
                url,
                null,
                mContext,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        loadEvents();
                    }
                },
                ServerRequest.DEFAULT_ERROR_LISTENER);

        Network.getInstance(mContext.getApplicationContext()).getRequestQueue().add(req);
    }
}
