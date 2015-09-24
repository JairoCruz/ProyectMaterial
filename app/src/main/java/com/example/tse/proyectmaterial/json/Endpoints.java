package com.example.tse.proyectmaterial.json;

import com.example.tse.proyectmaterial.MyAplication;

import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_BOX_OFFICE;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_CHAR_AMPERSAND;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_CHAR_QUESTION;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_PARAM_API_KEY;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_PARAM_LIMIT;

/**
 * Created by TSE on 24/09/2015.
 */
public class Endpoints {

    public static String getRequesUrlBoxOfficeMovies(int limit){
        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyAplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMPERSAND
                + URL_PARAM_LIMIT + limit;
    }
}
