package de.in.uulm.map.tinder.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.main.eventlist.EventListAdapter;
import de.in.uulm.map.tinder.main.eventlist.EventListFragment;
import de.in.uulm.map.tinder.main.eventlist.EventListPresenter;
import de.in.uulm.map.tinder.main.groupchat.GroupChatAdapter;
import de.in.uulm.map.tinder.main.groupchat.GroupChatFragment;
import de.in.uulm.map.tinder.main.groupchat.GroupChatPresenter;
import de.in.uulm.map.tinder.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jona on 21.05.2017.
 */

public class MainActivity extends AppCompatActivity implements MainContract.Backend{

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new MainPresenter(this, this);

        final BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation_menu);

        EventListPresenter eventListPresenter = new EventListPresenter(this);

        final EventListFragment nearbyFragment =
                EventListFragment.newInstance(eventListPresenter, "");
        nearbyFragment.setAdapter(new EventListAdapter(this, eventListPresenter));

        final EventListFragment joinedFragment =
                EventListFragment.newInstance(eventListPresenter, "Joined");
        joinedFragment.setAdapter(new EventListAdapter(this, eventListPresenter));

        final EventListFragment createdFragment =
                EventListFragment.newInstance(eventListPresenter, "Created");
        createdFragment.setAdapter(new EventListAdapter(this, eventListPresenter));


        final GroupChatFragment groupChatFragment =
                GroupChatFragment.newInstance();
        GroupChatPresenter groupChatPresenter = new GroupChatPresenter(this,
                groupChatFragment);

        groupChatFragment.setPresenter(groupChatPresenter);
        groupChatFragment.setAdapter(new GroupChatAdapter(groupChatPresenter, this));

        eventListPresenter.addEventView(nearbyFragment);
        eventListPresenter.addEventView(joinedFragment);
        eventListPresenter.addEventView(createdFragment);

        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                nearbyFragment,
                R.id.content_frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Fragment fragment = null;

                        switch(item.getItemId()) {
                            case R.id.bottom_nav_nearby:
                                fragment = nearbyFragment;
                                break;
                            case R.id.bottom_nav_my_events:
                                fragment = joinedFragment;
                                break;
                            case R.id.bottom_nav_chat:
                                fragment = groupChatFragment;
                                break;
                            default:
                                return false;
                        }

                        ActivityUtils.addFragmentToActivity(
                                getSupportFragmentManager(),
                                fragment,
                                R.id.content_frame);

                        return true;
                    }
                });
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
}
