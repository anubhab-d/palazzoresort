package com.example.android.hotelbook;

import android.app.Activity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database extends Activity {

    private Connection connection;

    //private final String host = "ssprojectinstance.csv2nbvvgbcb.us-east-2.rds.amazonaws.com" ; // For Amazon Postgresql
    private final String host = "192.168.0.106";
    // For Google Cloud Postgresql
    //private final String host = "0.0.1";
    private final String database = "postgres";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "Anubhab1#$%";
    private String url = "jdbc:postgresql://192.168.0.106:5432/postgres";
    private boolean status;



    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);

    }

    public Connection connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return null;
    }

    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

   // String q = "Insert into customers(ID, Name, Phone number, E-Mail, Room type, Room No., Check In, Check Out, No. of guests, AADHAR, Address) values()";


}
