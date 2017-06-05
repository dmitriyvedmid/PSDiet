package com.dmitriyvedmid.psdiet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dmitr on 6/1/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "userItemsdb";
    public static final String TABLE_NAME = "userItems";
    public static final String USER_NAME = "user_name";
    public static final String DISH_NAME = "dish_name";
    public static final String DISH_MEAL = "dish_meal";
    public static final String DISH_AMOUNT = "dish_amount";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"("+USER_NAME+" text,"+DISH_NAME+" text,"+DISH_MEAL+" integer,"+DISH_AMOUNT+" integer"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
}
