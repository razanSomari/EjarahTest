package com.example.ruzun.ejarahtest;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class homeFragment extends Fragment {

    ImageView createPost;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    final ArrayList<Post> posts = new ArrayList<Post>();
    ListView listView;

    TextView userNameMenu, userEmailMenu;
    String currentUserEmail;

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

        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.removeAll(posts);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    posts.add(post);
                    dispaly();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //to get the email of the current user (treating email as it is the user ID)






        listView = (ListView) view.findViewById(R.id.post_list);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Post post = posts.get(position);

                Intent i = new Intent(getActivity(),PostActivity.class);
                i.putExtra("CONTENT", post.getContent());
                i.putExtra("NAME", post.getName());
                i.putExtra("POST_ID", post.getPostID());
                startActivity(i);
            }
        });

        return view ;
    }


    void dispaly(){
        Collections.reverse(posts);
        PostAdapter<Post> adapter = new PostAdapter<Post>(getContext(),posts);

        listView.setAdapter(adapter);

    }


}

