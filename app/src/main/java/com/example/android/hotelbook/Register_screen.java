package com.example.android.hotelbook;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Register_screen extends Activity implements View.OnClickListener {

    private EditText name_edit, mobnum_edit, email_edit, pass_edit;
    private Button register;



    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        mAuth = FirebaseAuth.getInstance();


        register = (Button) findViewById(R.id.register);
        name_edit= (EditText) findViewById(R.id.name_edit);
        mobnum_edit= (EditText) findViewById(R.id.mobnum_edit);
        email_edit= (EditText) findViewById(R.id.email_edit);
        pass_edit= (EditText) findViewById(R.id.pass_edit);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                registerUser();
        }
    }

    private void registerUser() {
        String email = email_edit.getText().toString().trim();
        String name = name_edit.getText().toString().trim();
        String mob = mobnum_edit.getText().toString().trim();
        String pass = pass_edit.getText().toString().trim();

        if(name.isEmpty()){

            name_edit.setError("Name is required");
            name_edit.requestFocus();
            return;
        }

        if(mob.isEmpty()){

            mobnum_edit.setError("Mobile number is required");
            mobnum_edit.requestFocus();
            return;
        }

        if(email.isEmpty()){

            email_edit.setError("Email is required");
            email_edit.requestFocus();
            return;
        }

        if(pass.isEmpty()){

            pass_edit.setError("Mobile number is required");
            pass_edit.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_edit.setError("Please provide valid E-Mail");
            email_edit.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, mob, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register_screen.this, "Successfully Registered",Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(Register_screen.this, "Failed to register",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });








                        }

                    }
                });




    }
}