package de.in.uulm.map.tinder.main.eventlist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.main.event.EventActivity;
import de.in.uulm.map.tinder.network.DefaultErrorListener;
import de.in.uulm.map.tinder.network.EventRequest;
import de.in.uulm.map.tinder.network.Network;
import de.in.uulm.map.tinder.network.ServerRequest;
import de.in.uulm.map.tinder.util.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderrasputin on 03.05.17.
 */

public class EventListPresenter implements EventListContract.EventListPresenter {

    private final AppCompatActivity mActivity;

    private ArrayList<EventListContract.EventListView> mViews;

    public EventListPresenter(AppCompatActivity context) {

        mActivity = context;
        mViews = new ArrayList<>();
    }

    public void addEventView(EventListContract.EventListView view) {

        mViews.add(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadEvents(final EventListContract.EventListView view) {

        // TODO: asking for permissions this way sucks!

        if (ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions((Activity) mActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

        EventRequest req = EventRequest.newInstance(
                view.getGroupUri(),
                mActivity,
                new Response.Listener<List<Event>>() {
                    @Override
                    public void onResponse(List<Event> response) {

                        view.getAdapter().setEvents((ArrayList<Event>)response);
                    }
                },
                new DefaultErrorListener(mActivity));

        Network.getInstance(mActivity.getApplicationContext()).getRequestQueue().add(req);
    }

    @Override
    public void onDeleteClicked(final Event e) {

        String url = mActivity.getString(R.string.API_base);
        url += mActivity.getString(R.string.API_event);
        url += "/" + e.id;

        ServerRequest req = new ServerRequest(
                Request.Method.DELETE,
                url,
                null,
                mActivity,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        for(EventListContract.EventListView v : mViews) {
                            v.getAdapter().removeEvent(e);
                        }
                    }
                },
                new DefaultErrorListener(mActivity));

        Network.getInstance(mActivity.getApplicationContext()).getRequestQueue().add(req);

        FirebaseHelper helper = new FirebaseHelper();
        helper.removeGroupChat(e.id);
    }

    @Override
    public void onJoinClicked(final Event e) {

        String url = mActivity.getString(R.string.API_base);
        url += mActivity.getString(R.string.API_event);
        url += "/" + e.id + "/join";

        ServerRequest req = new ServerRequest(
                Request.Method.PUT,
                url,
                null,
                mActivity,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        for(EventListContract.EventListView v : mViews) {
                            loadEvents(v);
                        }
                    }
                },
                new DefaultErrorListener(mActivity));

        Network.getInstance(mActivity.getApplicationContext()).getRequestQueue().add(req);
    }

    @Override
    public void onLeaveClicked(final Event e) {

        String url = mActivity.getString(R.string.API_base);
        url += mActivity.getString(R.string.API_event);
        url += "/" + e.id + "/leave";

        ServerRequest req = new ServerRequest(
                Request.Method.PUT,
                url,
                null,
                mActivity,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        for(EventListContract.EventListView v : mViews) {
                            loadEvents(v);
                        }
                    }
                },
                new DefaultErrorListener(mActivity));

        Network.getInstance(mActivity.getApplicationContext()).getRequestQueue().add(req);
    }

    public void onEditClicked(Event e) {

        Intent intent = new Intent(mActivity, EventActivity.class);
        intent.putExtra(EventActivity.EVENT_EXTRA, e);
        mActivity.startActivityForResult(intent, 0);
    }
}
