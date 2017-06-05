package com.dmitriyvedmid.psdiet;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.dmitriyvedmid.psdiet.DietEditIntent.DietEditFragment;

import static com.dmitriyvedmid.psdiet.DietEditIntent.DietEditFragment.mealClicked;
import static com.dmitriyvedmid.psdiet.MainActivity.sDishes;
import static com.dmitriyvedmid.psdiet.MainActivity.sUserItems;

/**
 * Created by dmitr on 5/30/2017.
 */

public class MyDialogFragment extends DialogFragment {

    String[] names = new String[DietEditFragment.names.size()];
    String tempName;
    static final String FINISHED = "finished";

    public static MyDialogFragment newInstance(int title) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        for (int i = 0; i < DietEditFragment.names.size(); i++) {
            names[i] = DietEditFragment.names.get(i);
        }
        try {tempName = names[0];}catch (Exception e){};
        int title = getArguments().getInt("title");
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setSingleChoiceItems(names, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempName = names[which];
                    }
                })
                .setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                PrefManager prefManager = new PrefManager(getActivity());


                                Dish d;
                                for (int i = 0; i < sDishes.size(); i++)
                                    if ((sDishes.get(i).getName().equals(tempName))) {
                                        d = sDishes.get(i);
                                        sUserItems.add(new UserItem(prefManager.getUserName(), mealClicked, sDishes.get(i), sDishes.get(i).getAmount()));
                                        DBHelper dbHelper = new DBHelper(getActivity());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(DBHelper.USER_NAME, prefManager.getUserName());
                                        contentValues.put(DBHelper.DISH_NAME, d.getName());
                                        contentValues.put(DBHelper.DISH_MEAL, mealClicked);
                                        contentValues.put(DBHelper.DISH_AMOUNT, d.getAmount());
                                        db.insert(DBHelper.TABLE_NAME, null, contentValues);
                                        sendResult(1);
                                    }
                            }
                        }
                )
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .create();
    }

    public void sendResult(int REQUEST_CODE){
        Intent intent = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE, intent);
    }
}
