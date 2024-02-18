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

import android.util.Log;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private SharedPreferences sharedPref;
    private String home;
    SharedPreferences.Editor peditor;
    //this needs to be in on create cus context does not get initalized before the on create
   // SharedPreferences shared = getSharedPreferences(shared_prefs_file, Context.MODE_PRIVATE);
    private static final String shared_prefs_file = "LocationSharedPref";
    private static final String key_username = "timeValue";
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
        sharedPref = context.getSharedPreferences(String.valueOf(R.string.context_prefs), Context.MODE_PRIVATE);
        home = sharedPref.getString("timeValue", "America/New York");
        //peditor = sharedPref.edit();
        //peditor.putString(key_username, "America/New York");
        //peditor.apply();

        Log.d("SharedPreferences", "Saved value: " + home); // Log the saved SharedPreferences value

        currTimeZoneDropdown = findViewById(R.id.currTimeZoneDropdown);
        String[] time_zones = getResources().getStringArray(R.array.timezone_array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time_zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currTimeZoneDropdown.setAdapter(adapter);

        currTimeZoneDropdown.setOnItemSelectedListener(this);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //write to shared preferences:
                SharedPreferences.Editor peditor = sharedPref.edit();
                //peditor.putString(key_username, spinnerResult);
                peditor.putString("timeValue", spinnerResult);
                peditor.apply();

                Log.d("SharedPreferences", "Saved value1: " + spinnerResult); // Log the saved SharedPreferences value

                // Create an Intent to start the SettingsActivity

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
            spinnerResult = adapterView.getItemAtPosition(i).toString();
            //spinnerResult = valueFromSpinner;
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
        //peditor.putString(key_username, home);
        peditor.putString("timeValue", spinnerResult);

        peditor.apply();
        Log.d("SharedPreferences", "Saved value2: " + sharedPref.getString("timeValue", "failed")); // Log the saved SharedPreferences value
        super.onPause();

    }


}
