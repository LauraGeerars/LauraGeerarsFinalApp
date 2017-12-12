package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    public ArrayList<String> listinfo = new ArrayList<String>();
    public ArrayList<String> objectnumber = new ArrayList<String>();
    public ListView CollectionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final ListView InfoListView = (ListView) findViewById(R.id.ListViewCollection);
        final TextView test = (TextView) findViewById(R.id.test);
        final TextView title = (TextView) findViewById(R.id.titel);
        final TextView beschrijving = (TextView) findViewById(R.id.beschrijving);
        final TextView maker = (TextView) findViewById(R.id.maker);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String url = (String) intent.getStringExtra("InfoActivity");



        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //test.setText("Response is: "+ response.substring(0,500));
                        try {
                            JSONObject newObject = (JSONObject) new JSONObject(response);

                            JSONObject artObject = newObject.getJSONObject("artObject");

                            title.setText(artObject.getString("title"));
                            beschrijving.setText(artObject.getString("description"));


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
    public void addItem(String Item) {

        listinfo.add(Item);

    }
/*
    public void Adapter() {
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listinfo);
        ListView InfoListView = (ListView) findViewById(R.id.InfoViewCollection);
        InfoListView.setAdapter(theAdapter);
        InfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //gotoInfoActivity(String.valueOf(adapterView.getItemAtPosition(i)));




            }
        });
    }
    */
}

