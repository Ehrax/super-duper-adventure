package de.in.uulm.map.tinder.chat;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.R;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatFragment extends Fragment implements ChatContract.View {

    private ChatContract.Presenter mPresenter;

    private ChatAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_messages, container, false);

        RecyclerView recycler_view =
                (RecyclerView) view.findViewById(R.id.chat_message_list);

        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

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
