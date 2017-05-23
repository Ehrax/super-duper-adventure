package de.in.uulm.map.tinder.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jona on 01.05.2017.
 */

public class User {

    public long id;

    public String name;

    public Image image;

    public List<Event> events = new ArrayList<>();
}
