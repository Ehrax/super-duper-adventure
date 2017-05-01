package de.in.uulm.map.tinder;

import android.app.Application;

import io.realm.Realm;

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
    }
}
