package de.in.uulm.map.tinder.events;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.events.joined.JoinedFragment;
import de.in.uulm.map.tinder.events.joined.JoinedPresenter;
import de.in.uulm.map.tinder.events.myevents.MyEventsFragment;
import de.in.uulm.map.tinder.events.myevents.MyEventsPresenter;
import de.in.uulm.map.tinder.events.naerby.NearbyFragment;
import de.in.uulm.map.tinder.events.naerby.NearbyPresenter;


public class EventsActivity extends AppCompatActivity {

    public static final String TAB_TITLE = "tab-title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // adding Toolbar to events activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.eventsToolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tab
        ViewPager viewPager = (ViewPager) findViewById(R.id.eventsViewpager);
        setupViewPager(viewPager);

        // Set Tab inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.eventTabs);
        tabs.setupWithViewPager(viewPager);

    }

    /**
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {

        // creating JoinedFragment and JoinedPresenter
        JoinedFragment joinedFragment = JoinedFragment.newInstance();
        JoinedPresenter joinedPresenter = new JoinedPresenter(joinedFragment,
                getApplicationContext());
        joinedFragment.setPresenter(joinedPresenter);

        // creating MyEventsFragment and MyEventsPresenter
        MyEventsFragment myEventsFragment = MyEventsFragment.newInstance();
        MyEventsPresenter myEventsPresenter = new MyEventsPresenter
                (myEventsFragment, getApplicationContext());
        myEventsFragment.setPresenter(myEventsPresenter);

        // creating NearbyFragment and NearbyPresenter
        NearbyFragment nearbyFragment = NearbyFragment.newInstance();
        NearbyPresenter nearbyPresenter = new NearbyPresenter(nearbyFragment,
                getApplicationContext());
        nearbyFragment.setPresenter(nearbyPresenter);

        EventsPageAdapter eventsPageAdapter = new EventsPageAdapter
                (getSupportFragmentManager());

        // adding Fragments to the EventPageAdapter
        eventsPageAdapter.addFragment(joinedFragment);
        eventsPageAdapter.addFragment(myEventsFragment);
        eventsPageAdapter.addFragment(nearbyFragment);
        viewPager.setAdapter(eventsPageAdapter);
    }
}