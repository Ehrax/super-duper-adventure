package de.in.uulm.map.tinder.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.User;
import de.in.uulm.map.tinder.main.eventlist.EventListAdapter;
import de.in.uulm.map.tinder.main.eventlist.EventListFragment;
import de.in.uulm.map.tinder.main.eventlist.EventListPresenter;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by Jona on 02.07.17.
 */

public class ProfileActivity extends AppCompatActivity {

    public final static String EXTRA_USER = "user";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = (User) getIntent().getSerializableExtra(EXTRA_USER);

        if(user != null) {
            CollapsingToolbarLayout collapsingToolbarLayout =
                    (CollapsingToolbarLayout) findViewById(R.id.main_bar_collapsing);
            collapsingToolbarLayout.setTitle(user.name);
        }

        EventListPresenter presenter = new EventListPresenter(this);

        EventListFragment fragment = (EventListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);


        if (fragment == null) {
            // TODO: add a url here that would allow you to pull
            // all events a specific user has joined
            // for now we only use the events of the current user
            fragment = EventListFragment.newInstance(presenter, "Joined");
            fragment.setAdapter(new EventListAdapter(this, presenter));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        fragment.setHasOptionsMenu(false);

        presenter.addEventView(fragment);

        Toolbar settingsToolbar = (Toolbar) findViewById
                (R.id.profile_activity_toolbar);

        setSupportActionBar(settingsToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
