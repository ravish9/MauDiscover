package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;

import java.util.Locale;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;

import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AdventureActivity extends AppCompatActivity {

    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    Spinner mySpinner;

    private SensorManager sm;

    private float acelVal;
    private float acelLast;
    private float shake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        configureMenuButton();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mySpinner = (Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AdventureActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Tourist));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }


            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {
                final Intent intent;
                switch (position) {
                    case 1:
                        intent = new Intent(AdventureActivity.this, CulturalActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(AdventureActivity.this, AdventureActivity.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(AdventureActivity.this, SportsActivity.class);
                        startActivity(intent);
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        initializeTextToSpeech();

    }

    private TextToSpeech myTTS;
    private void initializeTextToSpeech() {
        myTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size()==0){
                    Toast.makeText(AdventureActivity.this, "There is no TTS engine on your device"
                            , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    myTTS.setLanguage(Locale.UK);
                    speak("You are in the adventure section. Below are some places you might find interesting. We suggest to visit Le Morne and the seaplane.");


                }
            }
        });
    }

    private void speak(String message){
        if(Build.VERSION.SDK_INT>=21){
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }




    @Override
    protected void onPause(){
        super.onPause();
        myTTS.shutdown();
    }
    private void configureMenuButton(){
        ImageButton menu_button = findViewById(R.id.imageButton);
        menu_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdventureActivity.this, mainMenu.class));
            }
        });
    }

    private final SensorEventListener sensorListener= new SensorEventListener(){
        @Override
        public void onSensorChanged(SensorEvent sensorEvent){

            float x=sensorEvent.values[0];
            float y=sensorEvent.values[1];
            float z=sensorEvent.values[2];

            acelLast=acelVal;
            acelVal=(float) Math.sqrt((double) (x*x + y*y* + z*z));
            float delta=acelVal-acelLast;
            shake=shake*0.9f + delta;

            if(shake>40){
                Intent intToShopping = new Intent(AdventureActivity.this, SportsActivity.class);
                startActivity(intToShopping);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}