package com.example.android.hotelbook;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Insert extends Database{

    public static void insertMethod() {
        Connection connection;
        Statement statement;

        Database obj_db = new Database();
        connection = obj_db.getConnection();
        try {

            String query = "INSERT INTO customer_data(\"Cust_Name\", \"Phone\", \"E_Mail\", \"Room_type\", \"Room_No\", \"Check_In\", \"Check_Out\", \"Guests\", \"AADHAR\", \"Address\") VALUES ('Anubhab', '9903267691', 'adas99201@gmail.com', 'Deluxe', 254, '2021-11-28', '2021-11-29', 2, '538826147815', 'V.Pally, Sonarpur'); ";
            statement= connection.createStatement();
            statement.executeUpdate(query);
            //Log.v("Value", "Value inserted");

        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

}
