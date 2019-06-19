package com.udacity.android.movies.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.udacity.android.movies.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    public LiveData<List<Movie>> getAll();

    @Insert
    public void insertMovies(Movie... m);

    @Insert
    public void insertMovie(Movie m);

    @Query("DELETE FROM movies WHERE movieId = :id")
    public void delete(String id);

    @Query("SELECT * FROM movies WHERE movieId = :id")
    public Movie getMovieById(String id);
}
