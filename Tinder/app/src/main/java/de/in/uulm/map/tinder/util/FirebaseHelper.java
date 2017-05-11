package de.in.uulm.map.tinder.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class FirebaseHelper {

    private DatabaseReference mDatabase;

    public FirebaseHelper() {

        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * use this method if a user has created a group to create a new
     * groupchat in firebase, the user is automatically joining the chat
     *
     * @param event see @Event.class
     */
    private void createGroup(Event event) {

        DatabaseReference root = mDatabase.getRoot();

        Map<String, Object> map = new HashMap<>();
        map.put("room-id-" + event.id, "");
        root.updateChildren(map);
    }

    /**
     * use this method to write messages into a group
     *
     * @param user    see @User.class
     * @param message the message the user wants to send
     * @param eventId the event id the user is writing to
     */
    private void writeMessageToGroup(User user, String message, long eventId) {

        DatabaseReference chat = mDatabase.child("room-id-" + eventId);

        Map<String, Object> map = new HashMap<>();
        map.put("name", user.name);
        map.put("message", message);
        chat.updateChildren(map);
    }
}
