package de.in.uulm.map.tinder.main.events;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import de.in.uulm.map.tinder.main.MainContract;
import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface EventsContract {

    interface EventsView extends MainContract.MainView<EventsPresenter> {

    }

    interface EventsPresenter extends BasePresenter {

    };
}
