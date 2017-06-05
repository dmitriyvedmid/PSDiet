package com.dmitriyvedmid.psdiet;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dmitr on 5/29/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String USER_NAME = "UserName";
    private static final String USER_AGE = "UserAge";
    private static final String USER_HEIGHT = "UserHeight";
    private static final String USER_WEIGHT = "UserWeight";
    private static final String USER_TABLE = "UserTable";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setUserName(String name) {
        editor.putString(USER_NAME, name);
        editor.commit();
    }

    public void setUserAge(int age) {
        editor.putInt(USER_AGE, age);
        editor.commit();
    }

    public void setUserHeight(int height) {
        editor.putInt(USER_HEIGHT, height);
        editor.commit();
    }

    public void setUserWeight(int weight) {
        editor.putInt(USER_WEIGHT, weight);
        editor.commit();
    }

    public void setUserTable(int table) {
        editor.putInt(USER_TABLE, table);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public String getUserName() {
        return pref.getString(USER_NAME, null);
    }

    public int getUserAge() {
        return pref.getInt(USER_AGE, 0);
    }

    public int getUserHeight() {
        return pref.getInt(USER_HEIGHT, 0);
    }

    public int getUserWeight() {
        return pref.getInt(USER_WEIGHT, 0);
    }

    public int getUserTable() {
        return pref.getInt(USER_TABLE, 0);
    }
}