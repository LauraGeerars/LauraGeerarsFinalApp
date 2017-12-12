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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    //Defining views
    Button registerButton;
    Button loginButton;
    EditText emailField;
    EditText passwordField;

    //Login with help from: https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initialize views
        registerButton = (Button) findViewById(R.id.register_button);
        loginButton  = (Button) findViewById(R.id.login_button);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        //Fixing on click at buttons on page
        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is logged in (non-null), give toast en go to homepage
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            Toast.makeText(MainActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(intent);
        }
        // If user is not logged in, give toast en log in or create new account
        else {

            Toast.makeText(MainActivity.this, "You have to log in first.", Toast.LENGTH_SHORT).show();
        }

    }
    private void logIn() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        //checking if email field is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Vul een geldig emailadres in.",Toast.LENGTH_LONG).show();
            return;
        }

        //checking if password field is empty
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Vul een geldig wachtwoord in.",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Log in successful", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Succesvol ingelogd!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                            startActivity(intent);
                        }

                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("Log in fail", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Er is iets foutgegaan met inloggen.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }

    //On click function for buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                logIn();
                break;
            case R.id.register_button:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }

    }
}
