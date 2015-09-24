package com.example.tse.proyectmaterial;

import android.app.Application;
import android.content.Context;

import com.example.tse.proyectmaterial.database.MoviesDatabase;

/**
 * Created by TSE on 04/09/2015.
 */
public class MyAplication extends Application {

    public static final String API_KEY_ROTTEN_TOMATOES = "54wzfswsa4qmjg8hjwa64d4c";

    private static MyAplication sInstance;

    private static MoviesDatabase mDatabase;

    public synchronized  static MoviesDatabase getWritableDatabase(){
        if(mDatabase == null){
            mDatabase = new MoviesDatabase(getAppContext());
        }
        return  mDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = new MoviesDatabase(this);
    }

    public static MyAplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
