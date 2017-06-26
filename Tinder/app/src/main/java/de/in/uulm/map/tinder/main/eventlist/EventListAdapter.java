package de.in.uulm.map.tinder.main.eventlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.User;
import de.in.uulm.map.tinder.util.AsyncImageDecoder;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jona on 04.05.17.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private final Context mContext;

    private final EventListContract.EventsPresenter mPresenter;

    public ArrayList<Event> mEvents;

    public EventListAdapter(Context context, EventListContract.EventsPresenter presenter) {

        mContext = context;
        mEvents = new ArrayList<>();
        mPresenter = presenter;
    }

    public void setEvents(ArrayList<Event> events) {

        mEvents = events;
        notifyDataSetChanged();
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
        public Button mDeleteButton;
        public Button mEditButton;

        public ViewHolder(View itemView) {

            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.event_card_image);
            mTitle = (TextView) itemView.findViewById(R.id.event_card_title);
            mUserCount = (TextView) itemView.findViewById(R.id.event_card_user_count);
            mTime = (TextView) itemView.findViewById(R.id.event_card_time);
            mDescription = (TextView) itemView.findViewById(R.id.event_card_description);
            mJoinButton = (Button) itemView.findViewById(R.id.event_card_join);
            mLeaveButton = (Button) itemView.findViewById(R.id.event_card_leave);
            mMapButton = (Button) itemView.findViewById(R.id.event_card_map);
            mDeleteButton = (Button) itemView.findViewById(R.id.event_card_delete);
            mEditButton = (Button) itemView.findViewById(R.id.event_card_edit);
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

        final Event e = mEvents.get(position);
        holder.mTitle.setText(e.title);
        holder.mDescription.setText(e.description);
        holder.mUserCount.setText(
                e.participants.size() + "/" + e.max_user_count);

        new AsyncImageDecoder(e.image, new WeakReference<>(holder.mImage))
                .execute();

        long end_date = new Date().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            end_date = format.parse(e.start_date).getTime();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        long left = end_date - new Date().getTime();
        long hours = left / 3600000;
        long minutes = (left % 3600000) / 60000;

        holder.mTime.setText(String.format("%02d:%02d left", hours, minutes));

        SharedPreferences accountPrefs = mContext.getSharedPreferences(
                mContext.getString(R.string.store_account),
                Context.MODE_PRIVATE);

        final String userName = accountPrefs.getString(
                mContext.getString(R.string.store_username), "");

        boolean currentUserParticipates = false;
        for(User u : e.participants) {
            if(u.name.equals(userName)) {
                currentUserParticipates = true;
                break;
            }
        }

        holder.mJoinButton.setVisibility(
                currentUserParticipates ? View.GONE : View.VISIBLE);
        holder.mLeaveButton.setVisibility(
                currentUserParticipates && !e.creator.name.equals(userName)
                        ? View.VISIBLE : View.GONE);
        holder.mMapButton.setVisibility(
                currentUserParticipates ? View.VISIBLE : View.GONE);
        holder.mDeleteButton.setVisibility(
                e.creator.name.equals(userName) ? View.VISIBLE : View.GONE);
        holder.mEditButton.setVisibility(
                e.creator.name.equals(userName) ? View.VISIBLE : View.GONE);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.onDeleteClicked(e);
            }
        });

        holder.mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.onJoinClicked(e);
            }
        });

        holder.mLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.onLeaveClicked(e);
            }
        });

        holder.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.onEditClicked(e);
            }
        });

        holder.mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+e.latitude+","+e.longitude+"("+e.title+")");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(mapIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mEvents.size();
    }
}
