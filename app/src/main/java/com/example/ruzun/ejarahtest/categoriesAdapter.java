package com.example.ruzun.ejarahtest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class categoriesAdapter extends ArrayAdapter<String> {

     int count = 0 ;
     int max =100;

    Context context;
    String rTitle[];
    int rImgs[];

    ProgressBar progressBar;

    User currentUser;
    String email;
    public static Map<String, Integer> userPoints = new HashMap<>();
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();




    categoriesAdapter (Context c, String[] title, int[] imgs, ProgressBar bar  ) {
        super(c, R.layout.categories_level, R.id.textView1, title);
        setCurrentUserPoints();

        this.context = c;
        this.rTitle = title;
        this.rImgs = imgs;
        this.progressBar = bar ;



    }

    void setCurrentUserPoints(){

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = mFirebaseAuth.getCurrentUser().getEmail();
        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.categories_level, parent, false);
        }

        String s = getItem(position);
        ImageView images = listItemView.findViewById(R.id.image);
        TextView myTitle = listItemView.findViewById(R.id.textView1);
        final TextView myLevel = listItemView.findViewById(R.id.level);

        final ProgressBar progressBar1 = listItemView.findViewById(R.id.bar);
        progressBar1.setMax(max);

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user.getEmail()!=null&&email!=null)
                        if (user.getEmail().toLowerCase().equals(email.toLowerCase()))
                        {
                            currentUser = user;
                        }
                }
                databaseReference.child("User").child(currentUser.getUserID()).child("level").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                            Level level = snapshot.getValue(Level.class);
                            userPoints.put(level.getCategory(), level.getPoints());
                        }

                        if(userPoints.get(rTitle[position].toLowerCase())!=null){
                            int points = userPoints.get(rTitle[position].toLowerCase());
                            int level = points/max;
                            int progress = points%max;
                            progressBar1.setProgress(progress);
                            myLevel.setText(level+"");

                        }
                        else{
                            progressBar1.setProgress(0);
                            myLevel.setText(0+"");
                        }

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        // now set our resources on views
        images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle[position]);





        return listItemView;
    }


}
