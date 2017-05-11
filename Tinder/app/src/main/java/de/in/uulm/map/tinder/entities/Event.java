package de.in.uulm.map.tinder.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jona on 01.05.2017.
 */

public class Event extends RealmObject {

    public String title;

    @PrimaryKey
    public long id;


    public String description;

    public long end_date;

    public int max_user_count;

    public double longitude;

    public double latitude;

    public String category;

    public Image image;

    public User creator;

    public RealmList<User> participants = new RealmList<>();

    public RealmList<Message> messages = new RealmList<>();
}
