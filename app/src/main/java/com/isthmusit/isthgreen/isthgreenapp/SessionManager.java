package com.isthmusit.isthgreen.isthgreenapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "pref";
    private static final String IS_LOGIN = "isLogIn";
    private static final String KEY_TOKEN = "jwtToken";
    private static final String USER_ID = "userId";

    public SessionManager(Context pcontext){
        this.context = pcontext;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSession(String token, String userId){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, token);
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public UserSession getToken(){
        UserSession userSession = new UserSession(pref.getString(KEY_TOKEN, null),
                pref.getString(USER_ID, null));
        return userSession;
    }

    public void logOut(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
