package com.example.tse.proyectmaterial.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.tse.proyectmaterial.MyAplication;
import com.example.tse.proyectmaterial.R;
import com.example.tse.proyectmaterial.adapter.AdapterBoxOffice;
import com.example.tse.proyectmaterial.callbacks.BoxOfficeMoviesLoadedListener;
import com.example.tse.proyectmaterial.extras.MovieSorter;
import com.example.tse.proyectmaterial.extras.SortListener;
import com.example.tse.proyectmaterial.logging.L;
import com.example.tse.proyectmaterial.model.Movie;
import com.example.tse.proyectmaterial.network.VolleySingleton;
import com.example.tse.proyectmaterial.task.TaskLoadMoviesBoxOffice;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener, BoxOfficeMoviesLoadedListener, SwipeRefreshLayout.OnRefreshListener {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES = "state_movies" ;



    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private ArrayList<Movie> listMovies = new ArrayList<>();

    private RecyclerView listMovieHits;

    private AdapterBoxOffice adapterBoxOffice;
    private TextView textVolleyError;

    private MovieSorter mSorter = new MovieSorter();

    // Declaro una variable del tipo SwipeRefresh
    private SwipeRefreshLayout mSwipeRefreshLayout;

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


        // sendJsonRequest();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, listMovies);
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_fragment_box_office, container, false);

        View view = inflater.inflate(R.layout.fragment_fragment_box_office,container,false);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        // Recupero de mi vista el swipeRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeListMoviesHits);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        if (savedInstanceState != null){
            listMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        }else{
            listMovies = MyAplication.getWritableDatabase().getAllMoviesBoxOffice();
            if(listMovies.isEmpty()){
                L.t(getActivity(), "Executing task from fragment");
                new TaskLoadMoviesBoxOffice(this).execute();
            }
        }
        adapterBoxOffice.setMovieList(listMovies);

        return view;
    }





    @Override
    public void onSortByName() {
        L.t(getActivity(), "sort name box office");
        mSorter.sortMoviesByName(listMovies);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByDate() {

        mSorter.sortMoviesByDate(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void onSortByRating() {
        mSorter.sortMoviesByRating(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies) {
        L.t(getActivity(), "onBoxOfficeMoviesLoades Fragment");
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        adapterBoxOffice.setMovieList(listMovies);
    }

    @Override
    public void onRefresh() {
        L.t(getActivity(), "OnRefresh");
        new TaskLoadMoviesBoxOffice(this).execute();
    }
}
