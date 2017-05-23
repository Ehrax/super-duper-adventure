package de.in.uulm.map.tinder.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatActivity extends AppCompatActivity {

    public final static String EVENT_ID = "event_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        ChatFragment fragment = (ChatFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(fragment == null) {
            fragment = new ChatFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        int event_id = getIntent().getIntExtra(EVENT_ID, 1);

        // TODO: dummy event, replace with real event
        Event e = new Event();

        ChatPresenter presenter = new ChatPresenter(fragment, e);
        fragment.setPresenter(presenter);

        ChatAdapter adapter = new ChatAdapter(e, presenter);
        fragment.setAdapter(adapter);
    }
}
