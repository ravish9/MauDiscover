package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class mainMenu extends AppCompatActivity {

    Button button6;
    Button button3;
    Button button2;
    Button button5;
    Button button4;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        button6 = findViewById(R.id.button6);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        configureCloseButton();
    }

    private void configureCloseButton(){
        ImageButton close_button = (ImageButton) findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intToRestaurant = new Intent(mainMenu.this, RestaurantActivity.class);
                startActivity(intToRestaurant);
            }
        });




        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intToHotel = new Intent(mainMenu.this, HotelActivity.class);
                startActivity(intToHotel);
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intToShopping = new Intent(mainMenu.this, ShoppingActivity.class);
                startActivity(intToShopping);
            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intToShopping = new Intent(mainMenu.this, VoiceActivity.class);
                startActivity(intToShopping);
            }
        });


       button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(mainMenu.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

    }
}
