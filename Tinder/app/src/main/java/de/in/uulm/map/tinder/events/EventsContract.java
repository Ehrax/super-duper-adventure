package de.in.uulm.map.tinder.events;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface EventsContract {

    interface TabView<T>  {

        /**
         * this is needed because onResume is not called if a tab has changed,
         * onPageSelected calls the corresponding fragment which is now visible.
         * Use this method if you want update the view.
         */
        void fragmentBecomesVisible();

        void setPresenter(T presenter);
    }

    interface EventsView extends BaseView<EventsPresenter>{};

    interface EventsPresenter extends BasePresenter{
        void setOnPageChangeListener(ViewPager viewPager, EventsPageAdapter
                adapter);

        void bottomNavSetOnNavigationItemSelected(BottomNavigationView view);

        void topNavOnOptionSelected(MenuItem item);
    };

}
