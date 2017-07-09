package de.in.uulm.map.tinder.chat;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by Jona on 08.05.17.
 */

public interface ChatContract {

    interface Presenter extends BasePresenter{

        void onSendButton(String inputText, String eventId);
        void loadMessages(String eventId);
    }

    interface View extends BaseView<Presenter> {
        ChatAdapter getAdapter();
    }
}
