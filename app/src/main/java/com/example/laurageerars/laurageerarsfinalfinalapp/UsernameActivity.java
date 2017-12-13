package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsernameActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText usernameField;
    Button addusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        usernameField = (EditText) findViewById(R.id.username);
        addusername = (Button) findViewById(R.id.usernamebutton);

        addusername.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is logged in (non-null), give toast en go to homepage
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            Toast.makeText(this, "Ingelogd!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
        }
        // If user is not logged in, give toast en log in or create new account
        else {

            Toast.makeText(this, "Er moet eerst worden ingelogd.", Toast.LENGTH_SHORT).show();
        }

    }*/

    private void saveUsername() {
        final String displayname = usernameField.getText().toString();

        if(displayname.isEmpty()) {
            Toast.makeText(this,"Vul een geldige gebruikersnaam in.",Toast.LENGTH_LONG).show();
            return;

        }

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(displayname).build();
            user.updateProfile(profile)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(UsernameActivity.this,"Gebruikersnaam is ingesteld: " + displayname,Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

    }

    //On click function for buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.usernamebutton:
                saveUsername();
                //Toast.makeText(this,"Gebruikersnaam ingesteld!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, HomepageActivity.class));
                break;

        }

    }
}
