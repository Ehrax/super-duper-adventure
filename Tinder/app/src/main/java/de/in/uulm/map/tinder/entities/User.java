package de.in.uulm.map.tinder.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jona on 01.05.2017.
 */

public class User extends RealmObject {

    @PrimaryKey
    public long id;

    public String name;

    public Image image;

    public RealmList<Event> events = new RealmList<>();
}
