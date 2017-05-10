package de.in.uulm.map.tinder.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_messages, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.chat_input_box);

        Button sendButton = (Button) view.findViewById(R.id.chat_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onSendButton(editText.getText().toString());
            }
        });

        RecyclerView recycler_view =
                (RecyclerView) view.findViewById(R.id.chat_message_list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(true);

        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(manager);

        return view;
    }

    public void setAdapter(ChatAdapter adapter) {

        mAdapter = adapter;
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {

        mPresenter = presenter;
    }
}
