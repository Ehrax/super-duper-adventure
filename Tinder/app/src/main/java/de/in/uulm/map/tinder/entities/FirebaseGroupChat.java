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

    public FirebaseGroupChat(String eventId, String chatName, String timestamp, String img, String lastMessage) {

        this.eventId = eventId;
        this.chatName = chatName;
        this.timestamp = timestamp;
        this.img = img;
        this.lastMessage = lastMessage;
    }

    @Override
    public boolean equals(Object obj) {

        FirebaseGroupChat chat = (FirebaseGroupChat) obj;

        if (eventId.equals(chat.eventId)) {
            return true;
        }

        return false;
    }
}
