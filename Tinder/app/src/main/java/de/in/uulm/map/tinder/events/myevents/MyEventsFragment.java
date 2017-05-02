package de.in.uulm.map.tinder.events.myevents;

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

public class MyEventsFragment extends Fragment implements MyEventsContract.MyEventsView {

    public static final String TAB_MY_EVENTS = "My Events";

    MyEventsContract.MyEventsPresenter mPresenter;


    public static MyEventsFragment newInstance() {


        Bundle args = new Bundle();
        args.putString(EventsActivity.TAB_TITLE, TAB_MY_EVENTS);

        MyEventsFragment fragment = new MyEventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(MyEventsContract.MyEventsPresenter presenter) {

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
