package de.in.uulm.map.tinder.main;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.settings.SettingsActivity;

/**
 * Created by Jona on 21.05.2017.
 */

public class MainPresenter implements MainContract.MainPresenter {

    Context mContext;

    public MainPresenter(Context context) {

        mContext = context;
    }

    @Override
    public void start() {

    }

    /**
     * Note: this needs to return true when the click event has been handled.
     *
     * @param item the menu item on which was clicked
     * @return boolean, true: event handled, false: event not handled
     */
    @Override
    public boolean topNavOnOptionSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_nav_settings: {
                Intent intent = new Intent(mContext, SettingsActivity.class);
                mContext.startActivity(intent);

                return true;
            }
        }

        return false;
    }

    public void onPageSelected() {

    }
}
