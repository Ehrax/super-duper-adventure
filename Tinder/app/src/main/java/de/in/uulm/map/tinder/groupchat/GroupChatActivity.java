package de.in.uulm.map.tinder.groupchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.chat.ChatFragment;
import de.in.uulm.map.tinder.util.ActivityUtils;

public class GroupChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        GroupChatFragment fragment = (GroupChatFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new GroupChatFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        GroupChatPresenter presenter = new GroupChatPresenter(fragment);
        fragment.setPresenter(presenter);

        GroupChatAdapter adapter = new GroupChatAdapter(presenter);
        fragment.setAdapter(adapter);
    }
}
