package de.in.uulm.map.tinder.main.events;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.main.MainContract;
import de.in.uulm.map.tinder.util.BasePresenter;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface EventsContract {

    interface EventsView extends MainContract.MainView<EventsPresenter> {

        EventsAdapter getAdapter();
    }

    interface EventsPresenter extends BasePresenter {

        void loadEvents();

        void onDeleteClicked(Event e);
        void onJoinClicked(Event e);
        void onLeaveClicked(Event e);
    };
}
