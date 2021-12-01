package com.example.android.hotelbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.HashMap;

public class checkin extends Activity {
    private DatabaseReference mDatabase;
    Connection connection= null;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    DatabaseReference reference;
    String name, roomt, roomno;
    TextView name_cin, roomn_cin, roomt_cin;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        /*TextView name_cin = (TextView) findViewById(R.id.name_cin);
        TextView room_type_cin = (TextView) findViewById(R.id.room_type_cin);
        TextView room_no_cin = (TextView) findViewById(R.id.room_no_cin);*/
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        fAuth =FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        ImageView img = (ImageView) findViewById(R.id.imageView5);
        img.setImageResource(R.drawable.ending);
        /*String name_c = Bookingdates.getName();
        name_cin.setText(name_c);

        String room_t=Bookingdates.getroomt();
        room_type_cin.setText(room_t);

        String room_no=Bookingdates.getroomno();
        room_no_cin.setText(""+room_no);*/
        readData(userID);

        Button cout =(Button) findViewById(R.id.button);
        cout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickConnect();
                onClickCreate();

                HashMap hashMap = new HashMap();
                hashMap.put("roomtype", "NA");
                hashMap.put("roomno", "NA");

                mDatabase.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(checkin.this, "Thank You for visiting!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });




    }

    private void readData(String uid) {

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if(task.getResult().exists()){
                        //Toast.makeText(welcomscreen.this, "Successfully read", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        name = String.valueOf(dataSnapshot.child("name").getValue());
                        roomt = String.valueOf(dataSnapshot.child("roomtype").getValue());
                        roomno = String.valueOf(dataSnapshot.child("roomno").getValue());

                        name_cin= (TextView) findViewById(R.id.name_cin);
                        name_cin.setText(name);

                        roomn_cin= (TextView) findViewById(R.id.room_no_cin);
                        roomn_cin.setText(roomno);

                        roomt_cin= (TextView) findViewById(R.id.room_type_cin);
                        roomt_cin.setText(roomt);
                    }

                }
                else {

                    //Toast.makeText(checkin.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }

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
                            //Toast.makeText(checkin.this, "Connected", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SQLTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(checkin.this, "Connection timeout", Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (SQLException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(checkin.this, "Failed to Connect", Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e("Tag", e.getMessage());

                }
            }
        }).start();
    }







    //Database db = new Database();
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
                        System.out.println("");
                        statement = connection.createStatement();
                        statement.execute("Delete from customer_data where \"Room_No\" = '"+roomno+"';");

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(checkin.this, "Deleted", Toast.LENGTH_LONG).show();
                        }
                    });
                }  catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(checkin.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }).start();
    }






}