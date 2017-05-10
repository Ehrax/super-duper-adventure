package de.in.uulm.map.tinder.chat;

import android.util.Log;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Message;
import de.in.uulm.map.tinder.entities.User;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private final ChatContract.View mView;

    private final Event mEvent;

    public ChatPresenter(ChatContract.View view, Event event) {

        mView = view;
        mEvent = event;
    }

    @Override
    public void start() {

    }

    /**
     * This will be called when the send button in the chat gui is clicked.
     *
     * @param inputText the text which is currently in the input box
     */
    @Override
    public void onSendButton(String inputText) {

        Log.d("Send", inputText);

        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();

        realm.beginTransaction();

        Message message = new Message();
        message.creator = user;
        message.timestamp = new Date().getTime();
        message.text = inputText;

        mEvent.messages.add(message);

        realm.copyToRealm(mEvent);

        realm.commitTransaction();
    }
}
