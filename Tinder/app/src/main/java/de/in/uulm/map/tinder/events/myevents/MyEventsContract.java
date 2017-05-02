package de.in.uulm.map.tinder.events.myevents;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface MyEventsContract {

    interface MyEventsView extends BaseView<MyEventsPresenter> {};

    interface MyEventsPresenter extends BasePresenter{};
}
