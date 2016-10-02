package com.example.shanu.travelex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail_Hotel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__hotel);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String location = intent.getStringExtra("location");
        String rates = intent.getStringExtra("rates");
        String thumbnail = intent.getStringExtra("thumb");
        String description = intent.getStringExtra("description");
        TextView title_view = (TextView) findViewById(R.id.hot_title);
        TextView description_view = (TextView) findViewById(R.id.hot_description);
        TextView location_view = (TextView) findViewById(R.id.hot_location);
        TextView rates_view = (TextView) findViewById(R.id.hot_rates);
        title_view.setText(title);
        location_view.setText(location);
        description_view.setText(description);
        rates_view.setText(rates);

        ImageView imageView= (ImageView) findViewById(R.id.hot_thumb);
        Picasso.with(this).load("http://images.trvl-media.com"+thumbnail)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageView);

    }
}
