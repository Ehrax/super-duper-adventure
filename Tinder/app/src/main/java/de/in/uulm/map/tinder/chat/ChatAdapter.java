package de.in.uulm.map.tinder.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Message> mMessages;

    private final ChatContract.Presenter mPresenter;

    private Context mContext;

    public ChatAdapter(ChatContract.Presenter presenter, Context context) {

        mMessages = new ArrayList<>();
        mPresenter = presenter;
        mContext = context;
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

        holder.mText.setText(message.mText);

        holder.mUserName.setText(message.mUserName);
        SimpleDateFormat formatParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat formatPresent = new SimpleDateFormat("HH:mm");

        try {
            long date = formatParse.parse(message.mTimestamp).getTime();
            holder.mTime.setText(formatPresent.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                holder.mLayout.getLayoutParams();

        SharedPreferences sharedPrefs = mContext.getSharedPreferences
                (mContext.getString(R.string.store_account), Context.MODE_PRIVATE);

        String creatorId =  sharedPrefs.getString(mContext.getString(R.string
                .store_token), "");

        if (message.mUid == creatorId) {
            params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            holder.mLayout.setBackgroundResource(R.color.color_chat_bubble_own);
        } else {
            params.removeRule(RelativeLayout.ALIGN_PARENT_END);
            holder.mLayout.setBackgroundResource(R.color.color_chat_bubble_other);
        }

        holder.mLayout.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {

        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mUserName;
        TextView mText;
        TextView mTime;
        LinearLayout mLayout;

        ViewHolder(View view) {

            super(view);

            mLayout = (LinearLayout) view.findViewById(R.id.chat_message_layout);
            mUserName = (TextView) view.findViewById(R.id.chat_message_user);
            mText = (TextView) view.findViewById(R.id.chat_message_text);
            mTime = (TextView) view.findViewById(R.id.chat_message_timestamp);
        }
    }

    public void addMessage(Message message) {

        mMessages.add(message);
        notifyDataSetChanged();
    }
}
