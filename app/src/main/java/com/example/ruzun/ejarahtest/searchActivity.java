package com.example.ruzun.ejarahtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class searchActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference;
    Location placeLocation=new Location("");;
    Location postLocation=new Location("");;
    TextView noResults;
    RecyclerView searchResults;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    TextView result;
    String featuresString="";
    HashMap<String, Integer> frequencyHash=new HashMap<String, Integer>();
    ArrayList<String> features=new ArrayList<String>();

    String isItWorkingRazan="?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String apiKey = getString(R.string.api_key);

// Initialize Places.
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

// Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);


        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        noResults=findViewById(R.id.search_no_results);
        result=findViewById(R.id.search_result_text);
        searchResults=findViewById(R.id.search_results_rc);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);

        searchResults.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        searchResults.setLayoutManager(mLayoutManager);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                placeLocation.setLatitude(place.getLatLng().latitude);
                placeLocation.setLongitude(place.getLatLng().longitude);

                databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post=snapshot.getValue(Post.class);
                            postLocation.setLatitude(post.getLocation().get(0));
                            postLocation.setLongitude(post.getLocation().get(1));

                            if(placeLocation.distanceTo(postLocation)<=5000){

                                featuresString=post.getFeatures()+" "+featuresString;
                            }
                        }
                        if(featuresString.equals("")){
                            noResults.setVisibility(View.VISIBLE);
                        } else{
                            featuresString=featuresString.trim();

                            features=new ArrayList<String>(Arrays.asList(featuresString.split(" ")));
                            features.trimToSize();

                            if(features.contains("null"))
                                features.remove("null");
                            if(features.contains(" "))
                                features.remove(" ");

// for(String i:features){
// Log.e("features "," feature are:"+i);
// }
//Log.e("features "," feature are:"+features.size());

                            for(String i:features){
//transfer features arraylist contents to frequencyHash
                                int frequency=Collections.frequency(features,i);
                                frequencyHash.put(i,frequency);
                            }
                            Map <String, Integer> frequencyMap= new LinkedHashMap<>();
                            frequencyMap=sortByValue(frequencyHash);

                            features.removeAll(features);

                            features.addAll(frequencyMap.keySet()); //sorted features to array list
                            if(features.contains("null"))
                                features.remove("null");
                            if(features.contains(""))
                                features.remove("");

                            features.trimToSize();
                            Log.e("feature size","feature: "+features.size());
                            List<String> featuresTen=new ArrayList<>();

                            if(features.size()>10){ //0-9
                                featuresTen.addAll(features.subList(0,10));
                                features.removeAll(features);
                                features.addAll(featuresTen);
                            }
                            mAdapter=new SearchAdapter(features);
                            searchResults.setAdapter(mAdapter);
                            searchResults.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onError(Status status) {
                noResults.setText("An error occurred, please try again");
                noResults.setVisibility(View.VISIBLE);
                Log.e("error","An error occurred: " + status);
            }
        });


    }
    private static <K, V> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Object>() {
            @SuppressWarnings("unchecked")
            public int compare(Object o2, Object o1) {
                return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue()).compareTo(((Map.Entry<K, V>) (o2)).getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Iterator<Map.Entry<K, V>> it = list.iterator(); it.hasNext();) {
            Map.Entry<K, V> entry = (Map.Entry<K, V>) it.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

}