package de.in.uulm.map.tinder.tinderview;

import android.content.Context;

import de.in.uulm.map.tinder.entities.Event;

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
    public List<Event> loadEvents() {

        return null;
    }
}
