package de.in.uulm.map.tinder.main;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Image;
import de.in.uulm.map.tinder.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jona on 23.05.17.
 */

public class DbMock {

    private ArrayList<User> users = new ArrayList<>();

    private ArrayList<Event> events = new ArrayList<>();

    private ArrayList<Image> images = new ArrayList<>();

    private static DbMock mInstance = null;

    public static DbMock getInstance() {

        if(mInstance == null) {
            mInstance = new DbMock();
        }

        return mInstance;
    }

    private DbMock() {

        Image image = new Image();
        image.path = "test_path";

        User alice = new User();
        alice.id = 1;
        alice.name = "Alice";
        alice.image = image;

        User bob = new User();
        bob.id = 2;
        bob.name = "Bob";
        bob.image = image;

        User eve = new User();
        eve.id = 3;
        eve.name = "Eve";
        eve.image = image;

        User mallory = new User();
        mallory.id = 4;
        mallory.name = "Mallory";
        mallory.image = image;

        Image hiking_img = new Image();
        hiking_img.path = "file:///android_asset/hiking.jpeg";

        Event hiking = new Event();
        hiking.title = "Hiking";
        hiking.description = "We are waiting at the bottom of the mountains!" +
                "Going for a long trip ...";
        hiking.image = hiking_img;
        hiking.end_date = new Date().getTime() + 3600000;
        hiking.max_user_count = 4;
        hiking.category = "Sport";
        hiking.creator = alice;
        hiking.participants.add(alice);
        hiking.participants.add(bob);

        Image party_img = new Image();
        party_img.path = "file:///android_asset/party.jpeg";

        Event party = new Event();
        party.title = "Party";
        party.description = "Public party with all of your friends. " +
                "Come along and get wrecked!";
        party.image = party_img;
        party.end_date = new Date().getTime() + 3600000 * 8;
        party.category = "Ausgehen";
        party.max_user_count = 32;
        party.creator = eve;
        party.participants.add(alice);
        party.participants.add(eve);

        Image theater_img = new Image();
        theater_img.path = "file:///android_asset/theater.jpeg";

        Event theater = new Event();
        theater.title = "Theater";
        theater.description = "Let's go see a play at the local theater!";
        theater.image = theater_img;
        theater.end_date = new Date().getTime() + 3600000 * 4;
        theater.category = "Kultur";
        theater.max_user_count = 10;
        theater.creator = bob;
        theater.participants.add(bob);

        Image demo_img = new Image();
        demo_img.path = "file:///android_asset/demo.jpeg";

        Event demo = new Event();
        demo.title = "Demonstration";
        demo.description = "Everything is bad! Blame the system! Anarchy!";
        demo.image = demo_img;
        demo.end_date = new Date().getTime()+ 1800000;
        demo.category = "Sonstiges";
        demo.max_user_count = 32;
        demo.creator = mallory;
        demo.participants.add(mallory);
        demo.participants.add(bob);
        demo.participants.add(alice);
        demo.participants.add(eve);

        users.add(alice);
        users.add(bob);
        users.add(eve);
        users.add(mallory);

        events.add(hiking);
        events.add(theater);
        events.add(party);
        events.add(demo);

        images.add(image);
        images.add(hiking_img);
        images.add(theater_img);
        images.add(demo_img);
    }

    public User getCurrentUser() {

        return users.get(0);
    }

    public ArrayList<Event> getNearbyEvents() {

        return events;
    }

    public ArrayList<Event> getCreatedEvents() {

        ArrayList<Event> l = new ArrayList<>();

        for(Event e : events) {
            if(e.creator == getCurrentUser()) {
                l.add(e);
            }
        }

        return l;
    }

    public ArrayList<Event> getJoinedEvents() {

        ArrayList<Event> l = new ArrayList<>();

        for(Event e : events) {
            if(e.participants.contains(getCurrentUser())) {
                l.add(e);
            }
        }

        return l;
    }

    public void addEvent(Event event) {

        events.add(event);
    }
}
