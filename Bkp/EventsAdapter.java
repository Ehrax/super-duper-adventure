package de.in.uulm.map.tinder.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Jona on 04.05.17.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    final public RealmResults<Event> mEvents;

    final private EventsContract.EventsPresenter mPresenter;

    public EventsAdapter(RealmResults<Event> events, EventsContract.EventsPresenter presenter) {

        mEvents = events;
        mPresenter = presenter;

        mEvents.addChangeListener(new RealmChangeListener<RealmResults<Event>>() {
            @Override
            public void onChange(RealmResults<Event> element) {
                notifyDataSetChanged();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImage;
        public TextView mTitle;
        public TextView mUserCount;
        public TextView mTime;
        public TextView mDescription;
        public Button mJoinButton;
        public Button mLeaveButton;
        public Button mMapButton;

        public ViewHolder(View itemView) {

            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.event_card_image);
            mTitle = (TextView) itemView.findViewById(R.id.event_card_title);
            mUserCount = (TextView) itemView.findViewById(R.id.event_card_user_count);
            mTime = (TextView) itemView.findViewById(R.id.event_card_time);
            mDescription = (TextView) itemView.findViewById(R.id.event_card_description);
            mJoinButton = (Button) itemView.findViewById(R.id.event_card_join);
            mLeaveButton = (Button) itemView.findViewById(R.id.event_card_leave);
            mMapButton = (Button) itemView.findViewById(R.id.event_card_chat);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.event_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Event e = mEvents.get(position);
        holder.mTitle.setText(e.title);
        holder.mDescription.setText(e.description);
        holder.mUserCount.setText(
                e.participants.size() + "/" + e.max_user_count);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        holder.mTime.setText(format.format(new Date(e.end_date)) + " left");
    }

    @Override
    public int getItemCount() {

        return mEvents.size();
    }
}
