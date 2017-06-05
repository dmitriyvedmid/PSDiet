package com.dmitriyvedmid.psdiet;

/**
 * Created by dmitr on 5/22/2017.
 */

public class Dish {
    private String mName;
    private int mTable;
    private String mUnits;
    private int mAmount;
    private int mCalories;
    private int mProteins;
    private int mFats;
    private int mCarbohydrates;
    private boolean mIsBreakfast;
    private boolean mIsLunch;
    private boolean mIsDinner;
    private boolean mIsSupper;

    public Dish(String name, int table, String units, int amount, int calories, int proteins, int fats, int carbohydrates,
                boolean isBreakfast, boolean isLunch, boolean isDinner, boolean isSupper) {
        mName = name;
        mTable = table;
        mAmount = amount;
        mCalories = calories;
        mProteins = proteins;
        mFats = fats;
        mCarbohydrates = carbohydrates;
        mUnits = units;
        mIsBreakfast = isBreakfast;
        mIsLunch = isLunch;
        mIsDinner = isDinner;
        mIsSupper = isSupper;
    }

    public String getName() {
        return mName;
    }

    public int getCalories() {
        return mCalories;
    }

    public int getProteins() {
        return mProteins;
    }

    public int getFats() {
        return mFats;
    }

    public int getCarbohydrates() {
        return mCarbohydrates;
    }

    public String getUnits() {
        return mUnits;
    }

    public int getTable() {
        return mTable;
    }

    public boolean isBreakfast() {
        return mIsBreakfast;
    }

    public boolean isLunch() {
        return mIsLunch;
    }

    public boolean isDinner() {
        return mIsDinner;
    }

    public boolean isSupper() {
        return mIsSupper;
    }

    public int getAmount() {
        return mAmount;
    }

}