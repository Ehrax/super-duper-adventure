package de.in.uulm.map.tinder.events.myevents;

import de.in.uulm.map.tinder.events.EventsContract;
import de.in.uulm.map.tinder.util.BasePresenter;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface MyEventsContract {

    interface MyEventsView extends EventsContract.TabView<MyEventsPresenter> {};

    interface MyEventsPresenter extends BasePresenter{};
}
