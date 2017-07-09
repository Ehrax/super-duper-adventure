package de.in.uulm.map.tinder.chat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.entities.Message;
import de.in.uulm.map.tinder.util.FirebaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Created by Jona on 08.05.17.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private final ChatContract.View mView;

    private AppCompatActivity mActivity;


    public ChatPresenter(ChatContract.View view, AppCompatActivity activity) {

        mView = view;
        mActivity = activity;
    }

    @Override
    public void start() {

    }

    /**
     * This will be called when the send button in the chat gui is clicked.
     *
     * @param inputText the text which is currently in the input box
     */
    @Override
    public void onSendButton(String inputText, String eventId) {

        FirebaseHelper helper = new FirebaseHelper();
        Message msg = new Message();

        SharedPreferences sharedPrefs = mActivity.getSharedPreferences
                (mActivity.getString(R.string.store_account), Context.MODE_PRIVATE);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String currTime = format.format(System.currentTimeMillis());

        msg.mText = inputText;
        msg.mUserName = sharedPrefs.getString(
                mActivity.getString(R.string.store_username), "No Name");
        msg.mTimestamp = currTime;
        // TODO where do i get those user images? im not sure :D
        // msg.mUserImg = "";

        helper.writeMessageToGroup(eventId, msg);
    }

    /**
     * This method will be called from the ChatAdapter to load all Messages and
     * apply listeners to get new messages
     */
    @Override
    public void loadMessages(final String eventId) {

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference root = db.getReference().getRoot();
        final DatabaseReference messagesRef = root.child(eventId).child
                (FirebaseHelper.CHILD_GROUP_MESSAGES);

        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Message msg = dataSnapshot.getValue(Message.class);
                mView.getAdapter().addMessage(msg);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
