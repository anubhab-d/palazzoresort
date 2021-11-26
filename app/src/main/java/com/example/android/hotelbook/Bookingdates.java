package com.example.android.hotelbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Bookingdates extends Activity {
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


        TextView mail = (TextView)findViewById(R.id.mail);
        TextView phone = (TextView)findViewById(R.id.phone);
        TextView nameuser = (TextView)findViewById(R.id.name);

        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                phone.setText(value.getString("mobnum"));
                mail.setText(value.getString("email"));
                nameuser.setText(value.getString("name"));
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