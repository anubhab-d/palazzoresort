package com.example.android.hotelbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bookingdates extends Insert{
    int numguests=0;
    Button plus;
    Button minus;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdates);

        fAuth =FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        TextView mail = (TextView)findViewById(R.id.mail_final);
        TextView phone = (TextView)findViewById(R.id.phone_final);
        TextView nameuser = (TextView)findViewById(R.id.name_final);

        Button b = (Button) findViewById(R.id.confirm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertActor();
            }
        });






















        Intent intent = getIntent();
        String name = intent.getStringExtra(hotels.H1_NAME);
        TextView nametext = (TextView) findViewById(R.id.name_room);

        nametext.setText(name);
        //TextView num =(TextView) findViewById(R.id.num);
        //numguests  = Integer.parseInt(num.getText().toString());
        plus =(Button) findViewById(R.id.plus);
        minus =(Button) findViewById(R.id.minus);





        plus.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                increase();

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                decrease();
            }
        });




    }
    public void increase(){
        numguests+=1;
        TextView num =(TextView) findViewById(R.id.num);
        num.setText(""+numguests);

    }

    public void decrease(){
        if (numguests>0){
            numguests-=1;
        }

        TextView num =(TextView) findViewById(R.id.num);
        num.setText(""+numguests);
    }
}

