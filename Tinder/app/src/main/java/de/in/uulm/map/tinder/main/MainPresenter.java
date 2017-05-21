package de.in.uulm.map.tinder.main;

import android.view.MenuItem;

/**
 * Created by Jona on 21.05.2017.
 */

public class MainPresenter implements MainContract.MainPresenter {

    public MainPresenter() {

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

        return false;
    }

    public void onPageSelected() {

    }
}
