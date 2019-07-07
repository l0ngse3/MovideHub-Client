package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Film {

    @SerializedName("id_film")
    @Expose
    private String id_film;
    @SerializedName("title_film")
    @Expose
    private String title_film;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("film_description")
    @Expose
    private String film_description;
    @SerializedName("trailer_url")
    @Expose
    private String trailer_url;
    @SerializedName("film_url")
    @Expose
    private String film_url;
    @SerializedName("film_views")
    @Expose
    private String film_views;
    @SerializedName("rate_imdb")
    @Expose
    private String rate_imdb;
    @SerializedName("genre")
    @Expose
    private String genre;

    public Film() {
    }

    public Film(String id_film, String title_film, String thumbnail, String film_description, String trailer_url, String film_url, String film_views, String rate_imdb, String genre) {
        this.id_film = id_film;
        this.title_film = title_film;
        this.thumbnail = thumbnail;
        this.film_description = film_description;
        this.trailer_url = trailer_url;
        this.film_url = film_url;
        this.film_views = film_views;
        this.rate_imdb = rate_imdb;
        this.genre = genre;
    }


    public String getId_film() {
        return id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public String getTitle_film() {
        return title_film;
    }

    public void setTitle_film(String title_film) {
        this.title_film = title_film;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFilm_description() {
        return film_description;
    }

    public void setFilm_description(String film_description) {
        this.film_description = film_description;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public String getFilm_url() {
        return film_url;
    }

    public void setFilm_url(String film_url) {
        this.film_url = film_url;
    }

    public String getFilm_views() {
        return film_views;
    }

    public void setFilm_views(String film_views) {
        this.film_views = film_views;
    }

    public String getRate_imdb() {
        return rate_imdb;
    }

    public void setRate_imdb(String rate_imdb) {
        this.rate_imdb = rate_imdb;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
