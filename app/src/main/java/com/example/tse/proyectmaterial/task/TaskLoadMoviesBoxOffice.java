package com.example.tse.proyectmaterial.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.example.tse.proyectmaterial.callbacks.BoxOfficeMoviesLoadedListener;
import com.example.tse.proyectmaterial.extras.MovieUtils;
import com.example.tse.proyectmaterial.model.Movie;
import com.example.tse.proyectmaterial.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by TSE on 24/09/2015.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private BoxOfficeMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        ArrayList<Movie> listMovies = MovieUtils.loadBoxOfficeMovies(requestQueue);
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> listMovies) {
        if(myComponent != null){
            myComponent.onBoxOfficeMoviesLoaded(listMovies);
        }
    }
}
