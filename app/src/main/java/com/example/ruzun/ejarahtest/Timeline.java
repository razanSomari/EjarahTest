package com.example.ruzun.ejarahtest;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
/* import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
*/

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Timeline extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
   // LocationRequest mLocationRequest;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        requestPermission();

        final ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(new Post("HELLOOOOO","anyone has a calcluse book?",20));
        posts.add(new Post("Marrihan","I need a phone charger",88));
        posts.add(new Post("jawaher","anyone interrested in creating apps?",23));
        posts.add(new Post("amerah","welkfjlekrqkrmqkmwe",20));
        posts.add(new Post("Mohammed","esgfafeqr qr r ",20));
        posts.add(new Post("Sarah","qr qr qr ",20));
        posts.add(new Post("Mona","qrr qrgt",20));
        posts.add(new Post("Razan","anyone has a calcluse book?",20));
        posts.add(new Post("Marrihan","I need a phone charger",88));
        posts.add(new Post("jawaher","anyone interrested in creating apps?",23));
        posts.add(new Post("Mohammed","welkfjlekrqkrmqkmwe",20));



        PostAdapter<Post> adapter = new PostAdapter <Post>(this,  posts);

        ListView listView = (ListView) findViewById(R.id.post_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = posts.get(position);
                Toast.makeText(Timeline.this, "Toast Message", Toast.LENGTH_LONG).show();

            }
        });

        /*
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
         */



        //Location, Reem
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                           Toast.makeText(Timeline.this, "Latitude is"+location.getLatitude()+"Longitude is"+location.getLongitude(), Toast.LENGTH_LONG).show();
                           //double latitude = location.getLatitude();
                            //double longitude = location.getLongitude();
                        }
                    }
                });
}

private void requestPermission(){
    ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
}





}