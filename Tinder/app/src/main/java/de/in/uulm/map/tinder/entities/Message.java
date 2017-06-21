package de.in.uulm.map.tinder.entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Jona on 01.05.2017.
 */

public class Message {

    public String mText;

    public long mTimestamp;

    public String mUserName;

    public String mUserImg;

    public String mUid;

    public Message() {
        // default constructor required for calls to DataSnapshot.getValue
    }

    @Exclude
    public HashMap<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("mUid", mUid);
        result.put("mUserName", mUserName);
        result.put("mText", mText);
        result.put("mTimestamp", mTimestamp);
        result.put("mUserImg", mUserImg);

        return result;
    }
}
