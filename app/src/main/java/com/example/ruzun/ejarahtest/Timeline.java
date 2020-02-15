package com.example.ruzun.ejarahtest;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class Timeline extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

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

    }
}
