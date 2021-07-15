package com.example.cardamagedetector;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


@SuppressWarnings("ALL")
public class LocationActivity extends AppCompatActivity {
  private static final int REQUEST_CODE_LOCATION_PERMISSION=1;
  private TextView latlong,address;
  private ProgressBar progressbar;
  private ResultReceiver resultReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location2);
        resultReceiver=new AddressResultReceiver(new Handler());
        latlong=findViewById(R.id.latlong);
        address=findViewById(R.id.Address);
        progressbar = findViewById(R.id.Progressbar);
        ImageView shortcut;
        shortcut=findViewById(R.id.shortcut);
        Button nearbyloc=findViewById(R.id.nearbyloc);
        nearbyloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(LocationActivity.this,Mecshop_Details.class);
                startActivity(intsignup);
            }
        });
        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(LocationActivity.this,HomeActivity.class);
                startActivity(intsignup);
            }
        });
        findViewById(R.id.locbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                }
                else
                {
                    getCurrentLocation();
                }
            }
        });

    }
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode ==REQUEST_CODE_LOCATION_PERMISSION && grantResults.length >0)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation();
            }
            else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void getCurrentLocation(){
        progressbar.setVisibility(View.VISIBLE);
        LocationRequest locationRequest=new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(LocationActivity.this).requestLocationUpdates(locationRequest,
                new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                LocationServices.getFusedLocationProviderClient(LocationActivity.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude =
                            locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude =
                            locationResult.getLocations().get(latestLocationIndex).getLongitude();

                    latlong.setText(String.format("Latitude:%s Longitude:%s", latitude, longitude));
                Location location =new Location("providerNA");
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                fetchAddressfromlatlong(location);

                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            }, Looper.getMainLooper());

    }



    private void fetchAddressfromlatlong(Location location)
    {

        Intent intent=new Intent(this,FetchAddress.class);
        intent.putExtra(Constants.RECEIVER,resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }
    private class AddressResultReceiver extends ResultReceiver{
         AddressResultReceiver(Handler handler)
        {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode,Bundle resultData)
        {
            super.onReceiveResult(resultCode,resultData);
            if(resultCode==Constants.SUCCESS_RESULT){
                address.setText(resultData.getString(Constants.RESULT_DATA_KEY));
            }

            else{
                Toast.makeText(LocationActivity.this,resultData.getString(Constants.RESULT_DATA_KEY),Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }

}
