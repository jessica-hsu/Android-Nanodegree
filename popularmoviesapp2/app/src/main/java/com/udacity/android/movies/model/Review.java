package com.udacity.android.movies.model;

/**
 * Model to hold reviews for selected movie. Includes setters and getters
 */
public class Review {

    private String movie_id;
    private String author;
    private String content;
    private String url;

    public Review(String movie_id, String author, String content, String url) {
        this.movie_id = movie_id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getMovieId() {
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

    public void setMovieId(String id) {
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
