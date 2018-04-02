package com.example.android.saveme.core.main;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.saveme.R;
import com.example.android.saveme.common.data.pojo.Result;
import com.example.android.saveme.core.main.recycler_view.HospitalsAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainActivityViewModel mainActivityViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private List<Result> hospitals = new ArrayList<>();
    private HospitalsAdapter hospitalsAdapter;

    @BindView(R.id.rv_hospitals)
    public RecyclerView hospitalsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        hospitalsAdapter = new HospitalsAdapter(hospitals, this);
        hospitalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalsRecyclerView.setAdapter(hospitalsAdapter);

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
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        fetchNearbyHospitals(location);
                    }
                });
    }

    private void fetchNearbyHospitals(Location location) {
        mainActivityViewModel.getNearbyHospitals(location,
                getResources().getString(R.string.api_key))
                .observe(this, hospitalsResponse -> {
                    hospitals.clear();
                    hospitals.addAll(hospitalsResponse.results);
                    hospitalsAdapter.notifyDataSetChanged();
                });
    }
}

