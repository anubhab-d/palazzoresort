package com.example.android.hotelbook;

import android.app.Application;
import com.google.firebase.firestore.FirebaseFirestore;
import com.firebase.client.Firebase;
import com.google.firebase.analytics.FirebaseAnalytics;

public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
