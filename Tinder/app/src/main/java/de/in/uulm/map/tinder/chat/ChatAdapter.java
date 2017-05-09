package de.in.uulm.map.tinder.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Message;

import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private final RealmList<Message> mMessages;

    private final ChatContract.Presenter mPresenter;

    public ChatAdapter(Event event,
                       ChatContract.Presenter presenter) {

        if(event == null) {
            mMessages = new RealmList<>();
        } else {
            mMessages = event.messages;
        }

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
