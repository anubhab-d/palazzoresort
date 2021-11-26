package com.example.android.hotelbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class hotelinfo extends Activity {
    public static final String H_NAME = "com.example.android.hotelbook.H1_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelinfo);
        Intent intent = getIntent();
        String name = intent.getStringExtra(hotels.H1_NAME);
        String desc = intent.getStringExtra(hotels.H1_DESC);
        TextView nametext = (TextView) findViewById(R.id.Name);
        TextView desctext = (TextView) findViewById(R.id.desc);
        ImageView img = (ImageView) findViewById(R.id.imageView);
        nametext.setText(name);
        desctext.setText(desc);
        if(name.equals("Standard")){
            img.setImageResource(R.drawable.standard);
        }
        if(name.equals("Deluxe Suite")){
            img.setImageResource(R.drawable.deluxe);
        }
        if(name.equals("Executive Suite")){
            img.setImageResource(R.drawable.executive);
        }

        Button proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Bookingdates.class);
                i.putExtra(H_NAME, name);
                startActivity(i);
            }
        });

    }
}