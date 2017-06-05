package com.dmitriyvedmid.psdiet.DietEditIntent;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dmitriyvedmid.psdiet.DBHelper;
import com.dmitriyvedmid.psdiet.MyDialogFragment;
import com.dmitriyvedmid.psdiet.PrefManager;
import com.dmitriyvedmid.psdiet.R;
import com.dmitriyvedmid.psdiet.RVAdapters.BreakfastRVAdapter;
import com.dmitriyvedmid.psdiet.RVAdapters.DinnerRVAdapter;
import com.dmitriyvedmid.psdiet.RVAdapters.LunchRVAdapter;
import com.dmitriyvedmid.psdiet.RVAdapters.SupperRVAdapter;
import com.dmitriyvedmid.psdiet.UserItem;
import com.dmitriyvedmid.psdiet.UserRegistrationActivity;

import java.util.ArrayList;

import static com.dmitriyvedmid.psdiet.DBHelper.TABLE_NAME;
import static com.dmitriyvedmid.psdiet.MainActivity.sDishes;
import static com.dmitriyvedmid.psdiet.MainActivity.sUserItems;

public class DietEditFragment extends Fragment implements View.OnClickListener {

    ListView listView = null;
    LinearLayout breakfastAdd;
    LinearLayout lunchAdd;
    LinearLayout dinnerAdd;
    LinearLayout supperAdd;
    TextView breakfastCalories;
    TextView breakfastProteins;
    TextView breakfastFats;
    TextView breakfastCarbohydrates;
    TextView lunchCalories;
    TextView lunchProteins;
    TextView lunchFats;
    TextView lunchCarbohydrates;
    TextView dinnerCalories;
    TextView dinnerProteins;
    TextView dinnerFats;
    TextView dinnerCarbohydrates;
    TextView supperCalories;
    TextView supperProteins;
    TextView supperFats;
    TextView supperCarbohydrates;
    public static BreakfastRVAdapter breakfastAdapter;
    public static LunchRVAdapter lunchAdapter;
    public static DinnerRVAdapter dinnerAdapter;
    public static SupperRVAdapter supperAdapter;
    public static ArrayList<UserItem> breakfastList = new ArrayList<>();
    public static ArrayList<UserItem> lunchList = new ArrayList<>();
    public static ArrayList<UserItem> dinnerList = new ArrayList<>();
    public static ArrayList<UserItem> supperList = new ArrayList<>();
    public static int mealClicked;
    public static ArrayList<String> names = new ArrayList<>();
    public static final String TAG = "My";
    View view;
    public static float mUserIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        fillLists();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_diet_edit, container, false);

        listView = new ListView(getActivity());
        breakfastCalories = (TextView) view.findViewById(R.id.breakfast_calories);
        breakfastProteins = (TextView) view.findViewById(R.id.breakfast_proteins);
        breakfastFats = (TextView) view.findViewById(R.id.breakfast_fats);
        breakfastCarbohydrates = (TextView) view.findViewById(R.id.breakfast_carbohydrates);
        lunchCalories = (TextView) view.findViewById(R.id.lunch_calories);
        lunchProteins = (TextView) view.findViewById(R.id.lunch_proteins);
        lunchFats = (TextView) view.findViewById(R.id.lunch_fats);
        lunchCarbohydrates = (TextView) view.findViewById(R.id.lunch_carbohydrates);
        dinnerCalories = (TextView) view.findViewById(R.id.dinner_calories);
        dinnerProteins = (TextView) view.findViewById(R.id.dinner_proteins);
        dinnerFats = (TextView) view.findViewById(R.id.dinner_fats);
        dinnerCarbohydrates = (TextView) view.findViewById(R.id.dinner_carbohydrates);
        supperCalories = (TextView) view.findViewById(R.id.supper_calories);
        supperProteins = (TextView) view.findViewById(R.id.supper_proteins);
        supperFats = (TextView) view.findViewById(R.id.supper_fats);
        supperCarbohydrates = (TextView) view.findViewById(R.id.supper_carbohydrates);
        breakfastAdd = (LinearLayout) view.findViewById(R.id.breakfast_add_dish_button);
        lunchAdd = (LinearLayout) view.findViewById(R.id.lunch_add_dish_button);
        dinnerAdd = (LinearLayout) view.findViewById(R.id.dinner_add_dish_button);
        supperAdd = (LinearLayout) view.findViewById(R.id.supper_add_dish_button);
        breakfastAdd.setOnClickListener(this);
        lunchAdd.setOnClickListener(this);
        dinnerAdd.setOnClickListener(this);
        supperAdd.setOnClickListener(this);
        breakfastList.clear();
        lunchList.clear();
        dinnerList.clear();
        supperList.clear();
        fillLists();
        setAdapters(view);
        return view;
    }

    public static void updateRVs() {
        breakfastAdapter.notifyDataSetChanged();
        lunchAdapter.notifyDataSetChanged();
        dinnerAdapter.notifyDataSetChanged();
        supperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().finish();
    }

    public void dialogCreation() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFragment newFragment = MyDialogFragment.newInstance(R.string.choose_meal);
        newFragment.setTargetFragment(this, 1);
        newFragment.show(fragmentManager, "dialog");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        fillLists();
        updateRVs();
    }

    public void fillLists() {
        breakfastList.clear();
        lunchList.clear();
        dinnerList.clear();
        supperList.clear();
        for (int i = 0; i < sUserItems.size(); i++) {
            switch (sUserItems.get(i).getMeal()) {
                case 0:
                    breakfastList.add(sUserItems.get(i));
                    break;
                case 1:
                    lunchList.add(sUserItems.get(i));
                    break;
                case 2:
                    dinnerList.add(sUserItems.get(i));
                    break;
                case 3:
                    supperList.add(sUserItems.get(i));
                    break;
            }
        }
        updateSums();
    }

    public void updateSums(){

        int calories = 0;
        int proteins = 0;
        int fats = 0;
        int carbohydrates = 0;
        for (int i = 0; i != breakfastList.size(); i++) {
            calories += breakfastList.get(i).getDish().getCalories()*breakfastList.get(i).getAmount();
            proteins += breakfastList.get(i).getDish().getProteins()*breakfastList.get(i).getAmount();
            fats += breakfastList.get(i).getDish().getFats()*breakfastList.get(i).getAmount();
            carbohydrates += breakfastList.get(i).getDish().getCarbohydrates()*breakfastList.get(i).getAmount();
        }
        breakfastCalories.setText(String.valueOf(calories + " кКал"));
        breakfastProteins.setText(String.valueOf("Білки: " + proteins));
        breakfastFats.setText(String.valueOf("Жири: " + fats));
        breakfastCarbohydrates.setText(String.valueOf("Вуглеводи: " + carbohydrates));
        calories = 0;
        proteins = 0;
        fats = 0;
        carbohydrates = 0;
        for (int i = 0; i < lunchList.size(); i++) {
            calories += lunchList.get(i).getDish().getCalories()*lunchList.get(i).getAmount();
            proteins += lunchList.get(i).getDish().getProteins()*lunchList.get(i).getAmount();
            fats += lunchList.get(i).getDish().getFats()*lunchList.get(i).getAmount();
            carbohydrates += lunchList.get(i).getDish().getCarbohydrates()*lunchList.get(i).getAmount();
        }
        lunchCalories.setText(String.valueOf(calories + " кКал"));
        lunchProteins.setText(String.valueOf("Білки: " + proteins));
        lunchFats.setText(String.valueOf("Жири: " + fats));
        lunchCarbohydrates.setText(String.valueOf("Вуглеводи: " + carbohydrates));
        calories = 0;
        proteins = 0;
        fats = 0;
        carbohydrates = 0;
        for (int i = 0; i < dinnerList.size(); i++) {
            calories += dinnerList.get(i).getDish().getCalories()*dinnerList.get(i).getAmount();
            proteins += dinnerList.get(i).getDish().getProteins()*dinnerList.get(i).getAmount();
            fats += dinnerList.get(i).getDish().getFats()*dinnerList.get(i).getAmount();
            carbohydrates += dinnerList.get(i).getDish().getCarbohydrates()*dinnerList.get(i).getAmount();
        }
        dinnerCalories.setText(String.valueOf(calories + " кКал"));
        dinnerProteins.setText(String.valueOf("Білки: " + proteins));
        dinnerFats.setText(String.valueOf("Жири: " + fats));
        dinnerCarbohydrates.setText(String.valueOf("Вуглеводи: " + carbohydrates));
        calories = 0;
        proteins = 0;
        fats = 0;
        carbohydrates = 0;
        for (int i = 0; i < supperList.size(); i++) {
            calories += supperList.get(i).getDish().getCalories()*supperList.get(i).getAmount();
            proteins += supperList.get(i).getDish().getProteins()*supperList.get(i).getAmount();
            fats += supperList.get(i).getDish().getFats()*supperList.get(i).getAmount();
            carbohydrates += supperList.get(i).getDish().getCarbohydrates()*supperList.get(i).getAmount();
        }
        supperCalories.setText(String.valueOf(calories + " кКал"));
        supperProteins.setText(String.valueOf("Білки: " + proteins));
        supperFats.setText(String.valueOf("Жири: " + fats));
        supperCarbohydrates.setText(String.valueOf("Вуглеводи: " + carbohydrates));
    }

    public void setAdapters(View view) {
        RecyclerView breakfastRecyclerView = (RecyclerView) view.findViewById(R.id.edit_diet_breakfast_recycler_view);
        LinearLayoutManager blinearLayoutManager = new LinearLayoutManager(getActivity());
        breakfastRecyclerView.setLayoutManager(blinearLayoutManager);
        breakfastAdapter = new BreakfastRVAdapter(breakfastList);
        breakfastRecyclerView.setAdapter(breakfastAdapter);

        RecyclerView lunchRecyclerView = (RecyclerView) view.findViewById(R.id.edit_diet_lunch_recycler_view);
        LinearLayoutManager llinearLayoutManager = new LinearLayoutManager(getActivity());
        lunchRecyclerView.setLayoutManager(llinearLayoutManager);
        lunchAdapter = new LunchRVAdapter(lunchList);
        lunchRecyclerView.setAdapter(lunchAdapter);

        RecyclerView dinnerRecyclerView = (RecyclerView) view.findViewById(R.id.edit_diet_dinner_recycler_view);
        LinearLayoutManager dlinearLayoutManager = new LinearLayoutManager(getActivity());
        dinnerRecyclerView.setLayoutManager(dlinearLayoutManager);
        dinnerAdapter = new DinnerRVAdapter(dinnerList);
        dinnerRecyclerView.setAdapter(dinnerAdapter);

        RecyclerView supperRecyclerView = (RecyclerView) view.findViewById(R.id.edit_diet_supper_recycler_view);
        LinearLayoutManager slinearLayoutManager = new LinearLayoutManager(getActivity());
        supperRecyclerView.setLayoutManager(slinearLayoutManager);
        supperAdapter = new SupperRVAdapter(supperList);
        supperRecyclerView.setAdapter(supperAdapter);
        breakfastAdapter.notifyDataSetChanged();
        lunchAdapter.notifyDataSetChanged();
        dinnerAdapter.notifyDataSetChanged();
        supperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        names.clear();
        PrefManager prefManager = new PrefManager(getContext());
        mUserIndex = prefManager.getUserWeight() / ((((float) prefManager.getUserHeight()) / 100) * (((float) prefManager.getUserHeight()) / 100));
        switch (v.getId()) {
            case R.id.breakfast_add_dish_button:
                for (int i = 0; i < sDishes.size(); i++) {
                    if ((sDishes.get(i).isBreakfast()) && (sDishes.get(i).getTable() == prefManager.getUserTable())) {
                        names.add(sDishes.get(i).getName());
                    }
                }
                for (int j = 0; j != breakfastList.size(); j++)
                    for (int k = 0; k != names.size(); k++)
                        if (breakfastList.get(j).getDish().getName().equals(names.get(k)))
                            names.remove(k);
                mealClicked = 0;
                dialogCreation();
                break;
            case R.id.lunch_add_dish_button:
                for (int i = 0; i < sDishes.size(); i++) {
                    if ((sDishes.get(i).isLunch()) && (sDishes.get(i).getTable() == prefManager.getUserTable())) {
                        names.add(sDishes.get(i).getName());
                    }
                }
                for (int j = 0; j != lunchList.size(); j++)
                    for (int k = 0; k != names.size(); k++)
                        if (lunchList.get(j).getDish().getName().equals(names.get(k)))
                            names.remove(k);
                mealClicked = 1;
                dialogCreation();
                break;
            case R.id.dinner_add_dish_button:
                for (int i = 0; i < sDishes.size(); i++) {
                    if ((sDishes.get(i).isDinner()) && (sDishes.get(i).getTable() == prefManager.getUserTable())) {
                        names.add(sDishes.get(i).getName());
                    }
                }
                for (int j = 0; j != dinnerList.size(); j++)
                    for (int k = 0; k != names.size(); k++)
                        if (dinnerList.get(j).getDish().getName().equals(names.get(k)))
                            names.remove(k);
                mealClicked = 2;
                dialogCreation();
                break;
            case R.id.supper_add_dish_button:
                for (int i = 0; i < sDishes.size(); i++) {
                    if ((sDishes.get(i).isSupper()) && (sDishes.get(i).getTable() == prefManager.getUserTable())) {
                        names.add(sDishes.get(i).getName());
                    }
                }
                for (int j = 0; j != supperList.size(); j++)
                    for (int k = 0; k != names.size(); k++)
                        if (supperList.get(j).getDish().getName().equals(names.get(k)))
                            names.remove(k);
                mealClicked = 3;
                dialogCreation();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_diet, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_diet_delete_current_user_menu_item:
                sUserItems.clear();
                DBHelper dbHelper = new DBHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("drop table if exists " + TABLE_NAME);
                dbHelper.onCreate(db);
                fillLists();
                updateRVs();
                PrefManager prefManager = new PrefManager(getContext());
                prefManager.setUserName(null);
                prefManager.setUserAge(0);
                prefManager.setUserHeight(0);
                prefManager.setUserTable(0);
                prefManager.setUserWeight(0);
                prefManager.setFirstTimeLaunch(true);
                Intent i = new Intent(getActivity(), UserRegistrationActivity.class);
                startActivity(i);
                getActivity().finish();
                break;
            case R.id.edit_diet_refresh_menu_item:
                updateSums();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}