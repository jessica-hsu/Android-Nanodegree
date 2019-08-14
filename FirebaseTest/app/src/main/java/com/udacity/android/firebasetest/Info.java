package com.udacity.android.firebasetest;

public class Info {

    private String userId;
    private String podcastId;

    public Info(String u, String p) {
        this.userId = u;
        this.podcastId = p;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPodcastId() {
        return this.podcastId;
    }
}
