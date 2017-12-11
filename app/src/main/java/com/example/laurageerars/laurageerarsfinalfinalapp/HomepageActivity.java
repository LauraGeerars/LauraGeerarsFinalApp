package com.example.laurageerars.laurageerarsfinalfinalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class HomepageActivity extends AppCompatActivity {
    public ArrayList<String> listcollection = new ArrayList<String>();
    public ListView CollectionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final ListView CollectionListView = (ListView) findViewById(R.id.ListViewCollection);
        final TextView test = (TextView) findViewById(R.id.test);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.rijksmuseum.nl/api/nl/collection?key=e53vvaf0&format=json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //test.setText("Response is: "+ response.substring(0,500));
                        try {
                            JSONObject newObject = (JSONObject) new JSONObject(response);
                            ArrayList<JSONObject> listcollection = new ArrayList<JSONObject>();
                            JSONArray collectionArray = newObject.getJSONArray("artObjects");
                            for (int i = 0; i < collectionArray.length(); i++) {
                                addItem(collectionArray.getJSONObject(i).getString("title"));

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




        /*
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the characters of the response string.
                        //mTextView.setText("Response is: " + response);

                        try {

                            JSONObject newObject = (JSONObject) new JSONTokener(response).nextValue();
                            ArrayList<JSONObject> listcollection = new ArrayList<JSONObject>();
                            JSONArray collectionArray = newObject.getJSONArray("title");
                            for (int i = 0; i < collectionArray.length(); i++) {
                                listcollection.add(collectionArray.getJSONObject(i));
                                test.setText(collectionArray.getJSONObject(i).getString("title"));
                                //addItem(categoryArray.get(i).toString());
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
                //TextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
*/
        //function for adding item to list categories
        public void addItem(String Item){

            listcollection.add(Item);

        }

    public void Adapter() {
        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listcollection);
        ListView CollectionListView = (ListView) findViewById(R.id.ListViewCollection);
        CollectionListView.setAdapter(theAdapter);
        CollectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //gotoCategoryMenu(String.valueOf(adapterView.getItemAtPosition(i)));

            }
        });
    }


}


