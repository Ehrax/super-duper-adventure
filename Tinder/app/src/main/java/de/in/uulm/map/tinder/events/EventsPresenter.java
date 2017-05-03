package de.in.uulm.map.tinder.events;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import de.in.uulm.map.tinder.events.joined.JoinedFragment;
import de.in.uulm.map.tinder.events.myevents.MyEventsFragment;
import de.in.uulm.map.tinder.events.naerby.NearbyFragment;

/**
 * Created by alexanderrasputin on 03.05.17.
 */

public class EventsPresenter implements EventsContract.EventsPresenter {

    /**
     * reference to the view
     */
    EventsContract.EventsView mView;

    /**
     * context needed for intent construction
     */
    Context mContext;

    public EventsPresenter(EventsContract.EventsView mView, Context mContext) {

        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void start() {

    }

    /**
     * this is needed because onResume is not called if a tab has changed,
     * onPageSelected calls the corresponding fragment which is now visible
     * @param viewPager see ViewPager
     * @param adapter see EventsPageAdapter
     */
    @Override
    public void setOnPageChangeListener(ViewPager viewPager,
                                        final EventsPageAdapter adapter) {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener
                () {

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Fragment fragment = adapter.getItem(position);
                Bundle args = fragment.getArguments();

                String title = args.getString(EventsActivity.TAB_TITLE);

                switch (title) {
                    case JoinedFragment.TAB_JOINED: {
                        JoinedFragment joinedFragment = (JoinedFragment) fragment;
                        joinedFragment.fragmentBecomesVisible();
                        break;
                    }
                    case MyEventsFragment.TAB_MY_EVENTS: {
                        MyEventsFragment myEventsFragment =
                                (MyEventsFragment) fragment;
                        myEventsFragment.fragmentBecomesVisible();
                        break;
                    }
                    case NearbyFragment.TAB_NEARBY: {
                        NearbyFragment nearbyFragment = (NearbyFragment) fragment;
                        nearbyFragment.fragmentBecomesVisible();
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * this method is starting the event add activity
     * @param fab see FloatingActionButton
     */
    @Override
    public void fabAddEventClickListener(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO start new activity here
            }
        });
    }
}
