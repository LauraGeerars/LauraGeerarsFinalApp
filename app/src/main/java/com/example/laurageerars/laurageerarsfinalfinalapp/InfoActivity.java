package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


// Activity for giving more information about a collection item.
// It shows the title, an image, the maker and a description from a collection item.
// This item can be added to favorites.

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();
    FirebaseUser currentUser;
    String favoritetitle;
    String favoriteobjectnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final TextView test = (TextView) findViewById(R.id.test);
        final TextView title = (TextView) findViewById(R.id.titel);
        final TextView beschrijving = (TextView) findViewById(R.id.beschrijving);
        final TextView maker = (TextView) findViewById(R.id.maker);
        final Button favorietbutton = (Button) findViewById(R.id.favoriet);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);

        profiel.setOnClickListener(this);
        favorietbutton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Get the url made in HomepageActivity, an unique url for a collection item.
        Intent intent = getIntent();
        String url = (String) intent.getStringExtra("InfoActivity");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Try to get the information of a collection item out of the API,
                        // and set this to the textview/imageview created in the xml.
                        try {
                            JSONObject newObject = (JSONObject) new JSONObject(response);
                            JSONObject artObject = newObject.getJSONObject("artObject");
                            JSONObject imageObject = artObject.getJSONObject("webImage");

                            // Set strings to views.
                            favoritetitle = artObject.getString("title");
                            title.setText(favoritetitle);
                            beschrijving.setText(artObject.getString("description"));
                            maker.setText(artObject.getString("principalOrFirstMaker"));

                            // Get url for showing an image of a collection item.
                            addImage(imageObject.getString("url"));

                            // Get unique objectNumber of a collection item for in Firebase.
                            favoriteobjectnr = artObject.getString("objectNumber");
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test.setText("That didn't work!");
                }
            });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // Function for adding image into the imageview, by using Picasso.
    public void addImage(String imageurl) {
        ImageView image = (ImageView) findViewById(R.id.itemimage);
        Picasso.with(this).load(imageurl).fit().into(image);
    }

    // On Click function for going to the profile of an user and adding titles
    // with their objectNumber as favorites to Firebase.
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profielfoto:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.favoriet:
                // Get the current user.
                currentUser = mAuth.getCurrentUser();
                Favoriet favoriet = new Favoriet(favoritetitle, favoriteobjectnr);
                // Adding new favorite (title and objectNumber) for the current username to Firebase.
                mDatabase.child("favoriet").child(currentUser.getDisplayName()).child(favoriteobjectnr).setValue(favoriet);
                Toast.makeText(InfoActivity.this, "Schilderij toegevoegd aan favorieten!",
                        Toast.LENGTH_SHORT).show();
            }
    }
}




