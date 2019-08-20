package com.example.rkg09.domesticservicesfreelancing;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class EmployeeMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location mLastLocation;
    LocationRequest mLocationRequest;


    private FusedLocationProviderClient mFusedLocationClient;


    private Button mLogout, mSettings, mRideStatus, mHistory;
    //destination;
    private LatLng  pickupLatLng; // destinationLatLng;
    //private float rideDistance;

    private Boolean isLoggingOut = false;
    private Boolean flag_available = false;
    private Boolean flag_working = false;
    private String customerID;
    //private SupportMapFragment mapFragment;

    //private LinearLayout mCustomerInfo;

    //private ImageView mCustomerProfileImage;

    //private TextView mCustomerName, mCustomerPhone, mCustomerDestination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_maps);

        customerID = getIntent().getStringExtra("Id");
        //Toast.makeText(getApplicationContext(),"Check 1",Toast.LENGTH_SHORT).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //polylines = new ArrayList<>();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

        /*mLogout = (Button) findViewById(R.id.log_out);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoggingOut = true;
                disconnectDriver();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DriverMapsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });*/
        getAssignedCustomer();
    }

    private void getAssignedCustomer() {
        //Toast.makeText(getApplicationContext(),"Check 2",Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"Id "+customerID,Toast.LENGTH_SHORT).show();

        String employeeId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference assignedCustomerRef = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(employeeId).child(customerID);
        assignedCustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //customerID = dataSnapshot.getValue().toString();
                    //Toast.makeText(getApplicationContext(),"Id "+customerId,Toast.LENGTH_SHORT).show();
                    getAssignedCustomerPickupLocation();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    Marker pickupMarker;
    private DatabaseReference assignedCustomerPickupLocationRef;
    private ValueEventListener assignedCustomerPickupLocationRefListener;

    private void getAssignedCustomerPickupLocation() {
        DatabaseReference assignedCustomerPickupLocation = FirebaseDatabase.getInstance().getReference().child("customerRequest").child(customerID).child("l");
        assignedCustomerPickupLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    if(pickupMarker != null){
                        pickupMarker.remove();
                    }

                    List<Object> map = (List<Object>) dataSnapshot.getValue();
                    double locationLat = 0;
                    double locationLon = 0;
                    if (map.get(0) != null) {
                        locationLat = Double.parseDouble(map.get(0).toString());
                    }
                    if (map.get(1) != null) {
                        locationLon = Double.parseDouble(map.get(1).toString());
                    }
                    LatLng driverLatLng = new LatLng(locationLat, locationLon);
                    //Toast.makeText(getApplicationContext(), "" + driverLatLng, Toast.LENGTH_LONG).show();

                    pickupMarker = mMap.addMarker(new MarkerOptions().position(driverLatLng).title("Customer Location"));
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Toast.makeText(getApplicationContext(), "Check 4", Toast.LENGTH_SHORT).show();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //Toast.makeText(getApplicationContext(), "Check 5", Toast.LENGTH_SHORT).show();

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(getApplicationContext(), "Check 6", Toast.LENGTH_SHORT).show();
                connectDriver();
            } else {
                //Toast.makeText(getApplicationContext(), "Check 7", Toast.LENGTH_SHORT).show();
                checkLocationPermission();
            }
        } else {
            connectDriver();
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            //Toast.makeText(getApplicationContext(), "Check 8 " + locationResult, Toast.LENGTH_SHORT).show();

            List<Location> locationList = locationResult.getLocations();
            Location location = locationList.get(locationList.size() - 1);
            //Toast.makeText(getApplicationContext(), "Check 9", Toast.LENGTH_SHORT).show();
            if (getApplicationContext() != null) {

                //Toast.makeText(getApplicationContext(), "Check 10", Toast.LENGTH_SHORT).show();

                //if (!customerId.equals("") && mLastLocation != null && location != null) {
                //rideDistance += mLastLocation.distanceTo(location) / 1000;
                //}
                mLastLocation = location;


                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

                //checkdriver(customerId,location);

                //Toast.makeText(getApplicationContext(), "Check 11", Toast.LENGTH_SHORT).show();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference refWorking = FirebaseDatabase.getInstance().getReference("employeesWorking");
                GeoFire geoFireWorking = new GeoFire(refWorking);

                refWorking.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            flag_working = true;
                        } else {
                            flag_working = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //Toast.makeText(getApplicationContext(), "Check 12", Toast.LENGTH_SHORT).show();

                switch (customerID) {

                    default:

                        //Toast.makeText(getApplicationContext(), "Check 14", Toast.LENGTH_SHORT).show();
                        geoFireWorking.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {

                            }
                        });
                        break;
                }
            }
        }
    };

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Check Permission", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(EmployeeMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(EmployeeMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        //ActivityCompat.requestPermissions(DriverMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Toast.makeText(getApplicationContext(), "Request", Toast.LENGTH_LONG).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mMap.setMyLocationEnabled(true);
                        //mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

                        //Toast.makeText(getApplicationContext(), "In if", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "In else", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please provide the permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void connectDriver() {
        checkLocationPermission();
        //Toast.makeText(getApplicationContext(), "Check 18", Toast.LENGTH_LONG).show();
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        mMap.setMyLocationEnabled(true);
    }

    private void disconnectDriver() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("employeesWorking").child(userId);
        ref.setValue(null);

        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(userId, new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!isLoggingOut) {
            //disconnectDriver();
        }
    }

}
