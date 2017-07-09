package de.in.uulm.map.tinder.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.in.uulm.map.tinder.R;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatFragment extends Fragment implements ChatContract.View {

    private ChatAdapter mAdapter;

    private ChatContract.Presenter mPresenter;

    public final static String EVENT_ID = "event_id";
    public final static String EVENT_NAME = "event_name";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.chat_messages, container, false);
        final EditText editText = (EditText) view.findViewById(R.id.chat_input_box);

        String eventName = getActivity().getIntent().getStringExtra(EVENT_NAME);
        final String eventId = getActivity().getIntent().getStringExtra(EVENT_ID);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        toolbar.setTitle(eventName);
        activity.setSupportActionBar(toolbar);

        Button sendButton = (Button) view.findViewById(R.id.chat_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onSendButton(editText.getText().toString(), eventId);
            }
        });

        RecyclerView recycler_view =
                (RecyclerView) view.findViewById(R.id.chat_message_list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(true);

        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(manager);


        mPresenter.start(); // TODO remove this after things got wired up
        mPresenter.loadMessages(eventId);

        return view;
    }

    public void setAdapter(ChatAdapter adapter) {

        mAdapter = adapter;
    }


    @Override
    public void setPresenter(ChatContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public ChatAdapter getAdapter() {

        return mAdapter;
    }

    // TODO handling inflating menu bar
}
