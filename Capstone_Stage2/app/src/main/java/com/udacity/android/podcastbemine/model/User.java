package com.udacity.android.podcastbemine.model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String id;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }
}
