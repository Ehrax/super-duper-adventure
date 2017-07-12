package de.in.uulm.map.tinder.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jona on 01.05.2017.
 */

public class Event implements Serializable {

    @SerializedName("EventModelID")
    public String id;

    @SerializedName("Title")
    public String title;

    @SerializedName("Description")
    public String description;

    @SerializedName("StartDate")
    private String start_date;

    @SerializedName("MaxUsers")
    public int max_user_count;

    @SerializedName("Longitude")
    public double longitude;

    @SerializedName("Latitude")
    public double latitude;

    @SerializedName("Location")
    public String location;

    @SerializedName("Category")
    public String category;

    @SerializedName("HasImage")
    public boolean has_image;

    @SerializedName("Creator")
    public User creator;

    @SerializedName("Members")
    public List<User> participants = new ArrayList<>();

    private static final DateFormat SERVER_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);

    public void setStartDate(Date date) {

        if(date == null) {
            start_date = "";
        } else {
            start_date = SERVER_DATE_FORMAT.format(date);
        }
    }

    public Date getStartDate() {

        try {
            return SERVER_DATE_FORMAT.parse(start_date);
        } catch (ParseException e) {
            return null;
        }
    }

    public String getFormattedStartDate() {

        return SimpleDateFormat.getDateInstance(DateFormat.SHORT)
                .format(getStartDate());
    }

    public String getFormattedStartTime() {

        return new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(getStartDate());
    }
}
