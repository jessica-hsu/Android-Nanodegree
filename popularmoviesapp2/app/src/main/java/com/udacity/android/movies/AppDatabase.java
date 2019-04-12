package com.udacity.android.movies;

import android.arch.persistence.room.Database;

import com.udacity.android.movies.dao.MovieDao;
import com.udacity.android.movies.entity.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase {
    public abstract MovieDao movieDao();
}
