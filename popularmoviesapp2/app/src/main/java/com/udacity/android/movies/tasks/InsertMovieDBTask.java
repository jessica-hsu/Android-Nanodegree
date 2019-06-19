package com.udacity.android.movies.tasks;

import android.os.AsyncTask;
import com.udacity.android.movies.dao.MovieDao;
import com.udacity.android.movies.entity.Movie;


/**
 * Class to run async tasks in background
 */
public class InsertMovieDBTask extends AsyncTask<Movie, Void, Void>  {

    private MovieDao asyncMovieDao;

    public InsertMovieDBTask(MovieDao movieDao) {
        this.asyncMovieDao = movieDao;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        this.asyncMovieDao.insertMovie(movies[0]);
        return null;
    }
}
