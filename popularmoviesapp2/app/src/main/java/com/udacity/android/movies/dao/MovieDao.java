package com.udacity.android.movies.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.udacity.android.movies.entity.Movie;

import java.util.List;

/**
 * Data Access Object for viewModel to use to add, delete, and get movies
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAll();

    @Insert
    void insertMovie(Movie m);

    @Query("DELETE FROM movies WHERE movieId = :id")
    void delete(String id);

    @Query("SELECT * FROM movies WHERE movieId = :id")
    Movie getMovieById(String id);
}
