package com.example.myapplication.Model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Genre {

    @SerializedName("genre")
    @Expose
    private String nameGenre;
    @SerializedName("list")
    @Expose
    private List<Film> list;

    public Genre() {
    }

    public Genre(String nameGenre, List<Film> list) {
        this.nameGenre = nameGenre;
        this.list = list;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String getNameGenre) {
        this.nameGenre = getNameGenre;
    }

    public List<Film> getList() {
        return list;
    }

    public void setList(List<Film> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: "+getNameGenre()+"; List: "+getList().toString();
    }
}
