package com.aplikacja.covidprogram;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class WHands extends AppCompatActivity {
    public TextView text2, text1;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private static final String TAG = "WHands";
    int LOCATION_REQUEST_CODE = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_hands);
        findViewById(R.id.StartBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            WHands.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );
                } else {
                    startLocationService();
                }
            }
        });
        text1= (TextView) this.findViewById(R.id.textView);
        text2= (TextView) this.findViewById(R.id.textView2);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                WHands.this
        );


        findViewById(R.id.StopBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationService();
            }
        });

        findViewById(R.id.SetBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ActivityCompat.checkSelfPermission(WHands.this,
                       Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
               && ActivityCompat.checkSelfPermission(WHands.this,
                       Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
    getCurrentLocation();
                }else{
                   ActivityCompat.requestPermissions(WHands.this,
                      new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100     );
               }
            }
        });
    }
    public void onLocationChange(){
        getCurrentLocation();
    }
    public void writethis(){
        Toast.makeText(this, "Pamiętaj o masce", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>(){
               @Override
               public void onComplete(@NonNull Task<Location> task){
                Location location =  task.getResult();
                if(location != null){
                    if(text1.getText() != "Szerokość geograficzna"){
                    if(text1.getText() != (String.valueOf(location.getLatitude())) || text2.getText() != (String.valueOf(location.getLongitude())) ){
                        writethis();}}
                        text1.setText(String.valueOf(location.getLatitude()));
                        text2.setText(String.valueOf(location.getLongitude()));

                }
                else{
                    LocationRequest locationRequest = new LocationRequest()
                   .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000)
                   .setFastestInterval(2000)
                    .setNumUpdates(1);

                    LocationCallback locationCallback = new LocationCallback(){
                        @Override
                        public void onLocationResult(LocationResult locationResult){
                            Location location1 = locationResult.getLastLocation();
                            text1.setText(String.valueOf(location1.getLatitude()));
                            text2.setText(String.valueOf(location1.getLongitude()));
                        }
                    };
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                            locationCallback, Looper.myLooper());
                }
               }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this, "Permision denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (LocationService.class.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService() {
        if (!isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constans.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service started", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationService() {
        if (isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constans.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
