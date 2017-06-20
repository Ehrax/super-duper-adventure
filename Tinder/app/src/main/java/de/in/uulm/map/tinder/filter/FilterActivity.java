package de.in.uulm.map.tinder.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by alexanderrasputin on 23.05.17.
 */

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        FilterFragment fragment = (FilterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new FilterFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);

        }

        FilterPresenter presenter = new FilterPresenter(this, fragment);
        fragment.setPresenter(presenter);
    }
}
