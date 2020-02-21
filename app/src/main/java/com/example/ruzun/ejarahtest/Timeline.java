package com.example.ruzun.ejarahtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000); //request location every 5 min 300000
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Log.e("Location", "lat: "+locationResult.getLastLocation().getLatitude()+"long: "+locationResult.getLastLocation().getLongitude());
            }
        }, getMainLooper());
    }






}
