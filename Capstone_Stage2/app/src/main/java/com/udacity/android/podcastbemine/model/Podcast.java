package com.udacity.android.podcastbemine.model;

import java.io.Serializable;

public class Podcast implements Serializable {

    private String title;
    private String author;
    private int audio_length;
    private String description;
    private String thumbnail;
    private String audio_url;

    public Podcast(String title, String author, int audio_length,
                   String description, String thumbnail, String audio_url) {
        this.title = title;
        this.author = author;
        this.audio_length = audio_length;
        this.description = description;
        this.thumbnail = thumbnail;
        this.audio_url = audio_url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getAudioLength() {
        return this.audio_length;
    }

    public String getDescription() {
        return this.description;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getAudioUrl() {
        return this.audio_url;
    }



}
