package de.in.uulm.map.tinder.events.myevents;

import android.content.Context;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public class MyEventsPresenter implements MyEventsContract.MyEventsPresenter {

    /**
     * Reference to the view
     */
    MyEventsContract.MyEventsView mView;

    /**
     * Context needed for intent construction
     */
    Context mContext;

    public MyEventsPresenter(MyEventsContract.MyEventsView mView,
                             Context mContext) {

        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void start() {

    }
}
