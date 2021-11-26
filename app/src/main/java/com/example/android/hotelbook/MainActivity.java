package com.example.android.hotelbook;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends Activity implements View.OnClickListener{
    private EditText email_login, pass_login;
    private TextView register2;
    private Button login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database db = new Database();

        register2=(TextView) findViewById(R.id.textView3);
        register2.setOnClickListener(this);

        login =(Button) findViewById(R.id.button2);
        login.setOnClickListener(this);

        email_login = (EditText) findViewById(R.id.email_login);
        pass_login = (EditText) findViewById(R.id.pass_login);

        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView3:
                startActivity(new Intent(this, Register_screen.class));
                break;

            case R.id.button2:
                userLogin();
                break;
        }
    }






    private void userLogin() {

        String emailid = email_login.getText().toString().trim();
        String password = pass_login.getText().toString().trim();

        if(emailid.isEmpty()){

            email_login.setError("Email is required");
            email_login.requestFocus();
            return;
        }

        if(password.isEmpty()){

            pass_login.setError("Mobile number is required");
            pass_login.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
            email_login.setError("Please provide valid E-Mail");
            email_login.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, welcomscreen.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Incorrect E-Mail/Password",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}