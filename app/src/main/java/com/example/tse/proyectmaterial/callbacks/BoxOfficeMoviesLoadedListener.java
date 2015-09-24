package com.example.tse.proyectmaterial.callbacks;

import com.example.tse.proyectmaterial.model.Movie;

import java.util.ArrayList;

/**
 * Created by TSE on 24/09/2015.
 */
public interface BoxOfficeMoviesLoadedListener {
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies);
}
