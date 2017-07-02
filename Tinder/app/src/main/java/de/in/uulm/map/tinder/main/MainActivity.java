package de.in.uulm.map.tinder.main;

import android.content.Intent;
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
import de.in.uulm.map.tinder.main.eventlist.EventListAdapter;
import de.in.uulm.map.tinder.main.eventlist.EventListFragment;
import de.in.uulm.map.tinder.main.eventlist.EventListPresenter;

/**
 * Created by Jona on 21.05.2017.
 */

public class MainActivity extends AppCompatActivity implements MainContract.Backend {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new MainPresenter(this, this);

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

        EventListPresenter eventListPresenter = new EventListPresenter(this);

        EventListFragment nearbyFragment =
                EventListFragment.newInstance(EventListFragment.TAB_NEARBY);
        nearbyFragment.setAdapter(new EventListAdapter(this, eventListPresenter));

        EventListFragment joinedFragment =
                EventListFragment.newInstance(EventListFragment.TAB_JOINED);
        joinedFragment.setAdapter(new EventListAdapter(this, eventListPresenter));

        EventListFragment createdFragment =
                EventListFragment.newInstance(EventListFragment.TAB_MY_EVENTS);
        createdFragment.setAdapter(new EventListAdapter(this, eventListPresenter));

        eventListPresenter.setNearbyView(nearbyFragment);
        eventListPresenter.setJoinedView(joinedFragment);
        eventListPresenter.setCreatedView(createdFragment);

        nearbyFragment.setPresenter(eventListPresenter);
        joinedFragment.setPresenter(eventListPresenter);
        createdFragment.setPresenter(eventListPresenter);

        pageAdapter.addFragment(nearbyFragment, R.id.bottom_nav_nearby);
        pageAdapter.addFragment(joinedFragment, R.id.bottom_nav_joined);
        pageAdapter.addFragment(createdFragment, R.id.bottom_nav_created);

        /**
         * Here you may add more fragments!
         */

        viewPager.setAdapter(pageAdapter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_nav_bar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mPresenter.topNavOnOptionSelected(item);
    }

    @Override
    public void startActivity(Intent intent) {

        super.startActivity(intent);
    }

    @Override
    public void startActivityFlip(Intent intent) {

        startActivity(intent);
        overridePendingTransition(R.transition.grow_from_middle, R
                .transition.shrink_to_middle);
    }
}
