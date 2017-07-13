package de.in.uulm.map.tinder.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.in.uulm.map.tinder.entities.FirebaseGroupChat;
import de.in.uulm.map.tinder.entities.Message;
import de.in.uulm.map.tinder.entities.User;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class FirebaseHelper {

    private DatabaseReference mDatabase;
    public static final String CHILD_GROUP_MESSAGES = "messages";

    public FirebaseHelper() {

        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * use this method if a user has created a group to create a new
     * groupchat in firebase, the user is automatically joining the chat
     *
     * @param groupChat see @FirebaseGroupChat.class
     */
    public void createGroup(FirebaseGroupChat groupChat) {

        DatabaseReference root = mDatabase.getRoot();
        root.child(groupChat.eventId).setValue(groupChat);
    }

    public void updateGroup(String chatId, HashMap<String, Object> updates) {

        DatabaseReference chatRef = mDatabase.getRoot().child(chatId);
        chatRef.updateChildren(updates);
    }

    public void removeGroupChat(String chatId) {

        DatabaseReference chatRef = mDatabase.getRoot().child(chatId);
        chatRef.removeValue();
    }

    /**
     * use this method to write messages into a group
     *
     * @param eventId the group event id
     * @param message See @Message.class
     */
    public void writeMessageToGroup(String eventId, Message message) {

        DatabaseReference chatRef = mDatabase.getRoot().child(eventId);
        DatabaseReference messagesRef = chatRef.child(CHILD_GROUP_MESSAGES);

        String key = messagesRef.push().getKey();

        HashMap<String, Object> map = new HashMap<>();
        map.put("lastMessage", message.mText);
        map.put("timestamp", message.mTimestamp);
        map.put("messages/" + key, message);

        chatRef.updateChildren(map);
    }


}
