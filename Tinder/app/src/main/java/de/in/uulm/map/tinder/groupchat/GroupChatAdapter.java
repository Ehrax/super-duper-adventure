package de.in.uulm.map.tinder.groupchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.chat.ChatContract;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter
        .ViewHolder> {

    private final GroupChatContract.Presenter mPresenter;

    public GroupChatAdapter(GroupChatContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {

            super(itemView);
        }
    }

}
