package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

// Activity for retrieving other users their username.
// This is for adding other users to a listview, so an user can look at other users favorites.
// Help with retrieving data out of firebase: https://stackoverflow.com/questions/37919455/how-to-get-string-from-firebase-realtime-db-that-contain-in-unique-key

public class OtherFavoritesActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<String> listfavorietanderen = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_favorites);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);

        // Setting on click to profile imageview.
        profiel.setOnClickListener(this);

        getFavoritesOthers();
    }

    // Function for retrieving other users their username out of Firebase.
    public void getFavoritesOthers() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Put the username into a string.
        final String user = currentUser.getDisplayName();

        // Get database reference.
        DatabaseReference mDatabase = database.getReferenceFromUrl("https://laurageerarsfinalfinalapp.firebaseio.com/favoriet/" );
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // For favorites in database, get children (in this case the username)
                for(DataSnapshot children : dataSnapshot.getChildren()) {
                    Log.v("favoriet anderen","   " + user);
                    // If the "child" username doesn't match with the current user, add it to the listview.
                    // This is for 'deleting' the current user out of the other favorites activity.
                    if(!Objects.equals(children.getKey(), user)) {
                        listfavorietanderen.add(children.getKey());
                    }
                    // Setting the adapter to create the listview.
                    Adapter();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Cancelled", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    //Function for adding the usernames of other users to a listview.
    public void Adapter() {
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listfavorietanderen);
        ListView listViewFavorietAnderen = (ListView) findViewById(R.id.favorietanderenlijst);
        listViewFavorietAnderen.setAdapter(theAdapter);
        listViewFavorietAnderen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OtherFavoritesActivity.this, ProfileActivity.class);
                // For 'sending' the right user, the user clicked on, to the profile activity.
                intent.putExtra("user", String.valueOf(adapterView.getItemAtPosition(i)));
                startActivity(intent);
            }
        });
    }


    // On click function for going to the profile of the user.
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profielfoto:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }}