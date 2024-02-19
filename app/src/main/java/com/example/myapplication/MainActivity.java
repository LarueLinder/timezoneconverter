package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";

    private static final String shared_prefs_file = "LocationSharedPref";
    private static final String key_username = "America/New York";

    Button timeButton;
    int hour;
    int min;

    int input_index;

    //this will have to be the index of the time zone set by the user: NOT DONE
    int output_index = 1;

    private Spinner currTimeZoneDropdown;

    //String home =
    SharedPreferences sharedPref;
    //String home = sharedPref.getString(key_username, "America/New York");
    String home;
    SharedPreferences.Editor peditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext(); // app level storage
        sharedPref = context.getSharedPreferences(String.valueOf(R.string.context_prefs), Context.MODE_PRIVATE);
        //peditor = sharedPref.edit();
        //peditor.putString("timeValue", "America/New York");
       // peditor.apply();
        Log.d("SharedPreferences", "Saved value6: " + sharedPref.getString("timeValue", "FAIL!"));


        //set current hometown to correct shared preference
        //TextView hometown = findViewById(R.id.homeTimeActual);
       // hometown.setText(home);

        timeButton = findViewById(R.id.timePickerButton);
        //set button to current time:
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeString = sdf.format(new Date(currentTime));
        timeButton.setText(timeString);


        currTimeZoneDropdown = findViewById(R.id.currTimeZoneDropdown);
        String[] time_zones = getResources().getStringArray(R.array.timezone_array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time_zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currTimeZoneDropdown.setAdapter(adapter);

        ImageButton settingButton = findViewById(R.id.settingsButton);

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
        TextView convertedTimer = findViewById(R.id.convertedTime);

        clockConvertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the SettingsActivity
                int time_dif = time_calc[output_index] - time_calc[input_index];
                //add time dif to time to get new time and display
                int new_hour = hour + time_dif;

                // check if the current timezone is the same as the home timezone
                String currentTimeZone = currTimeZoneDropdown.getSelectedItem().toString();
                home = sharedPref.getString("timeValue", "FAIL");

                if (currentTimeZone.equals(home)) {
                    // display te toast
                    Toast.makeText(MainActivity.this, "Current and home timezones are the same", Toast.LENGTH_SHORT).show();
                    return; //dont update the time
                }

                Log.d("Test", "here");
                // check if the converted time is between 11pm and 7am in the home time zone
                if (isDoNotDisturbHours(new_hour)) {
                    //do not disturb
                    Log.d("DoNotDisturb", "Do not disturb hours!!");
                    Toast.makeText(MainActivity.this, "Do not disturb hours", Toast.LENGTH_SHORT).show();
                }
                convertedTimer.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, min));

            }
        });

    }
    // check if current time is in do not disturb hours
    private boolean isDoNotDisturbHours(int convertedHour) {
        Log.d("DoNotDisturb", "in function");
        Log.d("DoNotDisturb", String.valueOf(convertedHour));
        return convertedHour >= 23 || convertedHour < 7;
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

    @Override
    protected void onStart() {
        super.onStart();

        //home = sharedPref.getString(key_username, "America/New York");
        home = sharedPref.getString("timeValue", "America/New York");
        TextView hometown = (TextView) findViewById(R.id.homeTimeActual);
        hometown.setText(home);

    }

    @Override
    protected void onResume() {

        super.onResume();
        home = sharedPref.getString("timeValue", "FAIL");
        TextView hometown = findViewById(R.id.homeTimeActual);
        hometown.setText(home);
        Log.d("SharedPreferences", "Saved value5: " + home); // Log the saved SharedPreferences value
    }

    @Override
    protected void onPause() {
        peditor = sharedPref.edit();
        peditor.putString("timeValue", home);
        peditor.apply();

        super.onPause();
    }

    @Override
    protected void onStop() {

        //peditor.putString(key_username, home);
        peditor.putString("timeValue", home);
        peditor.apply();

        super.onStop();
    }
}

