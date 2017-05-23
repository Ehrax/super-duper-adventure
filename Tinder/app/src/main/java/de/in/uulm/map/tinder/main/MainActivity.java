package de.in.uulm.map.tinder.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.main.add.AddEventFragment;
import de.in.uulm.map.tinder.main.add.AddEventPresenter;
import de.in.uulm.map.tinder.main.events.EventsAdapter;
import de.in.uulm.map.tinder.main.events.EventsFragment;
import de.in.uulm.map.tinder.main.events.EventsPresenter;

/**
 * Created by Jona on 21.05.2017.
 */

public class MainActivity extends AppCompatActivity {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new MainPresenter();

        final MainPageAdapter pageAdapter =
                new MainPageAdapter(getSupportFragmentManager());

        final BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation_menu);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // not used ...
            }

            @Override
            public void onPageSelected(int position) {

                bottomNavigationView.setSelectedItemId(
                        pageAdapter.getIdByIndex(position));

                MainContract.MainView fragment = (MainContract.MainView)
                        pageAdapter.getItem(position);
                fragment.onFragmentBecomesVisible();
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // not used ...
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        viewPager.setCurrentItem(
                                pageAdapter.getIndexById(item.getItemId()));
                        return true;
                    }
                });

        EventsPresenter eventsPresenter = new EventsPresenter(this);

        EventsFragment nearbyFragment =
                EventsFragment.newInstance(EventsFragment.TAB_NEARBY);
        nearbyFragment.setAdapter(new EventsAdapter(eventsPresenter));

        EventsFragment joinedFragment =
                EventsFragment.newInstance(EventsFragment.TAB_JOINED);
        joinedFragment.setAdapter(new EventsAdapter(eventsPresenter));

        EventsFragment createdFragment =
                EventsFragment.newInstance(EventsFragment.TAB_MY_EVENTS);
        createdFragment.setAdapter(new EventsAdapter(eventsPresenter));

        eventsPresenter.setNearbyView(nearbyFragment);
        eventsPresenter.setJoinedView(nearbyFragment);
        eventsPresenter.setCreatedView(nearbyFragment);

        nearbyFragment.setPresenter(eventsPresenter);
        joinedFragment.setPresenter(eventsPresenter);
        createdFragment.setPresenter(eventsPresenter);

        pageAdapter.addFragment(nearbyFragment, R.id.bottom_nav_nearby);
        pageAdapter.addFragment(joinedFragment, R.id.bottom_nav_joined);
        pageAdapter.addFragment(createdFragment, R.id.bottom_nav_created);

        AddEventFragment addEventFragment = AddEventFragment.newInstance();
        AddEventPresenter addEventPresenter =
                new AddEventPresenter(addEventFragment, addEventFragment);
        addEventFragment.setPresenter(addEventPresenter);

        pageAdapter.addFragment(addEventFragment, R.id.bottom_nav_add);

        /**
         * Here you may add more fragments!
         */

        viewPager.setAdapter(pageAdapter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mPresenter.topNavOnOptionSelected(item);
    }
}
