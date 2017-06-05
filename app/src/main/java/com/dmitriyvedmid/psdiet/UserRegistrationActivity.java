package com.dmitriyvedmid.psdiet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmitriyvedmid.psdiet.DietEditIntent.DietEditActivity;

public class UserRegistrationActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mHeightEditText;
    private EditText mWeightEditText;
    PrefManager prefManager;
    public static String uName = "nam";
    public static int uTable = 1;
    public static int uAge = 0;
    public static int uHeight = 0;
    public static int uWeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] operations = {"operation1", "operation2"};
        setContentView(R.layout.activity_user_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        mNameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        mAgeEditText = (EditText) findViewById(R.id.user_age_edit_text);
        mHeightEditText = (EditText) findViewById(R.id.user_height_edit_text);
        mWeightEditText = (EditText) findViewById(R.id.user_weight_edit_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.choose_operation_spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                uTable = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((mNameEditText.getText().length() >= 1) && (mAgeEditText.getText().length() >= 1) && (mHeightEditText.getText().length() > 1) && (mWeightEditText.getText().length() > 1)) {
            Intent intent = new Intent(getApplicationContext(), DietEditActivity.class);
            prefManager = new PrefManager(getApplicationContext());
            prefManager.setFirstTimeLaunch(false);
            uName = mNameEditText.getText().toString();
            prefManager.setUserName(uName);
            uAge = Integer.parseInt(mAgeEditText.getText().toString());
            prefManager.setUserAge(uAge);
            uHeight = Integer.parseInt(mHeightEditText.getText().toString());
            prefManager.setUserHeight(uHeight);
            uWeight = Integer.parseInt(mWeightEditText.getText().toString());
            prefManager.setUserWeight(uWeight);
            prefManager.setUserTable(uTable);
            startActivity(intent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Ведіть повну інформацію для продовження", Toast.LENGTH_LONG);
            View toastView = toast.getView();
            TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
            toastMessage.setTextSize(25);
            toastMessage.setTextColor(Color.BLACK);
            toastMessage.setGravity(Gravity.CENTER);
            toastMessage.setPadding(30, 10, 30, 10);
            toastView.setBackgroundColor(Color.WHITE);
            toast.show();
        }
        return true;
    }
}
