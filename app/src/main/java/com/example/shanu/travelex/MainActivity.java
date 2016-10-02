package com.example.shanu.travelex;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements  ConnectionCallbacks,OnConnectionFailedListener, LocationListener {

    protected static final String TAG = "main-activity";
    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mLocation;
    protected TextView mLatitude;
    protected TextView mLongitude;
    Boolean mRequestingLocationUpdates;
    protected boolean mAddressRequested;
    protected String mAddressOutput;
    private AddressResultReceiver mResultReceiver;
    protected TextView mLocationAddressTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Handle click event on both title and image click
        TextView textView1 = (TextView) findViewById(R.id.name1) ;
        ImageView imageView1 = (ImageView) findViewById(R.id.description1);
        textView1.setOnClickListener(clickListener1);
        imageView1.setOnClickListener(clickListener1);
        TextView textView2 = (TextView) findViewById(R.id.name2) ;
        ImageView imageView2 = (ImageView) findViewById(R.id.description2);
        textView2.setOnClickListener(clickListener2);
        imageView2.setOnClickListener(clickListener2);
        //Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        TextView textView3 = (TextView) findViewById(R.id.name3) ;
        ImageView imageView3 = (ImageView) findViewById(R.id.description3);
        textView3.setOnClickListener(clickListener3);
        imageView3.setOnClickListener(clickListener3);

        //mLatitude =(TextView) findViewById(R.id.latitude_value);
       // mLongitude=(TextView) findViewById(R.id.longitude_value);

        mRequestingLocationUpdates=false;
        mResultReceiver = new AddressResultReceiver(new Handler());

        mLocationAddressTextView = (TextView) findViewById(R.id.location_address_view);
        // mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //  mFetchAddressButton = (Button) findViewById(R.id.fetch_address_button);
        mAddressRequested = false;
        mAddressOutput = "";
        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();


    }

    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(view.getContext(), Expedia.class);
            myIntent.putExtra("loction", mAddressOutput);
            startActivity(myIntent);
        }
    };
    View.OnClickListener clickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(view.getContext(), Zomato.class);
            myIntent.putExtra("loction", mAddressOutput);
            startActivity(myIntent);
        }
    };
    View.OnClickListener clickListener3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(view.getContext(), CameraActivity.class);
            startActivity(myIntent);
        }
    };

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Check savedInstanceState to see if the address was previously requested.
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            // Check savedInstanceState to see if the location address string was previously found
            // and stored in the Bundle. If it was found, display the address string in the UI.
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                updateUI();
            }
        }
    }
    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient =new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // createLocationRequest();
    }

    @Override
    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        mLocationRequest =LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation=location;
        if (mLocation != null) {
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                return;
            }
            startIntentService();
        }
        // mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }

    protected void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i("main","connection failed");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Main", "failed");
    }
    private void updateUI() {
        mLocationAddressTextView.setText(mAddressOutput);
       // mLatitude.setText(String.valueOf(mLocation.getLatitude()));
        //mLongitude.setText(String.valueOf(mLocation.getLongitude()));

    }


    protected void showToast(String text) {
       //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save whether the address has been requested.
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);

        // Save the address string.
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            updateUI();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                //showToast(getString(R.string.address_found));
            }

        }
    }
}
