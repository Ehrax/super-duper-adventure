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
    public void createGroup(FirebaseGroupChat groupChat) {

        DatabaseReference root = mDatabase.getRoot();

        HashMap<String, Object> chat = new HashMap<>();
        chat.put("eventId", groupChat.eventId);
        chat.put("chatName", groupChat.chatName);
        chat.put("timestamp", groupChat.timestamp);
        chat.put("img", groupChat.img);
        chat.put("lastMessage", groupChat.lastMessage);

        root.child(groupChat.eventId).setValue(chat);
    }

    public void updateGroup(String chatId, HashMap<String, Object> updates) {

        DatabaseReference chatRef = mDatabase.getRoot().child(chatId);
        chatRef.updateChildren(updates);
    }

    /**
     * use this method to write messages into a group
     *
     * @param eventId the group event id
     * @param message See @Message.class
     */
    public void writeMessageToGroup(String eventId, Message message) {

        DatabaseReference chatRef = mDatabase.getRoot().child(eventId);
        DatabaseReference messagesRef = chatRef.child("messages");

        String key = messagesRef.push().getKey();

        HashMap<String, Object> sendingMessage = new HashMap<>();
        sendingMessage.put("userName", message.mUserName);
        sendingMessage.put("userId", message.mUid);
        sendingMessage.put("userImage", message.mUserImg);
        sendingMessage.put("timestamp", message.mTimestamp);
        sendingMessage.put("text", message.mText);

        messagesRef.child(key).setValue(sendingMessage);
    }
}
