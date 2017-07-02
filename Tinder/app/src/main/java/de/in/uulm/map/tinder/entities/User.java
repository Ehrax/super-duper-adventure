package de.in.uulm.map.tinder.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jona on 01.05.2017.
 */

public class User implements Serializable {

    @SerializedName("Id")
    public String id;

    @SerializedName("Username")
    public String name;

    @SerializedName("Email")
    public String email;
}
