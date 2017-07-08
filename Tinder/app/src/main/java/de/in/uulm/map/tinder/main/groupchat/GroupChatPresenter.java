package de.in.uulm.map.tinder.main.groupchat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.Manifest; import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
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

    public GroupChatPresenter(AppCompatActivity activity, GroupChatContract
            .View view) {

        mActivity = activity;
        mView = view;

        // TODO is this really a bad idea :D?

    }

    @Override
    public void start() {

        FirebaseHelper helper = new FirebaseHelper();

        FirebaseGroupChat chat = new FirebaseGroupChat();
        chat.eventId = "1071";
        chat.chatName = "Making some cool stuff here";
        chat.lastMessage = "Hello World!";
        chat.timestamp = "2017-078T21:00:00";

        FirebaseGroupChat chat2 = new FirebaseGroupChat();
        chat2.eventId = "1072";
        chat2.chatName = "My Second Test Event";
        chat2.lastMessage = "Roman are you stupid?";
        chat2.timestamp = "2017-07-08T23:00:00";

        helper.createGroup(chat);
        helper.createGroup(chat2);

    }

    @Override
    public void loadGroupChats(ArrayList<Event> events) {

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference root = db.getReference().getRoot();

        final ArrayList<FirebaseGroupChat> chats = new ArrayList<>();

        for (Event e : events) {
            root.child(e.id).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseGroupChat chat = dataSnapshot.getValue
                            (FirebaseGroupChat.class);

                    addGroupChatToAdapter(chat);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    // TODO better error response
                    Log.i(TAG, databaseError.getMessage());
                }
            });
        }
    }

    private void addGroupChatToAdapter(FirebaseGroupChat chat) {
        mView.getAdapter().addGroupChat(chat);
    }


    @Override
    public void loadEvents() {

        if (ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions((Activity) mActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

        EventRequest reqJoined = EventRequest.newInstance(
                "Joined",
                mActivity,
                new Response.Listener<List<Event>>() {
                    @Override
                    public void onResponse(List<Event> response) {

                        loadGroupChats((ArrayList<Event>) response) ;
                    }
                },
                new DefaultErrorListener(mActivity));


        Network.getInstance(mActivity.getApplicationContext())
                .getRequestQueue().add(reqJoined);
    }
}
