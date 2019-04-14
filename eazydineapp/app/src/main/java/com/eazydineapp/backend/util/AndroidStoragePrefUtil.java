package com.eazydineapp.backend.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Used for stroring and reading the key value.
 */
public class AndroidStoragePrefUtil {


    SharedPreferences sharedPref = null;
    final private String USER_KEY = "USER";
    final private String RESTAURANT_ID = "RESTAURANT_ID";
    final private String PREFERENCES_NAME = "EAZYDINEAPP";

    /**
     * Used for Storing the key Value
     * @param activity
     * @param key
     * @param value
     */
    public void putKeyValue(Activity activity, String key,String value){
        sharedPref = activity.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.commit();

    }

    /**
     * Used for getting the value.
     * @param activity
     * @param key
     * @return
     */
    public String getValue(Activity activity, String key){
        sharedPref = activity.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        return  sharedPref.getString(key,"No Key");
    }

    public String getValue(Fragment activity, String key){
        sharedPref = activity.getActivity().getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        return  sharedPref.getString(key,"No Key");
    }
    /**
     * Used for saving the user.
     * @param activity
     * @param userName
     */
    public void saveRegisteredUser(Activity activity, String userName){
        sharedPref = activity.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_KEY,userName);
        editor.commit();

    }


    /**
     * Used for saving the user.
     * @param fragment
     * @param userName
     */
    public void saveRegisteredUser(Fragment fragment, String userName){
        sharedPref = fragment.getActivity().getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_KEY,userName);
        editor.commit();

    }


    /**
     * Used for getting the user name .
     * @param activity
     * @return
     */
    public String getRegisteredUser(Activity activity){
        sharedPref = activity.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        return  sharedPref.getString(USER_KEY,"No Key");
    }


    /**
     * Used for getting the user name .
     * @param activity
     * @return
     */
    public String getRegisteredUser(Fragment activity) {
        sharedPref = activity.getActivity().getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        return sharedPref.getString(USER_KEY, "No Key");
    }
}
