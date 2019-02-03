package com.hsu.android.popularmovies.model;

public class Movie {

    private String poster;
    private String id;
    private String title;
    private String date;
    private String plot;
    private String rating;

    public Movie(String id, String title, String poster, String plot, String date, String rating) {
        this.poster = poster;
        this.id = id;
        this.plot = plot;
        this.date = date;
        this.rating = rating;
        this.title = title;
    }

    public String getPoster() {
        return this.poster;
    }

    public String getId() {
        return this.id;
    }

    public String getPlot() {
        return this.plot;
    }

    public String getDate() {
        return this.date;
    }

    public String getRating() {
        return this.rating;
    }

    public String getTitle() {
        return this.rating;
    }
}
