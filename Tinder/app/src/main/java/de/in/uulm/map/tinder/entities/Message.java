package de.in.uulm.map.tinder.entities;

import io.realm.RealmObject;

/**
 * Created by Jona on 01.05.2017.
 */

public class Message extends RealmObject {

    public String text;

    public long timestamp;

    public User creator;
}
