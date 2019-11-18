package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.net.Uri;
import android.widget.Button;
import android.os.*;

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
    }



}
