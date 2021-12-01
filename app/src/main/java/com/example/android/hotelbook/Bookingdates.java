package com.example.android.hotelbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bookingdates extends Insert{
    private static final long TIME_OUT = 4000;
    private DatabaseReference mDatabase;


    int numguests=0;
    Connection connection  = null;
    Button plus;
    Button minus;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    static String nameu;
    static String roomt;
    static String roomn;
    String uname, phno, email, cin, cout, guest, aadhr, addres;
    String r = "";

    static  int count;
   /* List<String> list=new ArrayList<String>();
    String[] arrStandard;
    String[] arrStandardTotal = {"200", "201", "202", "203", "204", "205", "206", "207", "208", "209"};
    ArrayList<String> arrayStandardAvail = new ArrayList<String>();

    String[] arrDeluxe;
    String[] arrDeluxeTotal = {"300", "301", "302", "303", "304", "305", "306", "307", "308", "309"};
    ArrayList<String> arrayDeluxeAvail = new ArrayList<String>();

    String[] arrExec;
    String[] arrExecTotal = {"400", "401", "402", "403", "404", "405", "406", "407", "408", "409"};
    ArrayList<String> arrayExecAvail = new ArrayList<String>();*/


    String room;
    int min, max;
    TextView roomtype,aadhar, unametv, phnotv, emailtv, address, checkin, checkout, guests, sample;
    ArrayList<String> available = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdates);
        onClickConnect();
        fAuth =FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        TextView nameuser = (TextView)findViewById(R.id.name_final);
        TextView room_type = (TextView)findViewById(R.id.name_room);

        unametv = (TextView) findViewById(R.id.name_final);
        phnotv = (TextView) findViewById(R.id.phone_final);
        emailtv = (TextView) findViewById(R.id.mail_final);
        roomtype = (TextView) findViewById(R.id.name_room);
        aadhar = (TextView) findViewById(R.id.aadhar);
        address = (TextView) findViewById(R.id.address);
        checkin = (TextView) findViewById(R.id.indate);
        checkout = (TextView) findViewById(R.id.indate);
        guests = (TextView) findViewById(R.id.num);



        uname = welcomscreen.getName();
        phno= welcomscreen.getPhno();
        email= welcomscreen.getEmail();


        unametv.setText(uname);
        phnotv.setText(phno);
        emailtv.setText(email);



        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        Intent intent2 = new Intent(this, checkin.class);
        Button b = (Button) findViewById(R.id.confirm);


        available=hotelinfo.getavail();
        room=available.get(0);
        System.out.println(room);

        for(String a: available){
            System.out.println(""+a);

        }


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameu = nameuser.getText().toString();
                roomt = room_type.getText().toString();
                cin = checkin.getText().toString();
                cout = checkout.getText().toString();
                guest = guests.getText().toString();
                aadhr = aadhar.getText().toString();
                addres = address.getText().toString();


                Snackbar.make(findViewById(R.id.imageView2), "Booking confirmed",
                        Snackbar.LENGTH_SHORT)
                        .show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(intent2);
                        finish();
                    }
                }, TIME_OUT);
                roomn = room;
                onClickCreate();

                HashMap hashMap = new HashMap();
                hashMap.put("roomtype", roomt);
                hashMap.put("roomno", roomn);

                mDatabase.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        //Toast.makeText(Bookingdates.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    }
                });

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

    void onClickConnect() {
        new Thread(new Runnable() {
            public void run() {
                /*String p;
                if (!port.getText().toString().equals(""))
                    p = port.getText().toString();
                else p= "5432";*/

                try {
                    connection = DriverManager.getConnection(
                            "jdbc:postgresql://192.168.0.106:5432/postgres",
                            "postgres",
                            "Anubhab1#$%"
                    );

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(Bookingdates.this, "Connected", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SQLTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(Bookingdates.this, "Connection timeout", Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (SQLException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(Bookingdates.this, "Failed to Connect", Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e("Tag", e.getMessage());

                }
            }
        }).start();
    }


    void onClickCreate() {
        try {
            if (connection == null || connection.isClosed()) {
                //Toast.makeText(this, "Connection closed", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement statement = null;
                    try {
                        System.out.println("INSERT INTO customer_data(\"Cust_Name\", \"Phone\", \"E_Mail\", \"Room_type\", \"Room_No\", \"Check_In\", \"Check_Out\", \"Guests\", \"AADHAR\", \"Address\") VALUES ('"+uname+"', '"+phno+"', '"+email+"', '"+roomt+"', '"+roomn+"', '"+cin+"', '"+cout+"', "+guest+", '"+aadhr+"', '"+addres+"');");
                        statement = connection.createStatement();
                        statement.execute("INSERT INTO customer_data(\"Cust_Name\", \"Phone\", \"E_Mail\", \"Room_type\", \"Room_No\", \"Check_In\", \"Check_Out\", \"Guests\", \"AADHAR\", \"Address\") VALUES ('"+uname+"', '"+phno+"', '"+email+"', '"+roomt+"', '"+roomn+"', '"+cin+"', '"+cout+"', "+guest+", '"+aadhr+"', '"+addres+"');");

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Bookingdates.this, "Thank You for Booking!", Toast.LENGTH_LONG).show();
                        }
                    });
                }  catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Bookingdates.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }).start();
    }

    /*void onClickSelect() {
        try {
            if (connection == null || connection.isClosed()) {
                Toast.makeText(this, "Connection closed", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement statement = null;
                    try {
                        //System.out.println("SELECT \"Room_No\" from customer_data where \"Room_type\" ='"+roomt+"';");
                        //statement = connection.createStatement();
                        //statement.execute("SELECT \"Room_No\" from customer_data where \"Room_type\" ='"+roomt+"';");


                        PreparedStatement stmt = connection.prepareStatement("SELECT * from customer_data where \"Room_type\" = 'Standard';");
                        ResultSet result = stmt.executeQuery();
                        while (result.next()){

                            String room = result.getString("Room_No");
                            r+= room+"\n";


                        }
                        //System.out.println(r);
                        arrStandard = r.split("\n");
                        availableStd();


                        System.out.println(count);




                    }


                    catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Bookingdates.this, "Selected", Toast.LENGTH_LONG).show();
                        }
                    });
                }  catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Bookingdates.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }).start();
    }*/


    public void increase(){
        numguests+=1;
        TextView num =(TextView) findViewById(R.id.num);
        num.setText(""+numguests);

    }

   /* public void availableStd(){
        int flag = 0;
        for(String a: arrStandardTotal){
            for(String b: arrStandard){
                if(b.equals(a)){
                    flag=1;

                }

            }
            if(flag == 1){
                flag=0;
                continue;
            }
            else if (flag==0){
                arrayStandardAvail.add(a);
            }
            count = arrayStandardAvail.size();

        }
    }*/

    public void decrease(){
        if (numguests>0){
            numguests-=1;
        }

        TextView num =(TextView) findViewById(R.id.num);
        num.setText(""+numguests);
    }

    public static String getName() {
        return nameu;
    }

    public static String getroomt() {

        return roomt;
    }

    public static String getroomno() {

        return roomn;
    }

    public static Integer getcount() {

        return count;
    }

}

