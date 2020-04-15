package com.example.ruzun.ejarahtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class homeFragment extends Fragment {

    ImageView createPost;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    final ArrayList<Post> posts = new ArrayList<Post>();
    ListView listView;
    Double lat, lng;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private String userId;

    TextView userNameMenu, userEmailMenu;
    String currentUserEmail;
    UserLocation userCurrentLocation;

    User currentUser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.fragment_home,container,false);

        mFirebaseAuth = FirebaseAuth.getInstance();

        createPost = view.findViewById(R.id.createPost);



        createPost.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), postpage.class));
            }
        });

        databaseReference.child("userLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (mFirebaseAuth.getUid().equals(snapshot.getKey()))
                    {
                        userCurrentLocation = snapshot.getValue(UserLocation.class);

                    }
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.removeAll(posts);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    Location postLocation = new Location("");
                    Location userLocation = new Location("");

                    if(post.getLocation()!=null){

                        postLocation.setLatitude(post.getLocation().get(0));
                        postLocation.setLongitude(post.getLocation().get(1));

                        userLocation.setLatitude(userCurrentLocation.getL().get(0));
                        userLocation.setLongitude(userCurrentLocation.getL().get(1));
                    }

                    if (userLocation.distanceTo(postLocation)<=1000)
                    {
                        posts.add(post);
                        dispaly();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //to get the email of the current user (treating email as it is the user ID)


        callPermissions();



        listView = (ListView) view.findViewById(R.id.post_list);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Post post = posts.get(position);

                Log.i("EEE", "home to post");
                Intent i = new Intent(getActivity(),PostActivity.class);
                i.putExtra("CONTENT", post.getContent());
                i.putExtra("NAME", post.getName());
                i.putExtra("POST_ID", post.getPostID());
                i.putExtra("EMAIL", post.getUsername());
                i.putExtra("CAT", post.getCatogry());
                startActivity(i);
            }
        });

        return view ;


    }
    public void callPermissions(){
        //request location permission
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
        Permissions.check(getActivity(), permissions, "Location is required to use Ejarah", new Permissions.Options().setSettingsDialogMessage("Warning").setRationaleDialogTitle("Info"), new PermissionHandler() {
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

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
        }, getActivity().getMainLooper());


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
        int radius=1;

        DatabaseReference nearbyDatabaseReference= FirebaseDatabase.getInstance().getReference().child("userLocation");
        GeoFire geoFire=new GeoFire(nearbyDatabaseReference);



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

    void dispaly(){
        Collections.reverse(posts);
        PostAdapter<Post> adapter = new PostAdapter<Post>(getContext(),posts);

        listView.setAdapter(adapter);

    }


}

