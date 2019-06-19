package com.udacity.android.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.udacity.android.movies.MovieRepo;
import com.udacity.android.movies.entity.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepo movieRepo;
    private LiveData<List<Movie>> allMovies;

    public MovieViewModel(Application app) {
        super(app);
        this.movieRepo = new MovieRepo(app);
        this.allMovies = movieRepo.getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return this.allMovies;
    }

    public void insertMovie(Movie m) {
        this.movieRepo.insertMovie(m);
    }

    public void deleteMovie(String id) {
        this.movieRepo.deleteMovie(id);
    }
}
