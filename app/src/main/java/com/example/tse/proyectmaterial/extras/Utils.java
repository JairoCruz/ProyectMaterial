package com.example.tse.proyectmaterial.extras;

import org.json.JSONObject;

/**
 * Created by TSE on 14/09/2015.
 */
public class Utils {
    public static boolean contains(JSONObject jsonObject, String key){
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}
