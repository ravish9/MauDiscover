package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


public class EcologicalActivity extends AppCompatActivity {

    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecological);
        configureMenuButton();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mySpinner = (Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(EcologicalActivity.this,
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
                        intent = new Intent(EcologicalActivity.this, CulturalActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(EcologicalActivity.this, EcologicalActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(EcologicalActivity.this, AdventureActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(EcologicalActivity.this, SportsActivity.class);
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
                    Toast.makeText(EcologicalActivity.this, "There is no TTS engine on your device"
                            , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    myTTS.setLanguage(Locale.UK);
                    speak("This is the ecological section");


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
                startActivity(new Intent(EcologicalActivity.this, mainMenu.class));
            }
        });
    }
}
