package de.in.uulm.map.tinder.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import de.in.uulm.map.tinder.R;

/**
 * Created by alexanderrasputin on 03.05.17.
 */

public class EventsPresenter implements EventsContract.EventsPresenter {

    /**
     * reference to the joined view
     */
    private final EventsContract.EventsView mJoinedView;

    /**
     * reference to the my events view
     */
    private final EventsContract.EventsView mMyEventsView;

    /**
     * reference to the nearby view
     */
    private final EventsContract.EventsView mNearbyView;

    /**
     * context needed for intent construction
     */
    Context mContext;

    public EventsPresenter(EventsContract.EventsView joinedFragment,
                           EventsContract.EventsView myEventsView,
                           EventsContract.EventsView nearbyView,
                           Context context) {

        this.mJoinedView = joinedFragment;
        this.mMyEventsView = myEventsView;
        this.mNearbyView = nearbyView;
        this.mContext = context;
    }

    @Override
    public void start() {

    }

    /**
     * this is needed because onResume is not called if a tab has changed,
     * onPageSelected calls the corresponding fragment which is now visible
     *
     * @param viewPager see ViewPager
     * @param adapter   see EventsPageAdapter
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

                EventsFragment fragment =
                        (EventsFragment) adapter.getItem(position);
                fragment.fragmentBecomesVisible();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * This method is setting a listener for the bottom navigation bar
     *
     * @param view see BottomNavigationView
     */
    @Override
    public void bottomNavSetOnNavigationItemSelected(BottomNavigationView view) {

        view.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(
                            @NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.bottom_nav_events: {
                                // TODO start events activity
                                Log.d("events", "onNavigationItemSelected: ");
                                break;
                            }
                            case R.id.bottom_nav_add: {
                                // TODO start here add activity
                                Log.d("add", "onNavigationItemSelected: ");
                                break;
                            }
                            case R.id.bottom_nav_chat: {
                                // TODO start chat activity here
                                Log.d("chat", "onNavigationItemSelected: ");
                                break;
                            }
                        }
                        return true;
                    }
                }
        );
    }
}
