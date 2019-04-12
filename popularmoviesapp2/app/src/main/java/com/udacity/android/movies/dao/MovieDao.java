package com.udacity.android.movies.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.udacity.android.movies.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    public List<Movie> getAll();

    @Insert
    public void insertAll(Movie... m);

    @Delete
    public void delete(Movie m);

    @Query("SELECT * FROM movies WHERE movieId = :id")
    public Movie getMovieById();
}
