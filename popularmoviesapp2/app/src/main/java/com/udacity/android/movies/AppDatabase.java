package com.udacity.android.movies;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.udacity.android.movies.dao.MovieDao;
import com.udacity.android.movies.entity.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    private static AppDatabase DB_INSTANCE;

    public static AppDatabase getDatabase(Context c) {
        if (DB_INSTANCE == null) {
           DB_INSTANCE =  Room.databaseBuilder(c, AppDatabase.class, "movieDb")
                    .allowMainThreadQueries()
                    .build();
        }
        return DB_INSTANCE;
    }

}
