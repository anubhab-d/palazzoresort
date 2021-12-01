package com.example.android.hotelbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.android.hotelbook.databinding.ActivityWelcomscreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class welcomscreen extends Activity {
    private static String uname, phno, email;
    ActivityWelcomscreenBinding binding;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    private TextView textView1;
    String uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomscreen);

        ImageView img =(ImageView)findViewById(R.id.imageView6);
        img.setImageResource(R.drawable.logotop);



        mAuth = FirebaseAuth.getInstance();
        textView1= (TextView) findViewById(R.id.textView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            uid = user.getUid();
            binding=ActivityWelcomscreenBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());


            if(!uid.isEmpty()){
                readData(uid);
            }

        }

        playVideo();

        Button book = findViewById(R.id.button3);
        Button check_in = findViewById(R.id.button5);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), hotels.class);
                startActivity(i);
            }
        });

        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), checkin.class);
                startActivity(i);
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
                        uname = String.valueOf(dataSnapshot.child("name").getValue());
                        phno = String.valueOf(dataSnapshot.child("mobnum").getValue());
                        email = String.valueOf(dataSnapshot.child("email").getValue());
                        textView1= (TextView) findViewById(R.id.textView);
                        textView1.setText(uname);
                    }

                }
                else {

                    Toast.makeText(welcomscreen.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
    private void playVideo() {

        VideoView video = (VideoView) findViewById(R.id.videoView);
        String videoPath = "android.resource://"+ getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        video.setVideoURI(uri);
        MediaController mc = new MediaController(this);
        video.setMediaController(mc);
        mc.setAnchorView(video);
    }

    public static String getName() {
        return uname;
    }
    public static String getPhno() {
        return phno;
    }
    public static String getEmail() {
        return email;
    }

}