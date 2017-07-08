package de.in.uulm.map.tinder.main.groupchat;

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
        ArrayList<FirebaseGroupChat> setupGroupChat();
    }

    interface View extends MainContract.MainView<Presenter> {
        GroupChatAdapter getAdapter();
    }

}
