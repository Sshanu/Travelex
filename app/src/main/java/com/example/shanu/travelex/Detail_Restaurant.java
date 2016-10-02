package com.example.shanu.travelex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail_Restaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__restaurant);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String location = intent.getStringExtra("location");
        String cuisines = intent.getStringExtra("cuisines");
        String url = intent.getStringExtra("url");
        String thumbnail = intent.getStringExtra("thumb");
        TextView title_view = (TextView) findViewById(R.id.rest_title);
        //TextView url_view = (TextView) findViewById(R.id.rest_url);
        TextView location_view = (TextView) findViewById(R.id.rest_location);
        TextView cuisines_view = (TextView) findViewById(R.id.rest_cuisines);
        title_view.setText(title);
        //url_view.setText(url);
        location_view.setText(location);
        cuisines_view.setText(cuisines);
        ImageView imageView= (ImageView) findViewById(R.id.description);
        Picasso.with(this).load(thumbnail)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageView);


    }
}
