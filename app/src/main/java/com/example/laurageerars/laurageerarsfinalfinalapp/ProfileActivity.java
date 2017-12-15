package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    public ArrayList<String> listfavoriet = new ArrayList<String>();
    //public boolean startsWith(String output);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference("title");
    //fUID = database.getCurrentUser().getUid();
    FirebaseUser user;
    TextView favoriettest;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);
        final Button loguit = (Button) findViewById(R.id.logout_button);
        final Button anderefavorieten = (Button) findViewById(R.id.otherfavoritesbutton);
        //TextView favotest = (TextView) findViewById(R.id.favorietentest);

        final ListView listViewFavoriet = (ListView) findViewById(R.id.favorietlijst);

        mAuth = FirebaseAuth.getInstance();

        profiel.setOnClickListener(this);
        loguit.setOnClickListener(this);
        anderefavorieten.setOnClickListener(this);
        getFavorites();
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //favorietList.clear();
                //favoriettest.clear();

                for (DataSnapshot favorietSnapshot : dataSnapshot.getChildren()) {
                    Favoriet favoriet = favorietSnapshot.getValue(Favoriet.class);

                    favorietList.add(favoriet);
                    //favoriettest.(favoriet);
                }

                FavorietList adapter = new FavorietList(ProfileActivity.this, favorietList);
                listViewFavoriet.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/


    public void getFavorites() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
        final String user = currentUser.getDisplayName();

        DatabaseReference mDatabase = database.getReferenceFromUrl("https://laurageerarsfinalfinalapp.firebaseio.com/favoriet/" + user);
        //fUID = database.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.v("favoriet key","   " + dataSnapshot);
                for(DataSnapshot children : dataSnapshot.getChildren()) {
                    //Log.v("favoriet key","   " + children.getValue().toString());
                    Object values = children.getValue();
                    //Log.v("favoriet key", "" + values.toString());
                    //Favoriet favoriet = children.getValue(Favoriet.class);
                   // Log.v("favoriet key", "" + children);
                    //for(DataSnapshot child: children.getChildren());
                        //Log.v("favoriet key","   " + dataSnapshot.getChildren());
                        //Log.v("favoriet key","   " + children.getChildren());
                    String getvalues = values.toString();
                    //Log.v("favoriet key","   " + getvalues);
                    //if(values.toString().contains("title=")) {
                    String[] output = getvalues.split(",");
                    //Log.v("favoriet key","   " + Arrays.toString(output));
                    //Log.v("favoriet key","   " + output.length);
                    for (int i = 0; i < output.length; i++) {
                        String outputlol = output[i];
                        //Log.v("favoriet key", "   " + outputlol);
                        if (outputlol.contains("title=")) {
                            listfavoriet.add(outputlol);

                            //Log.v("favoriet hoi", "   " + outputlol.toString());
                        }
                    }
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

    public void Adapter() {
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listfavoriet);
        ListView listViewFavoriet = (ListView) findViewById(R.id.favorietlijst);
        listViewFavoriet.setAdapter(theAdapter);

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
                break;
            case R.id.otherfavoritesbutton:
                Intent anderefavorietenintent = new Intent(this, OtherFavoritesActivity.class);
                startActivity(anderefavorietenintent);

        }

    }
}
