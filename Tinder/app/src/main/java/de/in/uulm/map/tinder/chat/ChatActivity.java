package de.in.uulm.map.tinder.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatActivity extends AppCompatActivity {



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

        ChatPresenter presenter = new ChatPresenter(fragment, this);
        fragment.setPresenter(presenter);

        ChatAdapter adapter = new ChatAdapter(presenter);
        fragment.setAdapter(adapter);
    }
}
