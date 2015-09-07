package com.example.tse.proyectmaterial;

import android.app.Application;
import android.content.Context;

/**
 * Created by TSE on 04/09/2015.
 */
public class MyAplication extends Application {

    public static final String API_KEY_ROTTEN_TOMATOES = "54wzfswsa4qmjg8hjwa64d4c";

    private static MyAplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyAplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
