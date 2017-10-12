package de.in.uulm.map.tinder.network;

import com.google.gson.Gson;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.preference.PreferenceManager;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.User;
import de.in.uulm.map.tinder.filter.FilterPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Jona on 28.06.17.
 */

public class EventRequest extends AuthRequest<List<Event>> {

    private final Response.Listener<List<Event>> mListener;

    /**
     * This method requires the ACCESS_FINE_LOCATION permission.
     * If this permission is not granted, the method will return null.
     *
     * @param groupUri Defines which group of event (nearby, joined, created)
     *                 will be loaded by this request. Can be "", "Joined",
     *                 "Created".
     */
    public static EventRequest newInstance(String groupUri,
                                           Context context,
                                           @Nullable Response.Listener<List<Event>> listener,
                                           @Nullable Response.ErrorListener errorListener) {

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            return null;
        }

        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

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

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        String category = prefs.getString(FilterPresenter.EVENT_CATEGORY, "all");
        if (category.equals("Alle")) {
            category = "all";
        } else {
            category = category.toLowerCase();
        }

        String url = context.getString(R.string.API_base);
        url += context.getString(R.string.API_event);
        url += "/" + groupUri;
        url += "?distance=" + prefs.getInt(FilterPresenter.EVENT_RADIUS, 1);
        url += "&category=" + category;
        url += "&latitude=" + latitude;
        url += "&longitude=" + longitude;

        return new EventRequest(Method.GET, url, context, listener, errorListener);
    }

    private EventRequest(int method,
                         String url,
                         Context context,
                         @Nullable Response.Listener<List<Event>> listener,
                         @Nullable Response.ErrorListener errorListener) {

        super(method, url, context, errorListener);

        mListener = listener;

        ArrayList<Event> events = new ArrayList<>();

        User userA = new User();
        userA.name = "Max";
        userA.id = "1";
        userA.email = "max@max.com";

        User userB = new User();
        userB.name = "Jona";
        userB.id = "2";
        userB.email = "jona@jona.com";

        User userC = new User();
        userC.name = "Alex";
        userC.id = "3";
        userC.email = "alex@alex.com";

        Event e = new Event();
        e.category = "4";
        e.location = "Uni";
        e.title = "Party";
        e.description = "Let's get rekt, m8ts!";
        e.creator = userA;
        e.has_image = true;
        e.id = "2";
        e.latitude = 0;
        e.longitude = 0;
        e.max_user_count = 20;
        e.setStartDate(new Date(2017, 10, 14, 20, 0));
        events.add(e);

        e = new Event();
        e.category = "2";
        e.location = "Uni";
        e.title = "Theater";
        e.description = "We are going to see some mediocre drama, fellows!";
        e.creator = userA;
        e.has_image = true;
        e.id = "4";
        e.latitude = 0;
        e.longitude = 0;
        e.max_user_count = 3;
        e.setStartDate(new Date(2017, 10, 13, 6, 30));
        e.participants.add(userB);
        events.add(e);

        e = new Event();
        e.category = "4";
        e.location = "Uni";
        e.title = "Demo";
        e.description = "Down with the sickness!";
        e.creator = userB;
        e.has_image = true;
        e.id = "1";
        e.latitude = 0;
        e.longitude = 0;
        e.max_user_count = 10;
        e.participants.add(userB);
        e.setStartDate(new Date(2017, 10, 13, 14, 0));
        events.add(e);

        e = new Event();
        e.category = "2";
        e.location = "Uni";
        e.title = "Hiking";
        e.description = "Let's go for a hike in the mountains!";
        e.creator = userA;
        e.has_image = true;
        e.id = "3";
        e.latitude = 0;
        e.longitude = 0;
        e.max_user_count = 5;
        e.setStartDate(new Date(2017, 10, 13, 6, 30));
        // e.participants.add(userB);
        events.add(e);

        listener.onResponse(events);
    }

    @Override
    protected Response<List<Event>> parseNetworkResponse(NetworkResponse response) {

        Gson gson = new Gson();
        String jsonString = new String(response.data);

        List<Event> events = Arrays.asList(
                gson.fromJson(jsonString, Event[].class));
        List<Event> filteredEvents = new ArrayList<>();

        for (Event e : events) {
            if (e.getStartDate().getTime() - new Date().getTime() >= 0) {
                filteredEvents.add(e);
            }
        }

        return Response.success(filteredEvents, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<Event> response) {

        mListener.onResponse(response);
    }
}
