package de.in.uulm.map.tinder.tinderview;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.WindowManager;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

import java.util.List;

/**
 * Created by maxka on 26.06.2017.
 */

public interface TinderViewContract {

    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter {

        List<Event> loadEvents();

        boolean topNavOnOptionSelected(MenuItem item);
    }

    interface Backend {

        void startActivity(Intent intent);

        void startActivityFlip(Intent intent);

    }
}
