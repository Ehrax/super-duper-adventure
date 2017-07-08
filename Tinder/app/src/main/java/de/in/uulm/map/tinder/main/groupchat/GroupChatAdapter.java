package de.in.uulm.map.tinder.main.groupchat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.util.FirebaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter
        .ViewHolder> {

    private final GroupChatContract.Presenter mPresenter;

    public ArrayList<FirebaseGroupChat> mGroupChats;


    public GroupChatAdapter(GroupChatContract.Presenter presenter) {

        mPresenter = presenter;
        mGroupChats = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_group_chat_item, parent, false);

        // TODO fetch here all group chats the user has joined

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final FirebaseGroupChat groupChat = mGroupChats.get(position);
        holder.mGroupNameTextView.setText(groupChat.chatName);
        holder.mGroupLastMessageTextView.setText(groupChat.lastMessage);

        // parse millisec time into string
        Date date = new Date(Long.parseLong(groupChat.timestamp));
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateFormatted = formatter.format(date);

        holder.mGroupLastTimestampTextView.setText(dateFormatted);

        // set image from group chat if there is an img
        if (groupChat.img != null) {
            byte[] bytes = Base64.decode(groupChat.img, Base64.DEFAULT);
            holder.mGroupCircleImageView.setImageBitmap(
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        }
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
