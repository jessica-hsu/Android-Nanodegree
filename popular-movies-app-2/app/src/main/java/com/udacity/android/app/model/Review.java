package com.udacity.android.app.model;

public class Review {

    private int movie_id;
    private String author;
    private String content;
    private String url;

    public Review(int movie_id, String author, String content, String url) {
        this.movie_id = movie_id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public int getMovieId() {
        return this.movie_id;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMovieId(int id) {
        this.movie_id = id;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    public void setContent(String s) {
        this.content = s;
    }

    public void setUrl(String u) {
        this.url = u;
    }

}
