package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";

    Button timeButton;
    int hour;
    int min;

    int input_index;

    //this will have to be the index of the time zone set by the user: NOT DONE
    int output_index = 1;

    private Spinner currTimeZoneDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeButton = findViewById(R.id.timePickerButton);

        currTimeZoneDropdown = findViewById(R.id.currTimeZoneDropdown);
        String[] time_zones = getResources().getStringArray(R.array.timezone_array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time_zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currTimeZoneDropdown.setAdapter(adapter);

        Button settingButton = findViewById(R.id.settingsButton);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the SettingsActivity
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton clockConvertsButton = findViewById(R.id.clockConvertButton);
        int[] time_calc = getResources().getIntArray(R.array.timezone_calc);

        clockConvertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the SettingsActivity
                int time_dif = time_calc[output_index] - time_calc[input_index];
                //add time dif to time to get new time and display
               // Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
               // startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.currTimeZoneDropdown) {
            String valueFromSpinner = parent.getItemAtPosition(position).toString();
            input_index = position;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            input_index = 0;

    }


    //handles the pop up time picker for users to pick the time
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, min));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK; //can delete/change style

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, min, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }
}

