package com.udacity.android.movies.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity model for Movie. For ViewModel/LiveData implementation
 */
@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey
    @NonNull public String movieId;

    @ColumnInfo(name = "movie_title")
    public String movieTitle;

    @ColumnInfo(name = "movie_release_date")
    public String movieReleaseDate;

    @ColumnInfo(name = "movie_rating")
    public String movieRating;

    @ColumnInfo(name = "movie_plot")
    public String moviePlot;

    @ColumnInfo(name = "movie_poster")
    public String moviePoster;

    public Movie(String id, String title, String date, String rating, String plot, String poster) {
        this.movieId = id;
        this.movieTitle = title;
        this.movieReleaseDate = date;
        this.movieRating = rating;
        this.moviePlot = plot;
        this.moviePoster = poster;
    }

    public Movie() {

    }

}
