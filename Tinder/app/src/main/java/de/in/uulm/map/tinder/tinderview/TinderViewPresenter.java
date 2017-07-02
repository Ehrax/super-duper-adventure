package de.in.uulm.map.tinder.tinderview;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.android.volley.Response;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.main.MainActivity;
import de.in.uulm.map.tinder.network.DefaultErrorListener;
import de.in.uulm.map.tinder.network.EventRequest;
import de.in.uulm.map.tinder.network.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxka on 26.06.2017.
 */

public class TinderViewPresenter implements TinderViewContract.Presenter {

    private final TinderViewContract.View mView;
    private final TinderViewContract.Backend mBackend;

    private Context mApplicationContext;
    private Context mContext;

    public TinderViewPresenter(TinderViewContract.View view, Context
            applicationContext, Context context, TinderViewContract.Backend
                                       backend) {

        mView = view;
        mBackend = backend;
        mApplicationContext = applicationContext;
        mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadEvents() {

        EventRequest req = EventRequest.newInstance(
                "",
                mContext,
                new Response.Listener<List<Event>>() {
                    @Override
                    public void onResponse(List<Event> response) {

                        mView.setEvents(response);
                    }
                },
                new DefaultErrorListener(mContext));

        Network.getInstance(mContext.getApplicationContext()).getRequestQueue().add(req);

    }

    @Override
    public boolean topNavOnOptionSelected(MenuItem item) {

        if (item.getItemId() == R.id.top_nav_flip_view_tinder) {
            mBackend.startActivityFlip(new Intent(mContext, MainActivity
                    .class));
        }
        return false;
    }
}
