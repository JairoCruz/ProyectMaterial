package com.example.tse.proyectmaterial.json;

import android.util.Log;

import com.example.tse.proyectmaterial.extras.Constants;
import com.example.tse.proyectmaterial.extras.Utils;
import com.example.tse.proyectmaterial.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

/**
 * Created by TSE on 24/09/2015.
 */
public class Parse {


    public static ArrayList<Movie> parseJsonResponse(JSONObject response) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<Movie> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0){

            try {
                JSONArray jsonArrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < jsonArrayMovies.length(); i++){
                    long id = -1;
                    String title = Constants.NA;
                    String releaseDate = Constants.NA;
                    int audienceScore = -1;
                    String urlThumbnail = Constants.NA;
                    String synopsis = Constants.NA;
                    // Estos los agregos ya que los agregare a la db sqlite
                    String urlSelft = Constants.NA;
                    String urlCast = Constants.NA;
                    String urlReviews = Constants.NA;
                    String urlSimilar = Constants.NA;
                    JSONObject currentMovies = jsonArrayMovies.getJSONObject(i);

                    if (Utils.contains(currentMovies, KEY_ID)){
                        id = currentMovies.getLong(KEY_ID);
                    }

                    if (Utils.contains(currentMovies, KEY_TITLE)) {
                        title = currentMovies.getString(KEY_TITLE);
                    }


                    if (Utils.contains(currentMovies, KEY_RELEASE_DATES)){
                        JSONObject objectReleaseDates = currentMovies.getJSONObject(KEY_RELEASE_DATES);
                        if (Utils.contains(objectReleaseDates, KEY_THEATER)){
                            releaseDate = objectReleaseDates.getString(KEY_THEATER);
                        }
                    }


                    if (Utils.contains(currentMovies, KEY_RATINGS)){
                        JSONObject objectRatings = currentMovies.getJSONObject(KEY_RATINGS);
                        if (Utils.contains(objectRatings, KEY_AUDIENCE_SCORE)){
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                        }
                    }

                    if (Utils.contains(currentMovies, KEY_SYNOPSIS)){
                        synopsis = currentMovies.getString(KEY_SYNOPSIS);
                    }

                    if (Utils.contains(currentMovies, KEY_POSTERS)){
                        JSONObject objectPoster = currentMovies.getJSONObject(KEY_POSTERS);
                        if (Utils.contains(objectPoster, KEY_THUMBNAIL)){
                            urlThumbnail = objectPoster.getString(KEY_THUMBNAIL);
                        }
                    }


                    // recupero estos ya que los agregare a la db
                    if (Utils.contains(currentMovies,KEY_LINKS)){
                        JSONObject objectLinks = currentMovies.getJSONObject(KEY_LINKS);
                        if (Utils.contains(objectLinks, KEY_SELF)){
                            urlSelft = objectLinks.getString(KEY_SELF);
                        }
                        if (Utils.contains(objectLinks, KEY_REVIEWS)){
                            urlReviews = objectLinks.getString(KEY_REVIEWS);
                        }
                        if (Utils.contains(objectLinks, KEY_SIMILAR)){
                            urlSimilar = objectLinks.getString(KEY_SIMILAR);
                        }
                    }



                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = null;
                    try{
                        date = dateFormat.parse(releaseDate);
                    }catch(ParseException e){

                    }
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);
                    movie.setUrlSelf(urlSelft);
                    movie.setUrlCast(urlCast);
                    movie.setUrlReviews(urlReviews);
                    movie.setUrlSimilar(urlSimilar);

                    if(id != -1 && !title.equals(Constants.NA)){
                        listMovies.add(movie);
                    }
                }

            }catch (JSONException e ){
                Log.e("Mi error", e.getMessage());
            }

        }
        // L.T(getActivity(), listMovies.toString());
        return listMovies;

    }
}
