package com.example.ruzun.ejarahtest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.sql.Time;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Timeline extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        requestPermission();

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


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Toast.makeText(Timeline.this, "Latitude is"+location.getLatitude() +" Longitute is "+location.getLongitude(), Toast.LENGTH_LONG).show();

                        }
                        else
                            Toast.makeText(Timeline.this, "Empty", Toast.LENGTH_LONG).show();

                    }
                });

        /*
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
         */

                        }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

}

