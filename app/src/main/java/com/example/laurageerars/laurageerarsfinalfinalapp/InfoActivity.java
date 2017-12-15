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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<String> listinfo = new ArrayList<String>();
    //public ArrayList<String> objectnumber = new ArrayList<String>();
    //public ListView CollectionListView;

    // Write a message to the database
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();
    FirebaseUser currentUser;
    String favoritetitle;
    String favoriteobjectnr;
    List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final TextView test = (TextView) findViewById(R.id.test);
        final TextView title = (TextView) findViewById(R.id.titel);
        final TextView beschrijving = (TextView) findViewById(R.id.beschrijving);
        final TextView maker = (TextView) findViewById(R.id.maker);
        final ImageView image = (ImageView) findViewById(R.id.itemimage);
        final Button favorietbutton = (Button) findViewById(R.id.favoriet);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);

        profiel.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String url = (String) intent.getStringExtra("InfoActivity");

        favorietbutton.setOnClickListener(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject newObject = (JSONObject) new JSONObject(response);

                            JSONObject artObject = newObject.getJSONObject("artObject");

                            favoritetitle = artObject.getString("title");
                            title.setText(favoritetitle);
                            favoriteobjectnr = artObject.getString("objectNumber");
                            beschrijving.setText(artObject.getString("description"));

                            JSONObject imageObject = artObject.getJSONObject("webImage");
                            addImage(imageObject.getString("url"));
                            maker.setText(artObject.getString("principalOrFirstMaker"));


                        } catch (JSONException e) {
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

    public void addImage(String imageurl) {
        ImageView image = (ImageView) findViewById(R.id.itemimage);
        Picasso.with(this).load(imageurl).fit().into(image);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profielfoto:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.favoriet:
                currentUser = mAuth.getCurrentUser();
                Favoriet favoriet = new Favoriet(favoritetitle, favoriteobjectnr);
                mDatabase.child("favoriet").child(currentUser.getDisplayName()).child(favoriteobjectnr).setValue(favoriet);
                Toast.makeText(InfoActivity.this, "Schilderij toegevoegd aan favorieten!",
                        Toast.LENGTH_SHORT).show();

            }





        }


    }




