package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// Activity for register a new user to the app (and so to Firebase).
// Register with help from: https://www.simplifiedcoding.net/android-firebase-tutorial-1/

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    //Defining views
    Button registerButton;
    Button backButton;
    EditText emailField;
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initialize views
        registerButton = (Button) findViewById(R.id.register_button);
        backButton  = (Button) findViewById(R.id.back_button);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        //Setting views for buttons to on clicks.
        registerButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    // Function for creating user in Firebase.
    private void createUser() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Checking if email field is empty, if so, give toast.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Vul een geldig emailadres in.",Toast.LENGTH_LONG).show();
            return;
        }

        // Checking if password field is empty, if so, give toast.
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Vul een wachtwoord in.",Toast.LENGTH_LONG).show();
            return;
        }

        //checking if password is longer than 6 characters, if not, give toast.
        if (password.length() < 6 ) {
            Toast.makeText(this,"Vul een wachtwoord in die bestaat uit minimaal 6 karakters.",Toast.LENGTH_LONG).show();
            return;
        }

        // Create a new user into Firebase.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Register success, go to the username activity to set an username.
                            Log.d("Register successful", "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this,"Succesvol geregistreerd!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, UsernameActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // If register fails, display a message to the user.
                            Log.w("Register failure", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Er is iets foutgegaan met registreren.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // On click function for register user, or going back and go to the main activity to log in.
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_button:
                createUser();
                break;
            case R.id.back_button:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
