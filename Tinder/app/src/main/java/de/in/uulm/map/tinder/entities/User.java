package de.in.uulm.map.tinder.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jona on 01.05.2017.
 */

public class User {

    @SerializedName("Id")
    public String id;

    @SerializedName("UserName")
    public String name;

    @SerializedName("UserImage")
    public String image;
}
