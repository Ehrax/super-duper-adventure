package de.in.uulm.map.tinder.main.events;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.network.JwtRequest;
import de.in.uulm.map.tinder.network.Network;

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
    public void loadEvents(EventsContract.EventsView view, final EventsAdapter adapter) {

        SharedPreferences prefs = mContext.getSharedPreferences(
                mContext.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        final String token = prefs.getString(
                mContext.getString(R.string.store_token), "");

        String url = mContext.getString(R.string.API_base);
        url += mContext.getString(R.string.API_getEvent);
        url += "?category=all";

        JwtRequest req = new JwtRequest(
                Request.Method.GET,
                url,
                token,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        Gson gson = new Gson();

                        List<Event> events = Arrays.asList(gson.fromJson(
                                new String(response), Event[].class));

                        adapter.setEvents(new ArrayList<>(events));
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
