package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id_comment")
    @Expose
    private String commentId;
    @SerializedName("id_film")
    @Expose
    private  String filmId;
    @SerializedName("username_")
    @Expose
    private String username;
    @SerializedName("content")
    @Expose
    private String content;

    public Comment() {
    }

    public Comment(String commentId, String filmId, String username, String content) {
        this.commentId = commentId;
        this.filmId = filmId;
        this.username = username;
        this.content = content;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
