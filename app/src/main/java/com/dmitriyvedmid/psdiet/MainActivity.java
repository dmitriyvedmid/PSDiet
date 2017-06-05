package com.dmitriyvedmid.psdiet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.dmitriyvedmid.psdiet.DietEditIntent.DietEditActivity;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static ArrayList<UserItem> sUserItems = new ArrayList<>();
    public static ArrayList<Dish> sDishes = new ArrayList<>();
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dishesCreation();
        updateUserItems();

        prefManager = new PrefManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!prefManager.isFirstTimeLaunch()) {
                    Intent i = new Intent(getApplicationContext(), DietEditActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), UserRegistrationActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void updateUserItems() {
        sUserItems.clear();
        DBHelper mDBHelper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int uNameIndex = cursor.getColumnIndex(DBHelper.USER_NAME);
            int dNameIndex = cursor.getColumnIndex(DBHelper.DISH_NAME);
            int dishMeal = cursor.getColumnIndex(DBHelper.DISH_MEAL);
            int dishAmount = cursor.getColumnIndex(DBHelper.DISH_AMOUNT);
            do {
                sUserItems.add(new UserItem(cursor.getString(uNameIndex), cursor.getString(dNameIndex),
                        cursor.getInt(dishMeal), cursor.getInt(dishAmount)));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void dishesCreation() {
        sDishes.clear();
        sDishes.add(new Dish("example of long name naaaaaaaaaaaaaaaaaaaaaaaaame1", 1, "units1", 10, 11, 132, 143, 14, true, false, true, false));
        sDishes.add(new Dish("name2", 1, "units1", 10, 12, 132, 143, 14, true, false, true, false));
        sDishes.add(new Dish("name3", 1, "units1", 10, 13, 142, 143, 14, false, true, true, false));
        sDishes.add(new Dish("name4", 1, "units1", 10, 14, 142, 143, 14, false, true, true, false));
        sDishes.add(new Dish("name5", 1, "units1", 10, 15, 152, 133, 14, true, false, false, true));
        sDishes.add(new Dish("name6", 1, "units1", 10, 16, 142, 133, 14, true, false, false, true));
        sDishes.add(new Dish("name7", 1, "units1", 10, 17, 152, 133, 14, false, true, true, false));
        sDishes.add(new Dish("name9", 1, "units1", 10, 18, 162, 133, 14, false, false, true, true));
        sDishes.add(new Dish("name8", 2, "units1", 10, 19, 162, 132, 14, true, false, false, true));
        sDishes.add(new Dish("nam10", 2, "units1", 10, 21, 162, 135, 14, true, false, false, true));
        sDishes.add(new Dish("nam11", 2, "units1", 10, 22, 152, 137, 14, false, false, true, true));
        sDishes.add(new Dish("nam12", 2, "units1", 10, 23, 142, 138, 14, false, true, true, false));
        sDishes.add(new Dish("nam13", 2, "units1", 10, 24, 142, 136, 14, true, false, true, false));
        sDishes.add(new Dish("nam14", 2, "units1", 10, 25, 132, 138, 14, true, false, true, false));
        sDishes.add(new Dish("nam15", 2, "units1", 10, 26, 122, 130, 14, true, false, true, false));
    }
}
