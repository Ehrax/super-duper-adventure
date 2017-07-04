package de.in.uulm.map.tinder.main.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by Jona on 26.06.17.
 */

public class EventActivity extends AppCompatActivity implements EventContract.Backend {

    public static String EVENT_EXTRA = "event";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        EventFragment fragment = (EventFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new EventFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        Event event = (Event) getIntent().getSerializableExtra(EVENT_EXTRA);

        EventPresenter mPresenter = new EventPresenter(this, fragment, this, event);
        fragment.setPresenter(mPresenter);
    }
}
