package de.in.uulm.map.tinder.events.joined;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.events.EventsActivity;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public class JoinedFragment extends Fragment implements JoinedContract.JoinedView {

    public static final String TAB_JOINED = "Joined";

    JoinedContract.JoinedPresenter mPresenter;

    public static JoinedFragment newInstance() {

        Bundle args = new Bundle();
        args.putString(EventsActivity.TAB_TITLE, TAB_JOINED);

        JoinedFragment fragment = new JoinedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(JoinedContract.JoinedPresenter presenter) {

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
