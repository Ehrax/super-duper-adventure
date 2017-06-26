package de.in.uulm.map.tinder.tinderview;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.*;
import com.mindorks.placeholderview.annotations.swipe.*;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.util.AsyncImageDecoder;

import java.lang.ref.WeakReference;

/**
 * Created by maxka on 26.06.2017.
 */

@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.img_tinder_event)
    private ImageView mImgEvent;

    @View(R.id.txt_tinder_event_name)
    private TextView mTxtEventName;

    @View(R.id.txt_tinder_location)
    private TextView mTxtLocation;

    private Event mEvent;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, Event event, SwipePlaceHolderView
            swipeView) {

        mContext = context;
        mEvent = event;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() {

        new AsyncImageDecoder(mEvent.image, new WeakReference<>(mImgEvent))
                .execute();
        mTxtEventName.setText(mEvent.title + ", " + mEvent
                .getFormattedStartDate() + R.string.at_time + mEvent.getFormattedStartTime());
        mTxtLocation.setText(mEvent.location);
    }

    @SwipeOut
    private void onSwipedOut() {

        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {

        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {

        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {

        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {

        Log.d("EVENT", "onSwipeOutState");
    }
}
