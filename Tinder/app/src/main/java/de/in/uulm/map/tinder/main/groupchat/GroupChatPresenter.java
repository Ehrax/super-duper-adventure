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
    public ArrayList<FirebaseGroupChat> setupGroupChat() {

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        ArrayList<FirebaseGroupChat> groupChat = new ArrayList<>();



        return groupChat;
    }
}
