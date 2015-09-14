package com.example.tse.proyectmaterial.fragment;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tse.proyectmaterial.MyAplication;
import com.example.tse.proyectmaterial.R;
import com.example.tse.proyectmaterial.adapter.AdapterBoxOffice;
import com.example.tse.proyectmaterial.extras.Constants;
import com.example.tse.proyectmaterial.extras.Keys;
import com.example.tse.proyectmaterial.extras.Utils;
import com.example.tse.proyectmaterial.logging.L;
import com.example.tse.proyectmaterial.model.Movie;
import com.example.tse.proyectmaterial.network.VolleySingleton;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_BOX_OFFICE;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_CHAR_AMPERSAND;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_CHAR_QUESTION;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_PARAM_API_KEY;
import static com.example.tse.proyectmaterial.extras.UrlEndPoints.URL_PARAM_LIMIT;
import static com.example.tse.proyectmaterial.extras.Keys.EndpointBoxOffice.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMovieHits;

    private AdapterBoxOffice adapterBoxOffice;
    private TextView textVolleyError;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // 54wzfswsa4qmjg8hjwa64d4c
        // esta es una key facil
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        // sendJsonRequest();

    }


    private void sendJsonRequest(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(10), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // L.t(getActivity(), response.toString());
                textVolleyError.setVisibility(View.GONE);
               listMovies = parseJSONResponse(response);
                adapterBoxOffice.setMovieList(listMovies);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);

            }
        });
        requestQueue.add(request);
    }

    private void handleVolleyError(VolleyError error){
        textVolleyError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError){
            textVolleyError.setText(R.string.error_timeout);
        }else if (error instanceof AuthFailureError){
            textVolleyError.setText(R.string.error_auth_failure);
        }else if (error instanceof ServerError){
            textVolleyError.setText(R.string.error_auth_failure);
        }else if (error instanceof NetworkError){
            textVolleyError.setText(R.string.error_network);
        }else if (error instanceof ParseError){
            textVolleyError.setText(R.string.error_parser);
        }
    }

    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_fragment_box_office, container, false);

        View view = inflater.inflate(R.layout.fragment_fragment_box_office,container,false);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;
    }


    public static String getRequestUrl(int limit){
        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyAplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMPERSAND
                + URL_PARAM_LIMIT + limit;
    }


}
