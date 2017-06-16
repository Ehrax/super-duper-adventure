package de.in.uulm.map.tinder.main.groupchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import de.in.uulm.map.tinder.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_group_chat_item, parent, false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView groupCircleImageView;
        public TextView groupNameTextView;
        public TextView groupLastMessageTextView;
        public TextView groupLastTimestampTextView;

        ViewHolder(View itemView) {

            super(itemView);
            groupCircleImageView = (CircleImageView) itemView.findViewById(R
                    .id.group_chat_img);
            groupNameTextView = (TextView) itemView.findViewById(R.id.group_chat_name);
            groupLastMessageTextView = (TextView) itemView.findViewById(R.id
                    .group_chat_last_message);
            groupLastTimestampTextView = (TextView) itemView.findViewById(R
                    .id.group_chat_last_timestamp);
        }
    }

}
