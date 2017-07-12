package de.in.uulm.map.tinder.main.groupchat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;

import de.in.uulm.map.tinder.chat.ChatActivity;
import de.in.uulm.map.tinder.chat.ChatFragment;
import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.main.MainActivity;
import de.in.uulm.map.tinder.network.DefaultErrorListener;
import de.in.uulm.map.tinder.network.EventRequest;
import de.in.uulm.map.tinder.network.Network;
import de.in.uulm.map.tinder.util.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatPresenter implements GroupChatContract.Presenter {

    private AppCompatActivity mActivity;

    private GroupChatContract.View mView;

    private final FirebaseDatabase db;

    private final DatabaseReference root;

    public GroupChatPresenter(AppCompatActivity activity, GroupChatContract
            .View view) {

        mActivity = activity;
        mView = view;
        db = FirebaseDatabase.getInstance();
        root = db.getReference().getRoot();
    }

    @Override
    public void start() {
        FirebaseHelper helper = new FirebaseHelper();

        FirebaseGroupChat chat = new FirebaseGroupChat();
        chat.eventId = "1077";
        chat.chatName = "Making some cool stuff here";
        chat.lastMessage = "Hello World!";
        chat.timestamp = "2017-07-08T21:00:00";

        helper.createGroup(chat);
    }

    @Override
    public void loadGroupChats(final ArrayList<Event> events) {

        for (Event e : events) {
            root.child(e.id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    FirebaseGroupChat chat = dataSnapshot.getValue
                            (FirebaseGroupChat.class);

                    if (chat != null) {
                       mView.getAdapter().addGroupChat(chat);
                    } else {
                        mView.getAdapter().setGroupChats(new ArrayList<FirebaseGroupChat>());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    // TODO better error response
                    Log.i(TAG, databaseError.getMessage());
                }
            });
        }

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                FirebaseGroupChat chat = dataSnapshot.getValue
                        (FirebaseGroupChat.class);

                mView.getAdapter().updateChat(chat);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                FirebaseGroupChat chat = dataSnapshot.getValue
                        (FirebaseGroupChat.class);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void loadEvents() {

        if (ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

        EventRequest reqJoined = EventRequest.newInstance(
                "Joined",
                mActivity,
                new Response.Listener<List<Event>>() {
                    @Override
                    public void onResponse(List<Event> response) {

                        loadGroupChats((ArrayList<Event>) response);
                    }
                },
                new DefaultErrorListener(mActivity));


        Network.getInstance(mActivity.getApplicationContext())
                .getRequestQueue().add(reqJoined);
    }

    @Override
    public void openMessageActivity(String eventId, String eventName) {

        Intent intent = new Intent(mActivity, ChatActivity.class);
        intent.putExtra(ChatFragment.EVENT_ID, eventId);
        intent.putExtra(ChatFragment.EVENT_NAME, eventName);
        mActivity.startActivity(intent);
    }
}
