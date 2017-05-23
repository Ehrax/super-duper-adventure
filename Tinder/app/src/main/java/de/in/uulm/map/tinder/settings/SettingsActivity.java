package de.in.uulm.map.tinder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by alexanderrasputin on 23.05.17.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        SettingsFragment fragment = (SettingsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new SettingsFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);

        }

        SettingsPresenter presenter = new SettingsPresenter(fragment);
        fragment.setPresenter(presenter);
    }
}
