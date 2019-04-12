package com.udacity.android.movies.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey
    @NonNull public int movieId;

    @ColumnInfo(name = "movie_title")
    public String movieTitle;

    @ColumnInfo(name = "movie_release_date")
    public String movieReleaseDate;

    @ColumnInfo(name = "movie_rating")
    public String movieRating;

    @ColumnInfo(name = "movie_plot")
    public String movie_plot;



}
