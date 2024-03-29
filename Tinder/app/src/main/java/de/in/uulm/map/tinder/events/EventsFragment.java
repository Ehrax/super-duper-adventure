package de.in.uulm.map.tinder.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.R;

/**
 * Created by Jona on 05.05.17.
 */

public class EventsFragment extends Fragment implements EventsContract.EventsView {

    public static final String TAB_NEARBY = "Nearby";
    public static final String TAB_JOINED = "Joined";
    public static final String TAB_MY_EVENTS = "My Events";

    private EventsContract.EventsPresenter mPresenter;

    private EventsAdapter mAdapter;

    public static EventsFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(EventsActivity.TAB_TITLE, title);

        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void fragmentBecomesVisible() {

    }

    public void setAdapter(EventsAdapter adapter) {

        this.mAdapter = adapter;
    }

    @Override
    public void setPresenter(EventsContract.EventsPresenter presenter) {

        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.event_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
