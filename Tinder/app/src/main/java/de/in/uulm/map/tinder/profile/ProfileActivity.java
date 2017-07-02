package de.in.uulm.map.tinder.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by Jona on 02.07.17.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new ProfileFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        ProfilePresenter profilePresenter = new ProfilePresenter(fragment);
        fragment.setPresenter(profilePresenter);
    }
}
