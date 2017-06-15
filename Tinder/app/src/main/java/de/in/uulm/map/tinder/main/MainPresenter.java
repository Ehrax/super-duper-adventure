package de.in.uulm.map.tinder.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.login.LoginActivity;

/**
 * Created by Jona on 21.05.2017.
 */

public class MainPresenter implements MainContract.MainPresenter {

    private Context mContext;
    private MainContract.Backend mBackend;

    public MainPresenter(Context ctxt, MainContract.Backend backend) {
        mContext=ctxt;
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
        if (item.getItemId() == R.id.top_nav_sign_out){
            SharedPreferences sharedPrefs = mContext.getSharedPreferences
                    (mContext.getString(R.string.store_account),Context
                            .MODE_PRIVATE);
            sharedPrefs.edit().clear().apply();
            mBackend.startActivity(new Intent(mContext, LoginActivity.class));
            return true;
        }
        return false;
    }

    public void onPageSelected() {

    }
}
