package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import android.os.Bundle;
import android.telecom.RemoteConference;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


import androidx.annotation.Nullable;


public class VoiceActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    float x1, x2, y1, y2;
    TextView firstNumTextView;
    //TextView secondNumTextView;
    TextView operatorTextView;
    TextView resultTextView;
    Button goButton;

    TextToSpeech textToSpeech;

    private float FIRST_NUMBER;
    private int SECOND_NUMBER;
    private char OPERATOR;
    private float RESULT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        configureMenuButton();

        textToSpeech = new TextToSpeech(this, this);

        firstNumTextView = findViewById(R.id.firstNumTextView);
        //secondNumTextView = findViewById(R.id.secondNumTextView);
        operatorTextView = findViewById(R.id.operatorTextView);
        resultTextView = findViewById(R.id.resultTextView);
        goButton = findViewById(R.id.goButton);

        firstNumTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
               intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(intent, 10);
            }
        });



        operatorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak("Enter the desired currency", TextToSpeech.QUEUE_ADD, null);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(intent, 30);
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RESULT = performCalculations();
                resultTextView.setText(String.valueOf(RESULT));
                textToSpeech.speak(String.valueOf(RESULT )+"Mauritian Rupees", TextToSpeech.QUEUE_ADD, null);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    int intFound = getNumberFromResult(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
                    if (intFound != -1) {
                        FIRST_NUMBER = intFound;
                        firstNumTextView.setText(String.valueOf(intFound));
                    } else {
                        //Toast.makeText(getApplicationContext(), "Sorry, I didn't catch that! Please try again", Toast.LENGTH_LONG).show();
                        textToSpeech.speak("Sorry Number not available. Please try again ", TextToSpeech.QUEUE_ADD, null);
                    }
                    break;

                case 30:
                    char operatorFound = getOperatorFromResult(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
                    if (operatorFound != '0') {
                        OPERATOR = operatorFound;
                        operatorTextView.setText(String.valueOf(operatorFound));
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry, I didn't catch that! Please try again", Toast.LENGTH_LONG).show();
                    }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }

    // method to loop through results trying to find a number
    private int getNumberFromResult(ArrayList<String> results) {
        for (String str : results) {
            if (getIntNumberFromText(str) != -1) {
                return getIntNumberFromText(str);
            }
        }
        return -1;
    }

    // method to loop through results trying to find an operator
    private char getOperatorFromResult(ArrayList<String> results) {
        for (String str : results) {
            if (getCharOperatorFromText(str) != '0') {
                return getCharOperatorFromText(str);
            }
        }
        return '0';
    }

    // method to convert string number to integer
    private int getIntNumberFromText(String strNum) {
        switch (strNum) {
            case "zero":
                return 0;
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
        }
        return -1;
    }

    // method to convert string operator to char
    private char getCharOperatorFromText(String strOper) {
        switch (strOper) {
            case "Pound":
                return '£';
            case "Yuan":
            case "Yen":
                return '¥';
            case "Euro":
                return '€';
            case "Indian rupee":
                return '₹';
            case "Dollar":
                return '$';


        }
        return '0';
    }

    private float performCalculations() {
        switch (OPERATOR) {
            case '₹':
                return (float) (FIRST_NUMBER *0.53);
            case '€':
                return (float) (FIRST_NUMBER *42.8);
            case '¥':
                return (float) (FIRST_NUMBER * 37.4);
            case '£':
                return (float) (FIRST_NUMBER * 40);
            case '$':
                return (float) (FIRST_NUMBER * 35);


        }
        return -999;
    }

    @Override
    public void onInit(int i) {

    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                if(x1 < x2){
                    Intent i = new Intent(VoiceActivity.this, RestaurantActivity.class);
                    startActivity(i);
                }
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 > x2){
                    Intent i = new Intent(VoiceActivity.this, HotelActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    private void configureMenuButton() {
        ImageButton menu_button = findViewById(R.id.imageButton);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VoiceActivity.this, mainMenu.class));
            }
        });
    }
}
