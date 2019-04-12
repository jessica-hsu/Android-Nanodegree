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
    List<Movie> getAll();

    @Insert
    void insertAll(Movie... m);

    @Delete
    void delete(Movie m);
}
