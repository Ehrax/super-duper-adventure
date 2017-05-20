package de.in.uulm.map.tinder.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jona on 01.05.2017.
 */

public class Event extends RealmObject {

<<<<<<< HEAD
    @PrimaryKey
    public long id;
=======

    @PrimaryKey
    public long id;

    public String title;

>>>>>>> 1df27898e04edd594d3268e9153c0f27f3710be2

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
