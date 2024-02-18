package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private SharedPreferences sharedPref;
    private String home;
    SharedPreferences.Editor peditor;
    //this needs to be in on create cus context does not get initalized before the on create
   // SharedPreferences shared = getSharedPreferences(shared_prefs_file, Context.MODE_PRIVATE);
    private static final String shared_prefs_file = "LocationSharedPref";
    private static final String key_username = "America/New York";
    Button saveButton;

    private Spinner currTimeZoneDropdown;

    String spinnerResult = "America/New York";

    //Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        //
        SharedPreferences shared = getSharedPreferences(shared_prefs_file, Context.MODE_PRIVATE);


        saveButton = findViewById(R.id.saveButton);

        //SharedPreferences file

        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(shared_prefs_file, Context.MODE_PRIVATE);
        home = sharedPref.getString(key_username, "America/New York");
        peditor = sharedPref.edit();
        peditor.putString(key_username, "America/New York");
        peditor.apply();

        currTimeZoneDropdown = findViewById(R.id.currTimeZoneDropdown);
        String[] time_zones = getResources().getStringArray(R.array.timezone_array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time_zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currTimeZoneDropdown.setAdapter(adapter);


        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the selectged time zone from the spinner
                String spinnerResult = currTimeZoneDropdown.getSelectedItem().toString();

                // check if this equals home
                if (spinnerResult.equals(home)) {
                    //display toast
                    Toast.makeText(SettingsActivity.this, "Home and current timezone are the same", Toast.LENGTH_SHORT).show();
                    return; //stop it from going to main
                }

                //save selected time zone to shared pref
                SharedPreferences.Editor editor = shared.edit();
                editor.putString(key_username, spinnerResult);
                editor.apply();

                //go back to main activity
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.currTimeZoneDropdown) {
            String valueFromSpinner = adapterView.getItemAtPosition(i).toString();
            spinnerResult = valueFromSpinner;

            //if spinnerResult is equal to the home then pop up the toast?
            if(home.equals(spinnerResult)) {
                Toast.makeText(this,"Home and current timezone are the same", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //input_index = 0;
    }

    @Override
    public void onPause() {
        //write to shared preferences:
        SharedPreferences.Editor peditor = sharedPref.edit();
        peditor.putString(key_username, home);
        peditor.apply();
        super.onPause();

    }


}
