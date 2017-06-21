package de.in.uulm.map.tinder.entities;

import java.util.List;

/**
 * Created by alexanderrasputin on 16.06.17.
 */

public class FirebaseGroupChat {

    public String eventId;
    public String chatName;
    public String timestamp;
    public String img;
    public String lastMessage;

    public FirebaseGroupChat() {
        // default constructor required for calls to DataSnapshot.getValue
    }
}
