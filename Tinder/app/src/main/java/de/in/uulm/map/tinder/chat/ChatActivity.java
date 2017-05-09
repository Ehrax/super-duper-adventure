package de.in.uulm.map.tinder.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.in.uulm.map.tinder.entities.Event;

import io.realm.Realm;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatActivity extends AppCompatActivity {

    public final static String EVENT_ID = "event_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ChatFragment fragment = new ChatFragment();
        ChatPresenter presenter = new ChatPresenter(fragment);

        int event_id = getIntent().getIntExtra(EVENT_ID, -1);

        Realm realm = Realm.getDefaultInstance();
        Event event = realm.where(Event.class).equalTo("id", event_id).findFirst();

        ChatAdapter adapter = new ChatAdapter(event, presenter);

        fragment.setAdapter(adapter);
    }
}
