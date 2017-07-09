package de.in.uulm.map.tinder.main.groupchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.util.AsyncImageLoader;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter
        .ViewHolder> {

    private final GroupChatContract.Presenter mPresenter;

    private final Context mContext;

    public ArrayList<FirebaseGroupChat> mGroupChats;


    public GroupChatAdapter(GroupChatContract.Presenter presenter, Context
            context) {

        mPresenter = presenter;
        mGroupChats = new ArrayList<>();
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_group_chat_item, parent, false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final FirebaseGroupChat groupChat = mGroupChats.get(position);
        holder.mGroupNameTextView.setText(groupChat.chatName);
        holder.mGroupLastMessageTextView.setText(groupChat.lastMessage);

        SimpleDateFormat formatParse = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat formatPresent = new SimpleDateFormat("HH:mm");

        try {
            long date = formatParse.parse(groupChat.timestamp).getTime();
            holder.mGroupLastTimestampTextView.setText(formatPresent.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (groupChat.img != null) {
            String uri = mContext.getString(R.string.API_base);
            uri += mContext.getString(R.string.API_event_image);
            new AsyncImageLoader(uri,
                    new WeakReference<ImageView>(holder
                            .mGroupCircleImageView), mContext).execute();
        } else {
            holder.mGroupCircleImageView.setImageResource(R.drawable.image_placeholder);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mPresenter.openMessageActivity(groupChat.eventId, groupChat.chatName);
            }
        });
    }

    public void setGroupChats(ArrayList<FirebaseGroupChat> chats) {
        mGroupChats = chats;
        notifyDataSetChanged();
    }

    public void addGroupChat(FirebaseGroupChat chat) {

        if (!mGroupChats.contains(chat)) {
            mGroupChats.add(chat);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mGroupChats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView mGroupCircleImageView;
        public TextView mGroupNameTextView;
        public TextView mGroupLastMessageTextView;
        public TextView mGroupLastTimestampTextView;

        ViewHolder(View itemView) {

            super(itemView);
            mGroupCircleImageView = (CircleImageView) itemView.findViewById(R
                    .id.group_chat_img);
            mGroupNameTextView = (TextView) itemView.findViewById(R.id
                    .group_chat_name);
            mGroupLastMessageTextView = (TextView) itemView.findViewById(R.id
                    .group_chat_last_message);
            mGroupLastTimestampTextView = (TextView) itemView.findViewById(R
                    .id.group_chat_last_timestamp);
        }
    }

}
