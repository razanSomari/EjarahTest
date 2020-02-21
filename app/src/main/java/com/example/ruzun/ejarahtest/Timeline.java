package com.example.ruzun.ejarahtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import java.sql.Time;
import java.util.ArrayList;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Timeline extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    Location userLocation;
    private String userId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        callPermissions();

        final ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(new Post("HELLOOOOO", "anyone has a calcluse book?", 20));
        posts.add(new Post("Marrihan", "I need a phone charger", 88));
        posts.add(new Post("jawaher", "anyone interrested in creating apps?", 23));
        posts.add(new Post("amerah", "welkfjlekrqkrmqkmwe", 20));
        posts.add(new Post("Mohammed", "esgfafeqr qr r ", 20));
        posts.add(new Post("Sarah", "qr qr qr ", 20));
        posts.add(new Post("Mona", "qrr qrgt", 20));
        posts.add(new Post("Razan", "anyone has a calcluse book?", 20));
        posts.add(new Post("Marrihan", "I need a phone charger", 88));
        posts.add(new Post("jawaher", "anyone interrested in creating apps?", 23));
        posts.add(new Post("Mohammed", "welkfjlekrqkrmqkmwe", 20));


        PostAdapter<Post> adapter = new PostAdapter<Post>(this, posts);

        ListView listView = (ListView) findViewById(R.id.post_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = posts.get(position);

                Intent i = new Intent(Timeline.this, PostActivity.class);
                i.putExtra("CONTENT", post.getContent());
                i.putExtra("NAME", post.getUsername());
                startActivity(i);
            }
        });


    }
    public void callPermissions(){
        //request location permission
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
        Permissions.check(this, permissions, "Location is required to use Ejarah", new Permissions.Options().setSettingsDialogMessage("Warning").setRationaleDialogTitle("Info"), new PermissionHandler() {
            @Override
            public void onGranted() {
                requestLocationUpdates();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
            callPermissions();
            }
        });
    }

    public void requestLocationUpdates(){
        //update location periodically
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(2000); //request location every 5 min 300000
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //drains battery

        fusedLocationClient.requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                lat=locationResult.getLastLocation().getLatitude();
                lng=locationResult.getLastLocation().getLongitude();
                setUserLocation(lat,lng);
            }
        }, getMainLooper());


    }

    public void setUserLocation(Double lat, Double lng){
        //inserting into database!
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("userLocation");
        GeoFire geoFire=new GeoFire(databaseReference);
        geoFire.setLocation(userId, new GeoLocation(lat, lng));
        getNearbyUsers(lat,lng);
    }

    public void getNearbyUsers(Double lat, Double lng){
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference nearbyDatabaseReference=FirebaseDatabase.getInstance().getReference().child("userLocation");
        GeoFire geoFire=new GeoFire(nearbyDatabaseReference);

        GeoQuery geoQuery=geoFire.queryAtLocation(new GeoLocation(lat, lng),30); //users in a 30 kilometers
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                //Key Entered: The location of a key now matches the query criteria.
                //key is userID nearby, location is users location
                if(!key.equals(userId)){
                    //get all nearby users except me
                    Log.e("nearby user","User "+ key+"is at "+location);

                }

            }

            @Override
            public void onKeyExited(String key) {
            //Key Exited: The location of a key no longer matches the query criteria.
                //the user has existed the raduis
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
            //Key Moved: The location of a key changed but the location still matches the query criteria.
                //user is moving but is still within raduis
            }

            @Override
            public void onGeoQueryReady() {
            //Query Ready: All current data has been loaded from the server and all initial events have been fired.

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
            //Query Error: There was an error while performing this query, e.g. a violation of security rules.
            }
        });
    }



}
