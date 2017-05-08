package de.in.uulm.map.tinder.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.entities.Message;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private final RealmResults<Message> mMessages;

    private final ChatContract.Presenter mPresenter;

    public ChatAdapter(RealmResults<Message> messages,
                       ChatContract.Presenter presenter) {

        mMessages = messages;
        mPresenter = presenter;

        messages.addChangeListener(new RealmChangeListener<RealmResults<Message>>() {
            @Override
            public void onChange(RealmResults<Message> element) {

                notifyDataSetChanged();
            }
        });
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

        ViewHolder(View view) {

            super(view);
        }
    }
}
