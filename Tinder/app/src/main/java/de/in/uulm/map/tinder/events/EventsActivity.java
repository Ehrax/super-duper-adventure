package de.in.uulm.map.tinder.events;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.events.joined.JoinedFragment;
import de.in.uulm.map.tinder.events.joined.JoinedPresenter;
import de.in.uulm.map.tinder.events.myevents.MyEventsFragment;
import de.in.uulm.map.tinder.events.myevents.MyEventsPresenter;
import de.in.uulm.map.tinder.events.naerby.NearbyFragment;
import de.in.uulm.map.tinder.events.naerby.NearbyPresenter;


public class EventsActivity extends AppCompatActivity implements
        EventsContract.EventsView {

    public static final String TAB_TITLE = "tab-title";

    /**
     * reference to the presenter
     */
    private EventsContract.EventsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // setting up presenter
        EventsPresenter presenter = new EventsPresenter(this,
                getApplicationContext());
        setPresenter(presenter);

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
     * This method is creating the toolbar menu icon
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigatino_bar, menu);
        return true;
    }

    /**
     * This method is setting up the ViewPager with his adapter and their
     * tab fragments.
     */
    private void setupViewPager(ViewPager viewPager) {

        // creating NearbyFragment and NearbyPresenter
        NearbyFragment nearbyFragment = NearbyFragment.newInstance();
        NearbyPresenter nearbyPresenter = new NearbyPresenter(nearbyFragment,
                getApplicationContext());
        nearbyFragment.setPresenter(nearbyPresenter);

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

        EventsPageAdapter eventsPageAdapter = new EventsPageAdapter
                (getSupportFragmentManager());

        // adding Fragments to the EventPageAdapter
        eventsPageAdapter.addFragment(nearbyFragment);
        eventsPageAdapter.addFragment(joinedFragment);
        eventsPageAdapter.addFragment(myEventsFragment);
        viewPager.setAdapter(eventsPageAdapter);

        // adding onPageChangeListener to viewPager
        mPresenter.setOnPageChangeListener(viewPager, eventsPageAdapter);
    }

    @Override
    public void setPresenter(EventsContract.EventsPresenter presenter) {

        this.mPresenter = presenter;
    }
}