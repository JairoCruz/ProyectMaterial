package com.example.tse.proyectmaterial.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Marhinita on 6/9/2015.
 */
public class L {
    public static void m(String message){
        Log.e("Jairo "," " + message);
    }

    public static void t(Context context, String message){
        Toast.makeText(context,message + "", Toast.LENGTH_SHORT).show();
    }

    public static void T(Context context, String message){
        Toast.makeText(context,message + "", Toast.LENGTH_LONG).show();
    }

}
