package com.example.ruzun.ejarahtest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class categoriesAdapter extends ArrayAdapter<String> {

     int count = 0 ;
     FirebaseAuth mFirebaseAuth;
     FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
     DatabaseReference databaseReference = firebaseDatabase.getReference();
    Context context;
    String rTitle[];
    int rImgs[];
    ProgressBar progressBar ;


    String email;
    User currentUser;

    static ArrayList<Level> levels = new ArrayList<Level>();

    categoriesAdapter (Context c, String[] title, int[] imgs, ProgressBar bar  ) {
        super(c, R.layout.categories_level, R.id.textView1, title);

        this.context = c;
        this.rTitle = title;
        this.rImgs = imgs;
        this.progressBar = bar ;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.categories_level, parent, false);
        }

        ImageView images = listItemView.findViewById(R.id.image);
        TextView myTitle = listItemView.findViewById(R.id.textView1);
        final ProgressBar progressBar1 = listItemView.findViewById(R.id.bar);




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
                            levels.add(level);
                            count =  level.getPoints();
                            progressBar1.setProgress(count);












/*

                            progressBar1.setMax(100);
                            progressBar1.setProgress(level.getPoints());
*/



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
