package com.rakha.simpleprojecttemplate.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.rakha.simpleprojecttemplate.models.ProfileModel;
import com.google.gson.Gson;

/**
 * Created By rakha
 * 2019-12-15
 */
public class SPData {
    private static final String SP_NAME = "arbaajaa_spdata";
    public static final String LOGIN = "login";
    public static final String PROFILE = "profile";

    public static final String IS_REFRESH_TOKEN = "refresh_token";

    public static void clearAllData(Context context) {
        insertToSP(context, LOGIN, "");
        insertToSP(context, PROFILE, new Gson().toJson(new ProfileModel()));

        SharedPreferences pref = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
    }

    public static void insertToSP(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getSP(Context context, String key, String defaultValue) {
        if(context != null) {
            SharedPreferences pref = context.getSharedPreferences(SP_NAME,
                    Context.MODE_PRIVATE);
            return pref.getString(key, defaultValue);
        }else{
            return "";
        }
    }

    /**
     * Method untuk melakukan pengecekan apakah butuh untuk refresh access token
     * @param context
     * @param refreshToken
     */
    public static void setIsRefreshToken(Context context, boolean refreshToken) {
        SharedPreferences pref = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean(IS_REFRESH_TOKEN, refreshToken);
        edit.commit();
    }

    public static boolean isRefreshToken(Context context) {
        if(context != null) {
            SharedPreferences pref = context.getSharedPreferences(SP_NAME,
                    Context.MODE_PRIVATE);
            return pref.getBoolean(IS_REFRESH_TOKEN, false);
        } else{
            return false;
        }
    }
}