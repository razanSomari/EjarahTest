package com.example.ruzun.ejarahtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class levelFragment extends Fragment {



    static ArrayList<Level> levels = new ArrayList<Level>();

    ListView listView;
    String mTitle[] = {"Health", "Education", "Electronics", "Music", "Advice/question","Tv show","Writing/stories","Sport","Profession" , " Travel" };
    String mDescription[] = {"Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description"};
    String points [] = new String[mTitle.length];
    int images[] = {R.drawable.doctor, R.drawable.abc, R.drawable.responsive, R.drawable.guitar, R.drawable.question , R.drawable.music, R.drawable.poetry
            , R.drawable.soccer , R.drawable.light, R.drawable.cityscape};
    ProgressBar bar ;

    User currentUser;
    String email;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level,container,false);


        mFirebaseAuth = FirebaseAuth.getInstance();
        email = mFirebaseAuth.getCurrentUser().getEmail();


        listView = view.findViewById(R.id.categories_list);
        // now create an adapter class

        categoriesAdapter adapter = new categoriesAdapter(getContext(), mTitle, images , bar);
        adapter.setCurrentUserPoints();
        listView.setAdapter(adapter);





        return view ;
    }

    void setCurrentUserPoints(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = mFirebaseAuth.getCurrentUser().getEmail();

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
                            categoriesAdapter.userPoints.put(level.getCategory(), level.getPoints());
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
    }
}



