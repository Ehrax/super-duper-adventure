package de.in.uulm.map.tinder.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.entities.Message;
import de.in.uulm.map.tinder.entities.User;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class FirebaseHelper {

    private DatabaseReference mDatabase;
    public static final String CHILD_GROUP_MESSAGES = "messages";
    public static final String GROUP_CHATS = "group-chats";

    public FirebaseHelper() {

        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * use this method if a user has created a group to create a new
     * groupchat in firebase, the user is automatically joining the chat
     *
     * @param groupChat see @FirebaseGroupChat.class
     */
    public void createGroup(FirebaseGroupChat groupChat, String userId) {

        DatabaseReference root = mDatabase.getRoot();

        root.child(userId).child(GROUP_CHATS).child(groupChat.eventId).setValue
                (groupChat);
    }

    /**
     * use this method to write messages into a group
     *
     * @param eventId the group event id
     * @param message See @Message.class
     */
    public void writeMessageToGroup(String eventId, Message message, String
            userId) {

        DatabaseReference chat = mDatabase.child(userId).child("group-chats")
                .child(eventId);

        String key = chat.child(CHILD_GROUP_MESSAGES).push().getKey();
        chat.child(CHILD_GROUP_MESSAGES).child(key).setValue(message);
    }
}
