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

// Activity for setting up an username for the user.
// This will be for showing the favorites of other users later on in the app.
// Help for setting username from: https://www.youtube.com/watch?v=qVaBY92sOSI
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

        //Setting views for add username button to on click.
        addusername.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    // Function for saving the username into Firebase.
    private void saveUsername() {
        final String displayname = usernameField.getText().toString();

        // Checking if username field is empty, if so, give toast.
        if(displayname.isEmpty()) {
            Toast.makeText(this,"Vul een geldige gebruikersnaam in.",Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        // If the user is registered, set the username (displayname) in Firebase.
        if(user != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(displayname).build();
            user.updateProfile(profile)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // If setting up username is successful, give toast.
                    if(task.isSuccessful()){
                        Toast.makeText(UsernameActivity.this,"Gebruikersnaam is ingesteld: " + displayname,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //On click function for the username button, if clicked, set username and go to the app homepage.
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.usernamebutton:
                saveUsername();
                startActivity(new Intent(this, HomepageActivity.class));
                break;
        }
    }
}
