package com.example.prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.net.Uri;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HotelActivity extends AppCompatActivity {

    Button androidImageButton;
    Button androidImageButton2;
    Button androidImageButton3;
    Button androidImageButton4;
    Button androidImageButton5;
    Button buttonHotel1Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);



        buttonHotel1Map = findViewById(R.id.buttonHotel1Map);
        androidImageButton =  findViewById(R.id.buttonBook1);
        androidImageButton2 =  findViewById(R.id.buttonBook2);
        androidImageButton3 =  findViewById(R.id.buttonBook3);
        androidImageButton4 =  findViewById(R.id.buttonBook4);
        androidImageButton5 =  findViewById(R.id.buttonBook5);


        androidImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.angsana.com/en"));
                startActivity(intent);
            }
        });

        androidImageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.luxresorts.com/en/mauritius/hotel/luxbellemare/"));
                startActivity(intent);
            }
        });

        androidImageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.booking.com/hotel/mu/intercontinental-mauritius.en-gb.html?aid=356980;label=gog235jc-1DCAsonQFCGmludGVyY29udGluZW50YWwtbWF1cml0aXVzSDNYA2idAYgBAZgBCbgBF8gBDNgBA-gBAYgCAagCA7gC5pD77QXAAgE;sid=a839bd61a1d9d9018730638599d07b4e;dist=0&keep_landing=1&sb_price_type=total&type=total&"));
                startActivity(intent);
            }
        });

        androidImageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www3.hilton.com/en/hotels/mauritius/hilton-mauritius-resort-and-spa-MRUHIHI/index.html"));
                startActivity(intent);
            }
        });

        androidImageButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.fourseasons.com/mauritius/"));
                startActivity(intent);
            }
        });

        buttonHotel1Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:-20.099998, 57.513399?q=");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 1000);
            }
        });




        configureMenuButton();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void configureMenuButton() {
        ImageButton menu_button = findViewById(R.id.imageButton);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HotelActivity.this, mainMenu.class));
            }
        });
    }
}