package de.in.uulm.map.tinder.main.eventlist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.main.event.EventActivity;
import de.in.uulm.map.tinder.network.DefaultErrorListener;
import de.in.uulm.map.tinder.network.EventRequest;
import de.in.uulm.map.tinder.network.Network;
import de.in.uulm.map.tinder.network.ServerRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderrasputin on 03.05.17.
 */

public class EventListPresenter implements EventListContract.EventListPresenter {

    private final Context mContext;

    private EventListContract.EventListView mNearbyView;

    private EventListContract.EventListView mJoinedView;

    private EventListContract.EventListView mCreatedView;

    public EventListPresenter(Context context) {

        mContext = context;
    }

    public void setNearbyView(EventListContract.EventListView view) {

        mNearbyView = view;
    }

    public void setJoinedView(EventListContract.EventListView view) {

        mJoinedView = view;
    }

    public void setCreatedView(EventListContract.EventListView view) {

        mCreatedView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadEvents(final EventListContract.EventListView view) {

        // TODO: asking for permissions this way sucks!

        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

        String groupUri;
        if(view == mCreatedView) {
            groupUri = "Created";
        } else if (view == mJoinedView) {
            groupUri = "Joined";
        } else {
            groupUri = "";
        }

        EventRequest req = EventRequest.newInstance(
                groupUri,
                mContext,
                new Response.Listener<List<Event>>() {
                    @Override
                    public void onResponse(List<Event> response) {

                        view.getAdapter().setEvents((ArrayList<Event>)response);
                    }
                },
                new DefaultErrorListener(mContext));

        Network.getInstance(mContext.getApplicationContext()).getRequestQueue().add(req);
    }

    @Override
    public void onDeleteClicked(final Event e) {

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

                        mCreatedView.getAdapter().removeEvent(e);
                    }
                },
                new DefaultErrorListener(mContext));

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

                        // TODO: drink some tea
                    }
                },
                new DefaultErrorListener(mContext));

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

                        // TODO: drink some coffee
                    }
                },
                new DefaultErrorListener(mContext));

        Network.getInstance(mContext.getApplicationContext()).getRequestQueue().add(req);
    }

    public void onEditClicked(Event e) {

        Intent intent = new Intent(mContext, EventActivity.class);
        intent.putExtra(EventActivity.EVENT_EXTRA, e);
        mContext.startActivity(intent);
    }
}
