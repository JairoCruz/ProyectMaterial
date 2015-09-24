package com.example.tse.proyectmaterial.services;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.tse.proyectmaterial.MyAplication;
import com.example.tse.proyectmaterial.callbacks.BoxOfficeMoviesLoadedListener;
import com.example.tse.proyectmaterial.extras.Constants;
import com.example.tse.proyectmaterial.extras.Utils;
import com.example.tse.proyectmaterial.logging.L;
import com.example.tse.proyectmaterial.model.Movie;
import com.example.tse.proyectmaterial.network.VolleySingleton;
import com.example.tse.proyectmaterial.task.TaskLoadMoviesBoxOffice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_ID;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_LINKS;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_REVIEWS;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_SELF;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_SIMILAR;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.KEY_TITLE;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_BOX_OFFICE;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_CHAR_AMPERSAND;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_CHAR_QUESTION;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_PARAM_API_KEY;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_PARAM_LIMIT;

/**
 * Created by TSE on 16/09/2015.
 */
public class MyService extends JobService implements BoxOfficeMoviesLoadedListener {

    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
       L.t(this, "onStartJob");
        this.jobParameters = jobParameters;
        new TaskLoadMoviesBoxOffice(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        L.t(this, "onStopJob");
        return false;
    }

    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies){
        L.t(this, "onBoxOfficeMoviesLoaded");
        jobFinished(jobParameters, false);
    }


}
