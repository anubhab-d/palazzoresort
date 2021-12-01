package com.example.android.hotelbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class hotels extends Activity {
    public static final String H1_NAME = "com.example.android.hotelbook.H1_NAME";
    public static final String H1_DESC = "com.example.android.hotelbook.H1_DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        Button book1 = (Button) findViewById(R.id.book1);
        Button book2 = (Button) findViewById(R.id.book2);
        Button book3 = (Button) findViewById(R.id.book3);
        String h1 = "Standard";
        String desc1 = "376 square feet in area room offers Alarm Clock, Bathrobe, Bedside lamp, Carpeted Floor, Direct Dialing, Express Laundry Service, Flat-screen TVs, Full length Mirror, Hair Dryer, Hangers, In-room Menu, In-room Safe, Laundry Bag, Luggage space, Marble flooring, Mini Bar, Parallel phone line in bathroom, Phone Line, Safety Lockers, Satellite TV, Shower Cubicle, Slippers, Table lamp, Tea/Coffee Maker, Temp Control, Turn down service, Weighing Machine, Writing Desk";
        String h2 = "Deluxe";
        String desc2 = "Deluxe suite rooms are well decorated and fully furnished rooms, it has all the basic amenities like private attached bathroom, in room services are also available, internet and intercom facilities are also available, it will be a memorable stay for the guests and will enjoy the hospitality and will experience the best stay in the hotel, guests will have a lavish and luxurious stay and will experience the best ambience in the hotel.";
        String h3 = "Executive";
        String desc3 = "Executive rooms are well decorated and fully furnished rooms, it has all the basic amenities like private attached bathroom, in room services are also available, internet and intercom facilities are also available, it will be a memorable stay for the guests and will enjoy the hospitality and will experience the best stay in the hotel, guests will have a lavish and luxurious stay and will experience the best ambience in the hotel.";
        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), hotelinfo.class);
                i.putExtra(H1_NAME, h1);
                i.putExtra(H1_DESC, desc1);
                startActivity(i);
            }
        });

        book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), hotelinfo.class);
                i.putExtra(H1_NAME, h2);
                i.putExtra(H1_DESC, desc2);
                startActivity(i);
            }
        });

        book3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), hotelinfo.class);
                i.putExtra(H1_NAME, h3);
                i.putExtra(H1_DESC, desc3);
                startActivity(i);
            }
        });

    }
}