package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fistName")
    @Expose
    private String fistName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("image")
    @Expose
    private String image;

    public Profile() {
        super();
    }

    public Profile(String username, String fistName, String lastName,
                   String image) {
        super();
        this.username = username;
        this.fistName = fistName;
        this.lastName = lastName;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
