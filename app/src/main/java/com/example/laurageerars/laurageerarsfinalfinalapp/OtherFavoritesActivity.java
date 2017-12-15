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
import java.util.Arrays;
import java.util.Objects;

public class OtherFavoritesActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<String> listfavorietanderen = new ArrayList<String>();
    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_favorites);
        final ListView listViewFavorietAnderen = (ListView) findViewById(R.id.favorietlijst);

        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);

        profiel.setOnClickListener(this);
        getFavoritesOthers();
    }

    public void getFavoritesOthers() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String user = currentUser.getDisplayName();
        DatabaseReference mDatabase = database.getReferenceFromUrl("https://laurageerarsfinalfinalapp.firebaseio.com/favoriet/" );
        //fUID = database.getCurrentUser().getUid();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.v("favoriet anderen key","   " + dataSnapshot);
                for(DataSnapshot children : dataSnapshot.getChildren()) {
                    Log.v("favoriet anderen","   " + user);
                    if(!Objects.equals(children.getKey(), user)) {
                        listfavorietanderen.add(children.getKey());
                    }

                    //Log.v("favoriet anderen","   " + user);
                    //Object values = children.getValue();
                    //Log.v("favoriet key", "" + values.toString());
                    //Favoriet favoriet = children.getValue(Favoriet.class);
                    // Log.v("favoriet key", "" + children);
                    //for(DataSnapshot child: children.getChildren());
                    //Log.v("favoriet key","   " + dataSnapshot.getChildren());
                    //Log.v("favoriet key","   " + children.getChildren());
                    //String getvalues = values.toString();
                    //Log.v("favoriet key","   " + getvalues);
                    //if(values.toString().contains("title=")) {
                    //String[] output = getvalues.split(",");
                    //Log.v("favoriet key","   " + Arrays.toString(output));
                    //Log.v("favoriet key","   " + output.length);
                    //for (int i = 0; i < output.length; i++) {
                    //    String outputlol = output[i];
                    //    //Log.v("favoriet key", "   " + outputlol);
                    //    if (outputlol.contains("title=")) {
                    //        listfavorietanderen.add(outputlol);

                    //        Log.v("favoriet hoi", "   " + outputlol.toString());
                //}
                 //   }
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
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listfavorietanderen);
        ListView listViewFavorietAnderen = (ListView) findViewById(R.id.favorietanderenlijst);
        listViewFavorietAnderen.setAdapter(theAdapter);
        listViewFavorietAnderen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //gotoFavoActivity(adapterView.getItemAtPosition(i));
                Intent intent = new Intent(OtherFavoritesActivity.this, ProfileActivity.class);
                //intent.putExtra("FavoOtherListActivity", FavoOtherListActivity);
                intent.putExtra("user", String.valueOf(adapterView.getItemAtPosition(i)));
                startActivity(intent);

            }
        });
    }

    /*
    // Function for going to next activity (InfoActivity)
    public void gotoFavoActivity(String FavoOtherListActivity){
        Intent intent = new Intent(this, FavoOtherListActivity.class);
        intent.putExtra("FavoOtherListActivity", FavoOtherListActivity);
        startActivity(intent);
    }*/



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profielfoto:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }}