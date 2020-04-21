package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.Manifest.permission.RECORD_AUDIO;


public class HomeActivity extends AppCompatActivity  {
    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    Spinner mySpinner;
    Button buttonCasela;


    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SensorManager sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor= sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            private WindowManager.LayoutParams params;

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[0]<proximitySensor.getMaximumRange()){
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = 0;
                    getWindow().setAttributes(params);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(sensorEventListener,proximitySensor,2 * 1000* 1000);




        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO} , PackageManager.PERMISSION_GRANTED);

        configureMenuButton();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buttonCasela = findViewById(R.id.buttonCasela);

        mySpinner=(Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Tourist));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id)
            {
                final Intent intent;
                switch(position)
                {
                    case 1:
                        intent = new Intent(HomeActivity.this, CulturalActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(HomeActivity.this, AdventureActivity.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(HomeActivity.this, SportsActivity.class);
                        startActivity(intent);
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        buttonCasela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intToCasela = new Intent(HomeActivity.this, CaselaActivity.class);
                startActivity(intToCasela);
            }
        });





        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }




    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) ;
        {
            mySpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    List<String> results = bundle.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                    );
                    ProcessResults(results.get(0));

                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });


        }
    }

    private void ProcessResults(String command) {
        command = command.toLowerCase();

        if (command.indexOf("crocodile") != -1) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.lavanille-naturepark.com/index.php/en/home-1"));
            startActivity(intent);
        }
    }


    private void initializeTextToSpeech() {
        myTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size()==0){
                    Toast.makeText(HomeActivity.this, "There is no TTS engine on your device"
                            , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    myTTS.setLanguage(Locale.UK);
                    speak("Welcome to modiscover");


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





    private void configureMenuButton(){
        ImageButton menu_button = findViewById(R.id.imageButton);
        menu_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, mainMenu.class));
            }
        });
    }


    private void btn_speak(){
        Button btn_speak = findViewById(R.id.btn_speak);
        btn_speak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                mySpeechRecognizer.startListening(intent);


            }


        });
    }


    @Override
    protected void onPause(){
        super.onPause();
        myTTS.shutdown();
    }

}
