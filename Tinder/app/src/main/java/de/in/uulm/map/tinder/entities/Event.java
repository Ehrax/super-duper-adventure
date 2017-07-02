package de.in.uulm.map.tinder.entities;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public String start_date;

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

    public GregorianCalendar getStartDate() {

        Date date;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.ENGLISH);
        try {
            date = formatter.parse(start_date);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public String getFormattedStartDate() {

        GregorianCalendar startDate = getStartDate();
        return startDate.get(Calendar.DAY_OF_MONTH) + "." + ((startDate.get
                (Calendar
                        .MONTH) + 1) < 10 ? "0" : "") + (startDate.get(Calendar.MONTH) + 1) + "" +
                "." + startDate.get(Calendar.YEAR);

    }

    public String getFormattedStartTime() {

        GregorianCalendar startDate = getStartDate();

        return (startDate.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") +
                startDate.get(Calendar.HOUR_OF_DAY) + ":" +
                (startDate.get(Calendar.MINUTE) < 10 ? "0" : "") +
                startDate.get(Calendar.MINUTE);
    }
}
