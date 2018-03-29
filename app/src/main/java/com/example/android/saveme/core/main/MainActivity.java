package com.example.android.saveme.core.main;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.android.saveme.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainActivityViewModel mainActivityViewModel;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getPermissions();
    }

    private void getPermissions() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
        } else {
            fetchUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchUserLocation();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this,
                            getResources().getString(R.string.location_permission_required)
                            , Toast.LENGTH_LONG).show();

                    //TODO: Close the app
                }
            } break;
            default:
                // no implementation
        }
    }

    private void fetchUserLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            fetchNearbyHospitals(location);
                        }
                    }
                });
    }

    private void fetchNearbyHospitals(Location location) {
        Log.v(TAG, location.getAltitude() + "");
    }
}

