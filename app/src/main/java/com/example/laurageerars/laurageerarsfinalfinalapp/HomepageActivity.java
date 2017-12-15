package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;


public class HomepageActivity extends AppCompatActivity implements View.OnClickListener{
    public ArrayList<String> listcollection = new ArrayList<String>();
    public ListView CollectionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final ListView CollectionListView = (ListView) findViewById(R.id.ListViewCollection);
        final TextView test = (TextView) findViewById(R.id.test);
        final ImageView profiel = (ImageView) findViewById(R.id.profielfoto);

        profiel.setOnClickListener(this);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.rijksmuseum.nl/api/nl/collection?key=e53vvaf0&format=json&type=schilderij";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject newObject = (JSONObject) new JSONObject(response);
                            ArrayList<JSONObject> listcollection = new ArrayList<JSONObject>();
                            JSONArray collectionArray = newObject.getJSONArray("artObjects");
                            for (int i = 0; i < collectionArray.length(); i++) {
                                JSONObject jsonObject = collectionArray.getJSONObject(i);
                                addItem(jsonObject.getString("title"));
                                savetoSharedPrefs(jsonObject.getString("title"), jsonObject.getString("objectNumber"));

                            }
                            Adapter();
                        } catch (JSONException e) {
                            //mTextView.setText(e.toString());
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

        //function for adding item to list categories
        public void addItem(String Item){

            listcollection.add(Item);

        }

    public void savetoSharedPrefs(String title, String objectnumber) {
        SharedPreferences prefs = getSharedPreferences("title", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(title, objectnumber);
        prefsEditor.commit();

    }


    public void Adapter() {
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listcollection);
        ListView CollectionListView = (ListView) findViewById(R.id.ListViewCollection);
        CollectionListView.setAdapter(theAdapter);
        CollectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Title and objectnumber as shared preferences, so the url for extra info can be made and used in InfoActivity
                SharedPreferences yourOrderPrefs = getApplicationContext().getSharedPreferences("title", MODE_PRIVATE);
                String objectnumber = yourOrderPrefs.getString(String.valueOf(adapterView.getItemAtPosition(i)), null);
                if (objectnumber != null) {
                    String url = "https://www.rijksmuseum.nl/api/nl/collection/" + objectnumber +
                            "?key=e53vvaf0&format=json";
                    Log.d("url", url);
                    gotoInfoActivity(url);

                }


            }
        });
    }

    // Function for going to next activity (InfoActivity)
    public void gotoInfoActivity(String InfoActivity){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("InfoActivity", InfoActivity);
        startActivity(intent);
    }

    //On click function for buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profielfoto:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
        }

    }


}


