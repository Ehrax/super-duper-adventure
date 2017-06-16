package de.in.uulm.map.tinder.main.groupchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.main.MainPageAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatFragment extends Fragment implements GroupChatContract.View {

    private GroupChatAdapter mAdapter;

    private GroupChatContract.Presenter mPresenter;

    public static GroupChatFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(MainPageAdapter.TAB_TITLE, title);
        
        GroupChatFragment fragment = new GroupChatFragment();
        fragment.setArguments(args);
        
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_group_chat, container,
                false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id
                .group_chat_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                (getContext()));
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    public void setAdapter(GroupChatAdapter adapter) {

        mAdapter = adapter;
    }

    @Override
    public void setPresenter(GroupChatContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void onFragmentBecomesVisible() {

    }
}
