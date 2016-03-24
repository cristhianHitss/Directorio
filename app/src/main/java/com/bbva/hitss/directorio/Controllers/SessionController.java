package com.bbva.hitss.directorio.Controllers;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionController {


    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("Androidwarriors", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();

    }

    public  String getPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Androidwarriors",	Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }
}
