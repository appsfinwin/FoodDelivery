package com.finwin.brahmagiri.fooddelivery.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LocalPreferences {
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final String DEFAULT_STRING = "";


    //store string preference
    public static void storeStringPreference(Context context, String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    //return string preferences
    public static String retrieveStringPreferences(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, DEFAULT_STRING);
    }

    //store boolean preference
    public static void storeBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    //return boolean preference
    public static boolean retrieveBooleanPreferences(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, DEFAULT_BOOLEAN);
    }


    //Removing all local preferences
    public static void clearPreferences(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

    //Storing arraylist<string> to the local preference
    public static void storeSetPreference(Context context, String key, ArrayList<String> value) {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Set<String> set = new HashSet<String>();
        set.addAll(value);
        editor.putStringSet(key, set);
        editor.apply();
    }

    //for return arraylist<String> from local preference
    public static ArrayList<String> retrieveSetPreferences(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = new HashSet<String>();
        Set<String> setvalue = prefs.getStringSet(key, set);

        return new ArrayList<String>(setvalue);
    }
}