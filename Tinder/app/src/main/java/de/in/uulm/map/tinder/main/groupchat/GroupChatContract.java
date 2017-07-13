package de.in.uulm.map.tinder.main.groupchat;

import android.app.Activity;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.main.MainContract;
import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatContract {

    interface Presenter extends BasePresenter {
        void loadGroupChats(ArrayList<Event> events);
        void loadEvents();
        void openMessageActivity(String eventId, String eventName);
    }

    interface View extends MainContract.MainView<Presenter> {
        GroupChatAdapter getAdapter();
    }

}
