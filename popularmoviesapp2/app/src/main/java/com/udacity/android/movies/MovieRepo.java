package com.udacity.android.movies;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.udacity.android.movies.dao.MovieDao;
import com.udacity.android.movies.entity.Movie;
import com.udacity.android.movies.tasks.DeleteMovieDBTask;
import com.udacity.android.movies.tasks.InsertMovieDBTask;

import java.util.List;

/**
 * Repository class for LiveData/ViewModel use
 */
public class MovieRepo {

    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;

    public MovieRepo(Application app) {
        AppDatabase database = AppDatabase.getDatabase(app);
        movieDao = database.movieDao();
        allMovies = movieDao.getAll();
    }

    // grab them movies
    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    // insert to db
    public void insertMovie(Movie m) {
        new InsertMovieDBTask(movieDao).execute(m);
    }

    // delete from db
    public void deleteMovie(String id) {
        new DeleteMovieDBTask(movieDao).execute(id);
    }


}
