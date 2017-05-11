package de.in.uulm.map.tinder.groupchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatFragment extends Fragment implements GroupChatContract.View {

    private GroupChatAdapter mAdapter;

    private GroupChatContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setAdapter(GroupChatAdapter adapter) {

        mAdapter = adapter;
    }

    @Override
    public void setPresenter(GroupChatContract.Presenter presenter) {

        mPresenter = presenter;
    }
}
