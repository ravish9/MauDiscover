package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;

import android.net.Uri;
import android.widget.Button;
import android.os.*;


import android.os.Build;

import android.speech.tts.TextToSpeech;

import java.util.Locale;


import android.widget.Toast;


public class CaselaActivity extends AppCompatActivity {

    Button buttonCaselaMap;
    Button buttonCaselaWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casela);


        buttonCaselaWebsite =  findViewById(R.id.buttonCaselaWebsite);
        buttonCaselaMap =  findViewById(R.id.buttonCaselaMap);

        buttonCaselaWebsite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.caselapark.com"));
                startActivity(intent);
            }
        });

        buttonCaselaMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:-20.290816, 57.404182?q=");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 1000);
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
                    Toast.makeText(CaselaActivity.this, "There is no TTS engine on your device"
                            , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    myTTS.setLanguage(Locale.UK);
                    speak("You are viewing the casela  world of adventures");


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
}





