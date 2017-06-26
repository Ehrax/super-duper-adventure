package de.in.uulm.map.tinder.main;

import android.content.Intent;
import android.view.MenuItem;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by Jona on 21.05.2017.
 */

public interface MainContract {

    interface MainView<T> extends BaseView<T> {

        /**
         * This is needed because onResume is not called if a tab has changed,
         * onPageSelected calls the corresponding fragment which is now visible.
         * Use this method if you want update the view.
         */
        void onFragmentBecomesVisible();
    }

    interface MainPresenter extends BasePresenter {

        boolean topNavOnOptionSelected(MenuItem item);

        void onPageSelected();
    }

    interface Backend {

        void startActivity(Intent intent);
    }
}
