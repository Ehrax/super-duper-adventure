package de.in.uulm.map.tinder.events;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventsActivity extends AppCompatActivity {

    public static final String TAB_TITLE = "tab-title";

    /**
     * reference to the presenter
     */
    private EventsContract.EventsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // adding Toolbar to events activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tab
        ViewPager viewPager = (ViewPager) findViewById(R.id.events_view_pager);
        setupViewPager(viewPager);

        // Set Tab inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.event_tabs);
        tabs.setupWithViewPager(viewPager);

        // adding bottom menu navigation handler
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById
                (R.id.bottom_navigation_menu);
        mPresenter.bottomNavSetOnNavigationItemSelected(bottomNav);
    }

    /**
     * This method is setting up the ViewPager with his adapter and their
     * tab fragments.
     */
    private void setupViewPager(ViewPager viewPager) {

        Realm realm = Realm.getDefaultInstance();

        // creating JoinedFragment and JoinedPresenter
        EventsFragment joinedFragment =
                EventsFragment.newInstance(EventsFragment.TAB_JOINED);
        RealmResults<Event> joinedEvents = realm.where(Event.class)
                .equalTo("participants.id", 1)
                .findAll();
        joinedFragment.setAdapter(new EventsAdapter(joinedEvents, mPresenter));

        // creating NearbyFragment and NearbyPresenter
        EventsFragment nearbyFragment =
                EventsFragment.newInstance(EventsFragment.TAB_NEARBY);
        RealmResults<Event> nearbyEvents = realm.where(Event.class)
                .findAll();
        nearbyFragment.setAdapter(new EventsAdapter(nearbyEvents, mPresenter));

        // creating MyEventsFragment and MyEventsPresenter
        EventsFragment myEventsFragment =
                EventsFragment.newInstance(EventsFragment.TAB_MY_EVENTS);
        RealmResults<Event> myEvents = realm.where(Event.class)
                .equalTo("creator.id", 1)
                .findAll();
        myEventsFragment.setAdapter(new EventsAdapter(myEvents, mPresenter));

        mPresenter = new EventsPresenter(joinedFragment, myEventsFragment,
                nearbyFragment, getApplicationContext());

        joinedFragment.setPresenter(mPresenter);
        myEventsFragment.setPresenter(mPresenter);
        nearbyFragment.setPresenter(mPresenter);

        EventsPageAdapter eventsPageAdapter =
                new EventsPageAdapter(getSupportFragmentManager());

        // adding Fragments to the EventPageAdapter
        eventsPageAdapter.addFragment(nearbyFragment);
        eventsPageAdapter.addFragment(joinedFragment);
        eventsPageAdapter.addFragment(myEventsFragment);
        viewPager.setAdapter(eventsPageAdapter);

        // adding onPageChangeListener to viewPager
        mPresenter.setOnPageChangeListener(viewPager, eventsPageAdapter);
    }
}