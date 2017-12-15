package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// Activity for showing the current user their profile, so their favorites,
// or the favorites of other users (see if statement in getFavorites).
// Help with retrieving data out of firebase: https://stackoverflow.com/questions/37919455/how-to-get-string-from-firebase-realtime-db-that-contain-in-unique-key


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    public ArrayList<String> listfavoriet = new ArrayList<String>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);
        final Button loguit = (Button) findViewById(R.id.logout_button);
        final Button anderefavorieten = (Button) findViewById(R.id.otherfavoritesbutton);

        mAuth = FirebaseAuth.getInstance();

        // Setting views to on clicks.
        profiel.setOnClickListener(this);
        loguit.setOnClickListener(this);
        anderefavorieten.setOnClickListener(this);

        getFavorites();
    }

    // Function for getting the current users favorites, or the favorites of other users.
    public void getFavorites() {
        currentUser = mAuth.getCurrentUser();

        // Retrieving intent with the username of the 'right' user who has been clicked on.
        Intent intent = getIntent();
        String otherUser = intent.getStringExtra("user");

        // If the user goes to their profile (and their is no user who has been clicked on in OtherFavoritesActivity
        // for displaying the favorites of other users), display the current users favorites.
        if (otherUser == null) {
            final String user = currentUser.getDisplayName();
            getFavorites(user);
            Log.d("true", user);
        }
        // If an other user has been clicked on in the activity for showing the usernames of other
        // users, display the favorites of the other user that has been clicked on.
        else {
            final TextView person = (TextView) findViewById(R.id.person);
            final String user = otherUser;
            person.setText("De favorieten van: " + user);
            getFavorites(user);
            Log.d("false", user);
        }
    }

    // Function for retrieving the favorite titles of users.
    public void getFavorites(String user) {
        DatabaseReference mDatabase = database.getReferenceFromUrl("https://laurageerarsfinalfinalapp.firebaseio.com/favoriet/" + user);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get favorites for an user out of Firebase.
                for(DataSnapshot children : dataSnapshot.getChildren()) {
                    // Object values is an object with all the values of favorites (title and objectNumber) of an user.
                    Object values = children.getValue();
                    // Set values into a string.
                    String getvalues = values.toString();
                    // Split string on a "," so the title and objectNumber are separated.
                    String[] output = getvalues.split(",");
                    // Looping through the string with titles and objectNumbers.
                    for (int i = 0; i < output.length; i++) {
                        String outputlol = output[i];
                        // If the value contains "title=" in it, give title without "title="
                        // and add it to the listview.
                        if (outputlol.contains("title=")) {
                            String result = outputlol.substring(7, outputlol.length()-1);
                            listfavoriet.add(result);
                        }
                    }
                    // Setting the adapter to create the listview.
                    Adapter();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Cancelled", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }

    // Function for adding the title of a favorite to a listview.
    public void Adapter() {
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listfavoriet);
        ListView listViewFavoriet = (ListView) findViewById(R.id.favorietlijst);
        listViewFavoriet.setAdapter(theAdapter);

    }

    // On click function for going to profile activity, for logging out, and
    // for going to the page with the list of other users of the app (for going to their favorites).
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profielfoto:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_button:
                // Logging out in the app, go to the login activity.
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ProfileActivity.this, "Succesvol uitgelogd!",
                        Toast.LENGTH_SHORT).show();
                Intent loguitintent = new Intent(this, MainActivity.class);
                startActivity(loguitintent);
                finish();
                break;
            case R.id.otherfavoritesbutton:
                Intent anderefavorietenintent = new Intent(this, OtherFavoritesActivity.class);
                startActivity(anderefavorietenintent);
        }
    }
}
