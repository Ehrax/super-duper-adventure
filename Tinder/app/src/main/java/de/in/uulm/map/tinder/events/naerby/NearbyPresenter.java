package de.in.uulm.map.tinder.events.naerby;

import android.content.Context;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public class NearbyPresenter implements NearbyContract.NearbyPresenter {

    /**
     * Reference to the view
     */
    NearbyContract.NearbyView mView;

    /**
     * Context needed for intent construction
     */
    Context mContext;

    public NearbyPresenter(NearbyContract.NearbyView mView, Context mContext) {

        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void start() {

    }
}
