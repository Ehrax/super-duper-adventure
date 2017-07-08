package de.in.uulm.map.tinder.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.User;
import de.in.uulm.map.tinder.filter.FilterActivity;
import de.in.uulm.map.tinder.login.LoginActivity;
import de.in.uulm.map.tinder.main.event.EventActivity;
import de.in.uulm.map.tinder.profile.ProfileActivity;

/**
 * Created by Jona on 21.05.2017.
 */

public class MainPresenter implements MainContract.MainPresenter {

    private Context mContext;
    private MainContract.Backend mBackend;

    public MainPresenter(Context ctxt, MainContract.Backend backend) {

        mContext = ctxt;
        mBackend = backend;
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

        if (item.getItemId() == R.id.top_nav_filter) {
            Intent intent = new Intent(mContext, FilterActivity.class);
            mContext.startActivity(intent);
            return true;
        }

        if(item.getItemId() == R.id.top_nav_add) {
            Intent intent = new Intent(mContext, EventActivity.class);
            mContext.startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.top_nav_sign_out) {
            SharedPreferences sharedPrefs = mContext.getSharedPreferences
                    (mContext.getString(R.string.store_account), Context
                            .MODE_PRIVATE);
            sharedPrefs.edit().clear().apply();
            mBackend.startActivity(new Intent(mContext, LoginActivity.class));
            return true;
        }

        if(item.getItemId() == R.id.top_nav_account) {
            User user = new User();
            SharedPreferences sharedPrefs = mContext.getSharedPreferences
                    (mContext.getString(R.string.store_account),
                            Context.MODE_PRIVATE);
            user.name = sharedPrefs.getString(
                    mContext.getString(R.string.store_username), "No Name");
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra(ProfileActivity.EXTRA_USER, user);
            mBackend.startActivity(intent);
        }

        return false;
    }

    public void onPageSelected() {

    }
}
