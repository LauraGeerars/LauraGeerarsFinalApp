package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void logIn() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Log in successful", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Log in successful!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                            startActivity(intent);
                        }

                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("Log in fail", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
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
