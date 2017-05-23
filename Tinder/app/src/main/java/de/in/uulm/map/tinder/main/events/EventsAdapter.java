package de.in.uulm.map.tinder.main.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.AsyncImageLoader;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jona on 04.05.17.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final Context mContext;

    final public ArrayList<Event> mEvents;

    final private EventsContract.EventsPresenter mPresenter;

    public EventsAdapter(Context context, ArrayList<Event> events, EventsContract.EventsPresenter presenter) {

        mContext = context;
        mEvents = events;
        mPresenter = presenter;
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

        if(e.image != null && e.image.path != null) {
            new AsyncImageLoader(e.image.path,
                    new WeakReference<>(holder.mImage),
                    mContext).execute();
        }

        long left = e.end_date - new Date().getTime();
        long hours = left / 3600000;
        long minutes = (left % 3600000) / 60000;

        holder.mTime.setText(String.format("%02d:%02d left", hours, minutes));
    }

    @Override
    public int getItemCount() {

        return mEvents.size();
    }
}
