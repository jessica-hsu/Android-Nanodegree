package com.udacity.android.movies.tasks;

import android.os.AsyncTask;
import com.udacity.android.movies.dao.MovieDao;
import com.udacity.android.movies.entity.Movie;


/**
 * Class to run async tasks in background
 */
public class DeleteMovieDBTask extends AsyncTask<String, Void, Void>  {

    private MovieDao asyncMovieDao;

    public DeleteMovieDBTask(MovieDao movieDao) {
        this.asyncMovieDao = movieDao;
    }

    @Override
    protected Void doInBackground(String... ids) {
        this.asyncMovieDao.delete(ids[0]);
        return null;
    }
}
