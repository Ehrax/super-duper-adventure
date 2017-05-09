package de.in.uulm.map.tinder;

import android.app.Application;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Image;
import de.in.uulm.map.tinder.entities.Message;
import de.in.uulm.map.tinder.entities.User;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Jona on 01.05.2017.
 */

public class App extends Application {

    /**
     * This method will be called before the onCreate methods of all Activities.
     * Therefore we can use it to init global objects.
     */
    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.deleteAll();

        User b = new User();
        b.image = new Image();
        b.id = 2;
        b.name = "Other";

        User a = new User();
        a.image = new Image();
        a.id = 1;
        a.name = "Test";

        Event event = new Event();
        event.id = 1;
        event.description = "Test Event";
        event.max_user_count = 4;
        event.category = "Test";
        event.end_date = new Date().getTime() + 3600000;
        event.image = new Image();
        event.creator = a;
        event.participants.add(a);
        event.participants.add(b);

        for(int i = 0; i < 10; i++) {
            Message m = new Message();
            m.creator = i < 5 ? a : b;
            m.text = "Message " + i;
            m.timestamp = new Date().getTime() - 3600 * i;
            event.messages.add(m);
        }

        a.events.add(event);

        realm.commitTransaction();
    }
}
