package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);
        final Button loguit = (Button) findViewById(R.id.logout_button);

        profiel.setOnClickListener(this);
        loguit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profielfoto:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_button:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ProfileActivity.this, "Succesvol uitgelogd!",
                        Toast.LENGTH_SHORT).show();
                Intent loguitintent = new Intent(this, MainActivity.class);
                startActivity(loguitintent);
                finish();
        }

    }
}
