package de.in.uulm.map.tinder.events.joined;

import android.content.Context;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public class JoinedPresenter implements JoinedContract.JoinedPresenter {

    /**
     * reference to the view
     */
    JoinedContract.JoinedView mView;

    /**
     * Context needed for intent construction
     */
    Context mContext;

    public JoinedPresenter(JoinedContract.JoinedView view, Context context) {

        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void start() {

    }
}
