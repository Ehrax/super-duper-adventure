package de.in.uulm.map.tinder.main.groupchat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.util.Log;

import de.in.uulm.map.tinder.entities.FirebaseGroupChat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatPresenter implements GroupChatContract.Presenter {

    private Context mContext;

    private GroupChatContract.View mView;


    public GroupChatPresenter(GroupChatContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void setupGroupChat() {
        final String mUserId = ";asdkjfa;dkfjas";
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference chatsChild = db.getReference(mUserId +
                "/group-chats");

        ArrayList<FirebaseGroupChat> groupChat = new ArrayList<>();

        chatsChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Object> rootMap = (HashMap<String, Object>)
                        dataSnapshot.getValue();

                for (Map.Entry<String, Object> rootEntry : rootMap.entrySet()) {
                    HashMap<String, Object> childMap = (HashMap<String, Object>)
                            rootEntry.getValue();

                    for (Map.Entry<String, Object> childEntry : childMap
                            .entrySet ()) {

                        FirebaseGroupChat chat = new FirebaseGroupChat();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
