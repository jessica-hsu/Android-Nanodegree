package com.udacity.android.movies.tasks;

import android.os.AsyncTask;
import com.udacity.android.movies.dao.MovieDao;


/**
 * Class to run async tasks in background - delete movie from Room database
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
