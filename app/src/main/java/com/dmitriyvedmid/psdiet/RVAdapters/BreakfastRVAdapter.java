package com.dmitriyvedmid.psdiet.RVAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmitriyvedmid.psdiet.DBHelper;
import com.dmitriyvedmid.psdiet.DietEditIntent.DietEditFragment;
import com.dmitriyvedmid.psdiet.MainActivity;
import com.dmitriyvedmid.psdiet.PrefManager;
import com.dmitriyvedmid.psdiet.R;
import com.dmitriyvedmid.psdiet.UserItem;

import java.util.List;

import static com.dmitriyvedmid.psdiet.DietEditIntent.DietEditFragment.breakfastList;
import static com.dmitriyvedmid.psdiet.DietEditIntent.DietEditFragment.updateRVs;
import static com.dmitriyvedmid.psdiet.MainActivity.sUserItems;

public class BreakfastRVAdapter extends RecyclerView.Adapter<BreakfastRVAdapter.MealViewHolder> {

    public static List<UserItem> mDishes;

    public BreakfastRVAdapter(List<UserItem> dishes) {
        mDishes = dishes;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishes_list_adapter, viewGroup, false);
        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MealViewHolder mealViewHolder, int i) {
        if (MainActivity.sUserItems != null) {
            final UserItem userItem = mDishes.get(i);
            mealViewHolder.bindDish(userItem);
        }
    }

    @Override
    public int getItemCount() {
        return mDishes.size();
    }

    /////////////////////////////////////////////////////////////////////////
    public class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mDishName;
        TextView mDishCalories;
        TextView mDishProteins;
        TextView mDishFats;
        TextView mDishCarbohydrates;
        TextView mDishAmount;
        TextView mDishUnits;
        ImageView mDeleteItem;
        UserItem mUserItem;

        MealViewHolder(View itemView) {
            super(itemView);
            final Context context = itemView.getContext();
            final PrefManager prefManager = new PrefManager(context);
            mDishName = (TextView) itemView.findViewById(R.id.dish_name_text_view_list_item);
            mDishCalories = (TextView) itemView.findViewById(R.id.calories_list_item);
            mDishProteins = (TextView) itemView.findViewById(R.id.protein_list_item);
            mDishFats = (TextView) itemView.findViewById(R.id.fats_list_item);
            mDishCarbohydrates = (TextView) itemView.findViewById(R.id.carbohydrates_list_item);
            mDishAmount = (TextView) itemView.findViewById(R.id.dish_amount_text_view_list_item);
            mDishUnits = (TextView) itemView.findViewById(R.id.dish_units_text_view_list_item);
            mDeleteItem = (ImageView) itemView.findViewById(R.id.delete_dish_list_item);

            mDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < sUserItems.size(); i++) {
                        if (sUserItems.get(i).getDish().getName().equals(mDishName.getText().toString())) {
                            DBHelper dbHelper = new DBHelper(context);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            db.execSQL("DELETE FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.DISH_NAME + "='" + sUserItems.get(i).getDish().getName() + "'");
                            sUserItems.remove(i);
                            breakfastList.remove(getAdapterPosition());
                        }
                    }
                    DietEditFragment.updateRVs();

                }
            });
            mDishAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Введіть кількість");
                    final EditText input = new EditText(v.getContext());
                    input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
                    alert.setView(input);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            try {
                                int value = Integer.parseInt(input.getText().toString());
                                for (int i = 0; i < sUserItems.size(); i++) {
                                    if (sUserItems.get(i).getDish().getName().equals(mDishName.getText().toString())) {
                                        sUserItems.get(i).setAmount(value);
                                        ContentValues data = new ContentValues();
                                        DBHelper dbHelper = new DBHelper(context);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        data.put(DBHelper.USER_NAME, mUserItem.getName());
                                        data.put(DBHelper.DISH_NAME, mUserItem.getDish().getName());
                                        data.put(DBHelper.DISH_MEAL, mUserItem.getMeal());
                                        data.put(DBHelper.DISH_AMOUNT, value);
                                        db.update(DBHelper.TABLE_NAME, data, DBHelper.USER_NAME + " = ?", new String[] {prefManager.getUserName()});
                                        updateRVs();
                                        dbHelper.close();
                                    }
                                }
                            } catch (Exception e) {
                                Toast toast = Toast.makeText(context, "Ви ввели невірні дані\nСпробуйте ще", Toast.LENGTH_LONG);
                                View toastView = toast.getView();
                                TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                                toastMessage.setTextSize(25);
                                toastMessage.setTextColor(Color.BLACK);
                                toastMessage.setGravity(Gravity.TOP);
                                toastMessage.setPadding(30, 10, 30, 10);
                                toastView.setBackgroundColor(Color.WHITE);
                                toast.show();
                            }
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    alert.show();
                }
            });
        }

        public void bindDish(UserItem userItem) {
            mUserItem = userItem;
            mDishName.setText(mUserItem.getDish().getName());
            mDishUnits.setText(mUserItem.getDish().getUnits());
            mDishCalories.setText(String.valueOf(mUserItem.getDish().getCalories()*mUserItem.getAmount() + "кКал"));
            mDishProteins.setText(String.valueOf("Білки: " + mUserItem.getDish().getProteins()*mUserItem.getAmount()));
            mDishFats.setText(String.valueOf("Жири: " + mUserItem.getDish().getFats()*mUserItem.getAmount()));
            mDishCarbohydrates.setText(String.valueOf("Вуглеводи: " + mUserItem.getDish().getCarbohydrates()*mUserItem.getAmount()));
            mDishAmount.setText(String.valueOf(mUserItem.getAmount()));
        }

    }
}
