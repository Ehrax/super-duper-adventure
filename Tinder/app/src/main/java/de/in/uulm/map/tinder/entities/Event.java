package de.in.uulm.map.tinder.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jona on 01.05.2017.
 */

public class Event {

    public long id;

    public String title;

    public String description;

    public long end_date;

    public int max_user_count;

    public double longitude;

    public double latitude;

    public String category;

    public Image image;

    public User creator;

    public List<User> participants = new ArrayList<>();

    public List<Message> messages = new ArrayList<>();
}
