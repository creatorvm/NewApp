package com.cvm.android.dancesterz.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Devalopment-1 on 05-01-2018.
 */

public class PreferencesManager {

    Context context;

    public PreferencesManager(){}

    public PreferencesManager(Context context){
        this.context = context;
    }

    /**
     *
     * @param key
     * @return
     */
    public String read(String key){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,null);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void store(String key, String value){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }



    public void remove(String key){
         Log.i("Preferences","In remove");
         SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
         sharedPreferences.edit().remove(key).commit();
         if(sharedPreferences.contains(key))
         {
            Log.i( "Preferences",sharedPreferences.getString(key,null));
         }
         else
         {
             Log.i( "Preferences","Not exists");
         }
    }
}
