package com.udacity.android.podcastbemine.model;

import com.udacity.android.podcastbemine.utils.Constant;

import java.io.Serializable;

public class Podcast implements Serializable {

    private String id;
    private String title;
    private String author;
    private int audio_length;
    private String description;
    private String thumbnail;
    private String audio_url;

    private String userId;  // identify user id
    private String combinedId; // userId + "~" + podcastId. use this for search

    public Podcast(String id, String title, String author, int audio_length,
                   String description, String thumbnail, String audio_url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.audio_length = audio_length;
        this.description = description;
        this.thumbnail = thumbnail;
        this.audio_url = audio_url;
        this.userId = Constant.DEFAULT_NO_USER;
        this.combinedId = this.userId + "~" + this.id;
    }

    public String getId() { return this.id; }

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

    public String getUserId() {
        return this.userId;
    }

    public String getCombinedId() {
        return this.combinedId;
    }

    public void setUserId(String user) {

        this.userId = user;
        this.combinedId = this.userId + "~" + this.id;
    }


}
