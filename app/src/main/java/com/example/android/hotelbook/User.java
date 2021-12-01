package com.example.android.hotelbook;

public class User {


    public String name, mobnum, email, roomtype, roomno;
    public User(){


    }

    public  User(String name, String mobnum, String email, String roomtype, String roomno){
        this.name=name;
        this.mobnum = mobnum;
        this.email = email;
        this.roomtype = roomtype;
        this.roomno = roomno;
    }

}
