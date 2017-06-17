package de.in.uulm.map.tinder.main.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jona on 04.05.17.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final Context mContext;

    private final EventsContract.EventsPresenter mPresenter;

    public ArrayList<Event> mEvents;

    public EventsAdapter(Context context, EventsContract.EventsPresenter presenter) {

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

        // TODO: do decoding in another thread

        if(e.image != null) {
            byte[] bytes = Base64.decode(e.image, Base64.DEFAULT);
            holder.mImage.setImageBitmap(
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }

        long end_date = new Date().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            end_date = format.parse(e.end_date).getTime();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        long left = end_date - new Date().getTime();
        long hours = left / 3600000;
        long minutes = (left % 3600000) / 60000;

        holder.mTime.setText(String.format("%02d:%02d left", hours, minutes));

        // TODO: Get real user ...

        final User user = new User();

        holder.mJoinButton.setVisibility(
                e.participants.contains(user) ? View.GONE : View.VISIBLE);
        holder.mLeaveButton.setVisibility(
                e.participants.contains(user) && e.creator != user
                        ? View.VISIBLE : View.GONE);
        holder.mMapButton.setVisibility(
                e.participants.contains(user) ? View.VISIBLE : View.GONE);
        holder.mDeleteButton.setVisibility(
                e.creator == user ? View.VISIBLE : View.GONE);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Issue real delete request ...
            }
        });

        holder.mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Issue real join request ...
            }
        });

        holder.mLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Issue real leave request ...
            }
        });

        holder.mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+e.latitude+","+e.longitude);
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
