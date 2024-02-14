package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";

    Random randy = new Random();
    private int secret;
    private int guess;

    //on create is the first method that gets executed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //one of the primary methods we call that connects layout files and java code

        secret = randy.nextInt(100) + 1;
        Log.d("secret", secret + " "); //log functionality for debugging
    }

    /** Called when the user taps the Send button */
    //function is called w/ onClick in activity_main when button is clicked
    public void sendMessage(View view) {
        //to move from one activity to another we create an intent and then move to that
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //we get the text out here
        EditText editText = (EditText) findViewById(R.id.editText);

        guess = Integer.parseInt(editText.getText().toString());
        String message = "correct!";

        if (guess < secret ) {
            message = "go higher";
        } else if (guess > secret) {
            message = "go lower";
        }

        intent.putExtra(EXTRA_MESSAGE, message); //takes the current message and puts it in the intent w/ a unique label
        //inten extras are maps key is a string value is whatever value corresponds w that
        //when we start the acitivty we pass in the intent so it comes out
        startActivity(intent);
    }
}
//new comment
