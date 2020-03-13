package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;

public class homeFragment extends Fragment {

    Button createPost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.fragment_home,container,false);

       createPost = view.findViewById(R.id.createPost);

        createPost.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), postpage.class));
            }
        });

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


        PostAdapter<Post> adapter = new PostAdapter<Post>(getContext(),posts);
        ListView listView;
        listView = (ListView) view.findViewById(R.id.post_list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Post post = posts.get(position);

                Intent i = new Intent(getActivity(),PostActivity.class);
                i.putExtra("CONTENT", post.getContent());
                i.putExtra("NAME", post.getUsername());
                startActivity(i);
            }
        });

        return view ;
    }
}

