package de.in.uulm.map.tinder.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private final RealmList<Message> mMessages;

    private final ChatContract.Presenter mPresenter;

    public ChatAdapter(Event event, ChatContract.Presenter presenter) {

        mMessages = event.messages;
        mPresenter = presenter;

        mMessages.addChangeListener(new RealmChangeListener<RealmList<Message>>() {
            @Override
            public void onChange(RealmList<Message> element) {

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.chat_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Message message = mMessages.get(position);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        holder.mUserName.setText(message.creator.name);
        holder.mText.setText(message.text);
        holder.mTime.setText(format.format(new Date(message.timestamp)));
    }

    @Override
    public int getItemCount() {

        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mUserName;
        TextView mText;
        TextView mTime;

        ViewHolder(View view) {

            super(view);

            mUserName = (TextView) view.findViewById(R.id.chat_message_user);
            mText = (TextView) view.findViewById(R.id.chat_message_text);
            mTime = (TextView) view.findViewById(R.id.chat_message_timestamp);
        }
    }
}
