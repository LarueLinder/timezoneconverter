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

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String shared_prefs_file = "LocationSharedPref";
    private static final String key_username = "America/New York";
    Button saveButton = findViewById(R.id.saveButton);

    private Spinner currTimeZoneDropdown;

    String spinnerResult = "America/New York";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        //SharedPreferences file
        SharedPreferences shared = getSharedPreferences(shared_prefs_file, Context.MODE_PRIVATE;

        currTimeZoneDropdown = findViewById(R.id.currTimeZoneDropdown);
        String[] time_zones = getResources().getStringArray(R.array.timezone_array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time_zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currTimeZoneDropdown.setAdapter(adapter);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //write to shared preferences:
                SharedPreferences.Editor editor = shared.edit();
                editor.putString(key_username, spinnerResult);
                editor.apply();
                // Create an Intent to start the SettingsActivity
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //input_index = 0;
    }
}
