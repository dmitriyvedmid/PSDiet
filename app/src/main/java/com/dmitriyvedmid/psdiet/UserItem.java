package com.dmitriyvedmid.psdiet;

import static com.dmitriyvedmid.psdiet.MainActivity.sDishes;

/**
 * Created by dmitr on 5/22/2017.
 */

public class UserItem {
    private String mName;
    private int mMeal;
    private Dish mDish;
    private int mAmount;

    public UserItem(String name, int meal, Dish dish, int amount) {
        mName = name;
        mMeal = meal;
        mDish = dish;
        mAmount = amount;
    }
    public UserItem(String name, String dish, int meal, int amount) {
        mName = name;
        mMeal = meal;
        mAmount = amount;
        for (int i = 0; i< sDishes.size(); i++)
            if (sDishes.get(i).getName().equals(dish)){
                mDish = sDishes.get(i); }
    }

    public String getName() {
        return mName;
    }

    public int getMeal() {
        return mMeal;
    }

    public Dish getDish() {
        return mDish;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

}
