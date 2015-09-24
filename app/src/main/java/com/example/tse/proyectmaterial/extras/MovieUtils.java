package com.example.tse.proyectmaterial.extras;

import com.android.volley.RequestQueue;
import com.example.tse.proyectmaterial.MyAplication;
import com.example.tse.proyectmaterial.json.Endpoints;
import com.example.tse.proyectmaterial.json.Parse;
import com.example.tse.proyectmaterial.json.Requestor;
import com.example.tse.proyectmaterial.model.Movie;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TSE on 24/09/2015.
 */
public class MovieUtils {
    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue){
        JSONObject response = Requestor.sendRequestBoxOfficeMovies(requestQueue, Endpoints.getRequesUrlBoxOfficeMovies(10));
        ArrayList<Movie> listMovies = Parse.parseJsonResponse(response);
        MyAplication.getWritableDatabase().insertMoviesBoxOffice(listMovies, true);
        return listMovies;
    }
}
