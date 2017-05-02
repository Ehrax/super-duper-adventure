package de.in.uulm.map.tinder.events.naerby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.events.EventsActivity;
import de.in.uulm.map.tinder.util.BasePresenter;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public class NearbyFragment extends Fragment implements NearbyContract.NearbyView {

    public static final String TAB_NEARBY = "Nearby";

    public static NearbyFragment newInstance() {

        Bundle args = new Bundle();
        args.putString(EventsActivity.TAB_TITLE, TAB_NEARBY);

        NearbyFragment fragment = new NearbyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    NearbyContract.NearbyPresenter mPresenter;

    @Override
    public void setPresenter(NearbyContract.NearbyPresenter presenter) {

        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
