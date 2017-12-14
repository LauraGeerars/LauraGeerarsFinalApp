package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference("title");

    ListView listViewFavoriet;
    List<Favoriet> favorietList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);
        final Button loguit = (Button) findViewById(R.id.logout_button);
        final Button anderefavorieten = (Button) findViewById(R.id.otherfavoritesbutton);

        listViewFavoriet = (ListView) findViewById(R.id.favorietlijst);
        favorietList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        profiel.setOnClickListener(this);
        loguit.setOnClickListener(this);
        anderefavorieten.setOnClickListener(this);
        //getFavorites();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favorietList.clear();

                for (DataSnapshot favorietSnapshot : dataSnapshot.getChildren()) {
                    Favoriet favoriet = favorietSnapshot.getValue(Favoriet.class);

                    favorietList.add(favoriet);
                }

                FavorietList adapter = new FavorietList(ProfileActivity.this, favorietList);
                listViewFavoriet.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    public void getFavorites() {
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Favoriet favoriet = dataSnapshot.getValue(Favoriet.class);
                System.out.println(favoriet);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
    });
    }/*
    /*
    public void Adapter() {
        ArrayList<String> listmenu = getFavorites();
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listmenu);
        ListView MenuListView = (ListView) findViewById(R.id.theOrderListView);
        MenuListView.setAdapter(theAdapter);

        });
    }*/


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
            case R.id.otherfavoritesbutton:
                Intent anderefavorietenintent = new Intent(this, OtherFavoritesActivity.class);
                startActivity(anderefavorietenintent);

        }

    }
}
