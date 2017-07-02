package de.in.uulm.map.tinder.main.eventlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.R;

/**
 * Created by Jona on 05.05.17.
 */

public class EventListFragment extends Fragment implements EventListContract.EventListView {

    public static final String TAB_NEARBY = "Nearby";
    public static final String TAB_JOINED = "Joined";
    public static final String TAB_MY_EVENTS = "My Events";

    private EventListContract.EventListPresenter mPresenter;

    private EventListAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    public void onResume() {

        super.onResume();
        mPresenter.loadEvents(this);
    }

    public static EventListFragment newInstance(String title) {

        return new EventListFragment();
    }

    public EventListFragment() {

        setHasOptionsMenu(true);
    }

    public void setAdapter(EventListAdapter adapter) {

        this.mAdapter = adapter;
    }

    @Override
    public void setPresenter(EventListContract.EventListPresenter presenter) {

        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        onFragmentBecomesVisible();

        return view;
    }

    @Override
    public void onFragmentBecomesVisible() {
        mPresenter.loadEvents(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        mAdapter = new EventListAdapter(getContext(), mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.loadEvents(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.top_nav_bar_eventlist, menu);
    }

    @Override
    public EventListAdapter getAdapter() {

        return mAdapter;
    }
}
