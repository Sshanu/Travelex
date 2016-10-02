package com.example.shanu.travelex;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Zomato extends AppCompatActivity {

    private static final String TAG = "RecyclerViewExample";
    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter1 adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zomato);
        Intent intent= getIntent();
        String mylocation = intent.getStringExtra("location");
        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        // Downloading data from below url
        final String url = "https://developers.zomato.com/api/v2.1/search?entity_type="+mylocation+"&q=food";
        new AsyncHttpTask().execute(url);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;

            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("user_key", "d36b1b110869f56c46103624d60f3cc7");

                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }

                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Download complete. Let us update UI
            progressBar.setVisibility(View.GONE);
            if (result == 1) {
                adapter = new MyRecyclerAdapter1(Zomato.this, feedsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(Zomato.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("restaurants");

            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {

                JSONObject post = posts.optJSONObject(i);
                JSONObject rest = post.optJSONObject("restaurant");
                JSONObject location = rest.optJSONObject("location");
                FeedItem item = new FeedItem();
                item.setTitle(rest.optString("name"));
                item.setLocation(location.optString("address"));
                item.setCuisines(rest.optString("cuisines"));
                item.seturl(rest.optString("url"));
                item.setThumbnail(rest.optString("thumb"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
