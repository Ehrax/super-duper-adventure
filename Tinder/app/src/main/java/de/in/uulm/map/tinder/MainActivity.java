package de.in.uulm.map.tinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import de.in.uulm.map.tinder.entities.Event;
import de.in.uulm.map.tinder.entities.Image;
import de.in.uulm.map.tinder.entities.Message;
import de.in.uulm.map.tinder.entities.User;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();

        Image user_img = new Image();
        user_img.path = "user/image.png";

        Image event_img = new Image();
        event_img.path = "events/image.png";

        User user = new User();
        user.id = 1;
        user.name = "User";
        user.image = user_img;

        Event event = new Event();
        event.image = event_img;
        event.description = "Test";
        event.creator = user;
        event.participants = new RealmList<>();
        event.participants.add(user);
        event.category = "Sport";
        event.longitude = 0.0;
        event.latitude = 0.0;
        event.end_date = new Date().getTime() + 3600000;
        event.max_user_count = 5;

        user.events.add(event);

        Message msg = new Message();
        msg.text = "Hello, World!";
        msg.event = event;
        msg.creator = user;
        msg.timestamp = new Date().getTime();

        event.messages.add(msg);

        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();

        RealmResults<User> users = realm.where(User.class).findAll();

        for(User u : users) {
            Event e = u.events.get(0);
            Log.d("Event", e.toString());
            Log.d("Event creator", e.creator.toString());
            Message m = e.messages.get(0);
            Log.d("Message", m.toString());
        }

        realm.beginTransaction();
        realm.delete(User.class);
        realm.delete(Image.class);
        realm.delete(Message.class);
        realm.delete(Event.class);
        realm.commitTransaction();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
