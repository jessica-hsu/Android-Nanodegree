package com.udacity.android.app.model;

public class Video {

    private String movie_id;
    private String key;
    private String name;

    public Video(String movie_id, String key, String name) {
        this.movie_id = movie_id;
        this.key = key;
        this.name = name;
    }

    public String getMovieId() {
        return this.movie_id;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public void setMovieId(String id) {
        this.movie_id = id;
    }

    public void setKey(String k) {
        this.key = k;
    }

    public void setName(String n) {
        this.name = n;
    }
}

