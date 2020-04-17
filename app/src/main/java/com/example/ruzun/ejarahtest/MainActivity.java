package com.example.ruzun.ejarahtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private String userId;
    FirebaseAuth mFirebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Double lat, lng;

    TextView userNameMenu, userEmailMenu;
    String currentUserEmail;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFirebaseListener();


        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        PostActivity.isPoster=false;


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        mFirebaseAuth = FirebaseAuth.getInstance();
        userEmailMenu = headerView.findViewById(R.id.menu_email);
        userNameMenu =  headerView.findViewById(R.id.menu_user_name);
        currentUserEmail = mFirebaseAuth.getCurrentUser().getEmail();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callPermissions();

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user.getEmail()!=null&&currentUserEmail!=null)
                    {
                        if(user.getEmail().toLowerCase().equals(currentUserEmail.toLowerCase()))
                        {
                            currentUser = user;
                            setUserNameAndEmailInMenuHeader();
                        }
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new homeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }





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
        databaseReference= FirebaseDatabase.getInstance().getReference("userLocation");
        GeoFire geoFire=new GeoFire(databaseReference);
        geoFire.setLocation(userId, new GeoLocation(lat, lng));
        getNearbyUsers(lat,lng);

    }

    public void getNearbyUsers(Double lat, Double lng){
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        int radius=30;

        DatabaseReference nearbyDatabaseReference= FirebaseDatabase.getInstance().getReference().child("userLocation");
        GeoFire geoFire=new GeoFire(nearbyDatabaseReference);
        Log.i("TAGG", "INSIDE");

        GeoQuery geoQuery=geoFire.queryAtLocation(new GeoLocation(lat, lng),radius); //users in a 30 kilometers radius
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                //Key Entered: The location of a key now matches the query criteria.
                //key is userID nearby, location is users location
                //
                if(!key.equals(userId)){

                    //get all nearby users except me
                    //array list of posts attached to key
                }

            }

            @Override
            public void onKeyExited(String key) {
                //Key Exited: The location of a key no longer matches the query criteria.
                //the user has existed the radius
                //remove posts from array list
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                //Key Moved: The location of a key changed but the location still matches the query criteria.
                //user is moving but is still within radius
                //don't think we'll need this
            }

            @Override
            public void onGeoQueryReady() {
                //Query Ready: All current data has been loaded from the server and all initial events have been fired.
                //don't think we'll need this
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                //Query Error: There was an error while performing this query, e.g. a violation of security rules.
                //don't think we'll need this
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new homeFragment()).commit();
                break;
               /* homeFragment homeFragment = new homeFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container , homeFragment).commit();
                break;*/
            case R.id.nav_level:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new levelFragment()).commit();
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new searchFragment()).commit();
                break;
            case R.id.nav_language:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new languageFragment()).commit();
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setUserNameAndEmailInMenuHeader(){
        if(currentUser!=null){
            userEmailMenu.setText(currentUser.getEmail());
            userNameMenu.setText(currentUser.getName());
        }

    }

    //----------------------------------------------------------------------
    public void onStart(){
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    //----------------------------------------------------------------------
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthStateListener!=null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
    }

    //----------------------------------------------------------------------
    private void setUpFirebaseListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null ){
                    Intent intent = new Intent(MainActivity.this, LogIn.class);
                    startActivity(intent);
                }


            }
        };
    }
}


