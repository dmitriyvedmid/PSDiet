package com.dmitriyvedmid.psdiet.DietEditIntent;

import android.support.v4.app.Fragment;

public class DietEditActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DietEditFragment();
    }
}