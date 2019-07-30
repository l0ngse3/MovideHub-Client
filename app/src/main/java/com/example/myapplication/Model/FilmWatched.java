package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilmWatched {

    @SerializedName("idFilm")
    @Expose
    private String idFilm;
    @SerializedName("curProgress")
    @Expose
    private int curProgress;
    @SerializedName("username")
    @Expose
    private String username;

    public FilmWatched() {
    }

    public FilmWatched(String idFilm, int curProgress, String username) {
        this.idFilm = idFilm;
        this.curProgress = curProgress;
        this.username = username;
    }

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public int getCurProgress() {
        return curProgress;
    }

    public void setCurProgress(int curProgress) {
        this.curProgress = curProgress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return "id film : \""+idFilm+"\"; cur_progress : "+curProgress+"; username_ : \""+username+"\"";
    }
}
