package de.in.uulm.map.tinder.main.eventlist;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.main.MainContract;
import de.in.uulm.map.tinder.util.BasePresenter;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface EventListContract {

    interface EventListView extends MainContract.MainView<EventListPresenter> {

        EventListAdapter getAdapter();
        String getGroupUri();
    }

    interface EventListPresenter extends BasePresenter {

        void loadEvents(EventListView view);

        void onDeleteClicked(Event e);
        void onJoinClicked(Event e);
        void onLeaveClicked(Event e);
        void onEditClicked(Event e);
    };
}
