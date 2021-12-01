package com.example.android.hotelbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;

public class hotelinfo extends Activity {
    public static final String H_NAME = "com.example.android.hotelbook.H1_NAME";
    private Connection connection=null;
    String r;
    int count, count2, count3;
    TextView availability;
    static String name;
    String[] arrStandard;
    String[] arrStandardTotal = {"201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211"};

    String[] arrDeluxe;
    String[] arrDeluxeTotal = {"301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311"};

    String[] arrExecutive;
    String[] arrExecutiveTotal = {"401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "411"};
    static ArrayList<String> arrayStandardAvail = new ArrayList<String>();
    static ArrayList<String> arrayDeluxeAvail = new ArrayList<String>();
    static ArrayList<String> arrayExecutiveAvail = new ArrayList<String>();
    static ArrayList<String> arrayAvail = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelinfo);


        Intent intent = getIntent();
        name = intent.getStringExtra(hotels.H1_NAME);
        String desc = intent.getStringExtra(hotels.H1_DESC);
        TextView nametext = (TextView) findViewById(R.id.Name);
        TextView desctext = (TextView) findViewById(R.id.desc);
        availability = (TextView) findViewById(R.id.avail);
        onClickConnect();
        //onClickSelect();



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
            img.setImageResource(R.drawable.exec2);
        }

        Button check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onClickSelect();
            }
        });

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
                            //Toast.makeText(hotelinfo.this, "Connected", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SQLTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(hotelinfo.this, "Connection timeout", Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (SQLException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(hotelinfo.this, "Failed to Connect", Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e("Tag", e.getMessage());

                }
            }
        }).start();
    }

    void onClickSelect() {
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
                        //System.out.println("SELECT \"Room_No\" from customer_data where \"Room_type\" ='"+roomt+"';");
                        //statement = connection.createStatement();
                        //statement.execute("SELECT \"Room_No\" from customer_data where \"Room_type\" ='"+roomt+"';");


                        PreparedStatement stmt = connection.prepareStatement("SELECT * from customer_data where \"Room_type\" = '"+name+"';");
                        ResultSet result = stmt.executeQuery();
                        System.out.println(stmt);

                        while (result.next()){

                            String room = result.getString("Room_No");
                            r+= room+"\n";


                        }
                        //System.out.println(r);
                        if (name.equals("Standard")){
                            arrStandard = r.split("\n");
                            availableStd();
                            String c1 = String.valueOf(count);
                            availability.setText("Available: "+c1);


                        }
                        else if (name.equals("Deluxe")){
                            arrDeluxe = r.split("\n");
                            availableDlx();
                            String c2 = String.valueOf(count2);
                            availability.setText("Available: "+c2);


                        }

                        else if (name.equals("Executive")){
                            arrExecutive = r.split("\n");
                            availableExec();
                            String c3 = String.valueOf(count3);
                            availability.setText("Available: "+c3);


                        }









                    }


                    catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(hotelinfo.this, "Selected", Toast.LENGTH_LONG).show();
                        }
                    });
                }  catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(hotelinfo.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }).start();
    }
    public static ArrayList<String> getavail() {
        if (name.equals("Standard")){
            arrayAvail= arrayStandardAvail;

        }
        else if (name.equals("Deluxe")){
            arrayAvail= arrayDeluxeAvail;
        }
        else if (name.equals("Executive")){
            arrayAvail= arrayExecutiveAvail;
        }
        return arrayAvail;
    }
    public void availableStd(){
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

    }
    public void availableDlx(){
        int flag = 0;

        for(String a: arrDeluxeTotal){
            for(String b: arrDeluxe){
                if(b.equals(a)){
                    flag=1;

                }

            }
            if(flag == 1){
                flag=0;
                continue;
            }
            else if (flag==0){
                arrayDeluxeAvail.add(a);
            }
            count2 = arrayDeluxeAvail.size();


        }

    }
    public void availableExec(){
        int flag = 0;

        for(String a: arrExecutiveTotal){
            for(String b: arrExecutive){
                if(b.equals(a)){
                    flag=1;

                }

            }
            if(flag == 1){
                flag=0;
                continue;
            }
            else if (flag==0){
                arrayExecutiveAvail.add(a);
            }
            count3 = arrayExecutiveAvail.size();


        }

    }


}