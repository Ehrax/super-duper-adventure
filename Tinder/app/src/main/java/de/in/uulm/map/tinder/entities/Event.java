package de.in.uulm.map.tinder.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jona on 01.05.2017.
 */

public class Event {

    @SerializedName("EventModelID")
    public String id;

    @SerializedName("Title")
    public String title;

    @SerializedName("Description")
    public String description;

    @SerializedName("EndDate")
    public String end_date;

    @SerializedName("MaxUsers")
    public int max_user_count;

    @SerializedName("Longitude")
    public double longitude;

    @SerializedName("Latitude")
    public double latitude;

    @SerializedName("Category")
    public String category;

    @SerializedName("EventImage")
    public String image;

    @SerializedName("Creator")
    public User creator;

    @SerializedName("Members")
    public List<User> participants = new ArrayList<>();
}
