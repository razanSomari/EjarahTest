package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    TextView textViewName;
    TextView textViewContent;
    TextView commentsCount;
    TextView postViewsCount;

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String email;
    PostAdapter<Post> adapter;
    User currentUser;
    ListView listView;

    public static String mostSuitableUser;
    public static int maxPoints=Integer.MIN_VALUE;

    static String name, postID, postEmail, postCategory;

    public static boolean isPoster = false;
    public static boolean isExist = false;

    final ArrayList<Post> replays = new ArrayList<Post>();
    static ArrayList<Level> levels = new ArrayList<Level>();

     ArrayList<User> users = new ArrayList<User>();

    Post currentPost;
    Map<String, Integer> UserTotalPoints = new HashMap<>();
    Map<String, Integer> userCategoryPoints = new HashMap<>();

    boolean isFirstTimeToSetViewCount = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        //database initialization
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = mFirebaseAuth.getCurrentUser().getEmail();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        commentsCount = findViewById(R.id.TextViewPostComments);

        listView = (ListView) findViewById(R.id.replay_list);



        //getting post information from previous activity
        String content, views;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name= "";
                content = "";
                postID="";
                postEmail ="";
                postCategory = "";
                views ="";

            } else {
                content = extras.getString("CONTENT");
                name = extras.getString("NAME");
                postID = extras.getString("POST_ID");
                postEmail = extras.getString("EMAIL");
                postCategory = extras.getString("CAT");
                views =  extras.getString("VIEWS");
            }
        } else {
            content= (String) savedInstanceState.getSerializable("CONTENT");
            name= (String) savedInstanceState.getSerializable("NAME");
            postID = (String) savedInstanceState.getSerializable("POST_ID");
            postEmail = (String) savedInstanceState.getSerializable("EMAIL");
            postCategory = (String) savedInstanceState.getSerializable("CAT");
            views = (String) savedInstanceState.getSerializable("VIEWS");

        }



        if(postEmail!=null){
            if (postEmail.equals(email))
                isPoster=true;
            else
                isPoster=false;
        }



        textViewName = findViewById(R.id.textViewPostUsername);
        textViewContent = findViewById(R.id.TextViewPostContent);
        postViewsCount = findViewById(R.id.TextViewPostViews);

        textViewName.setText(name);
        textViewContent.setText(content);


        databaseReference.child("Post").child(postID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentPost = dataSnapshot.getValue(Post.class);
                setViewsCount();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        if (postCategory!=null){
            databaseReference.child("User").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        users.add(user);
                        UserTotalPoints.put(user.getEmail().toLowerCase(), user.getPoints());

                        if(user.getEmail()!=null&&email!=null)
                            if (user.getEmail().toLowerCase().equals(email.toLowerCase()))
                            {
                                currentUser = user;
                            }
                    }
                    for (final User user : users)
                    {
                        databaseReference.child("User").child(user.getUserID()).child("level").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    Level level = snapshot.getValue(Level.class);
                                    if (user.getEmail().equals(currentUser.getEmail())){
                                        levels.add(level);
                                    }
                                    else
                                        {
                                        if (level.getCategory().equals(postCategory))
                                            userCategoryPoints.put(user.getEmail().toLowerCase(),level.getPoints());
                                    }
                                }
                                checkIfCategoryExist();

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                    databaseReference.child("Replay").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            replays.removeAll(replays);
                            maxPoints=Integer.MIN_VALUE;
                            mostSuitableUser="";
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Post replay = snapshot.getValue(Post.class);
                                if(replay.getPostID().equals(postID))
                                {
                                    if(userCategoryPoints.get(replay.getUsername().toLowerCase())>maxPoints)
                                    {
                                        maxPoints = userCategoryPoints.get(replay.getUsername().toLowerCase());
                                        mostSuitableUser = replay.getUsername();
                                    }
                                    replay.setPoints(UserTotalPoints.get(replay.getUsername().toLowerCase()));
                                    replays.add(replay);

                                }


                            }
                            dispaly();
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

    public void openDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Replay");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = input.getText().toString();
                if (str.length()!=0)
                {
                    Post replay = new Post(email,str,currentUser.getName());
                    String key = databaseReference.child("Replay").push().getKey();
                    replay.setPostID(postID);
                    databaseReference.child("Replay").child(key).setValue(replay);
                    if (!isPoster)
                    {
                        if (!isExist){
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(currentUser.getUserID());
                            Level level = new Level(1 , postCategory);


                            String k = mDatabase.child("level").push().getKey();
                            level.setID(k);
                            databaseReference.child("User").child(currentUser.getUserID()).child("level").child(k).setValue(level);
                            databaseReference.child("User").child(currentUser.getUserID()).child("points").setValue(currentUser.getPoints()+1);
                        }
                        else{
                            String levelID = levels.get(index).getID();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(currentUser.getUserID()).child("level").child(levelID);
                            mDatabase.child("points").setValue((levels.get(index).getPoints())+1);
                            databaseReference.child("User").child(currentUser.getUserID()).child("points").setValue(currentUser.getPoints()+1);


                        }
                    }
                    Toast.makeText(PostActivity.this,"sent successfully",Toast.LENGTH_LONG).show();

                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.show();
    }

    void dispaly(){
        commentsCount.setText(replays.size()+"");
        adapter = new PostAdapter<Post>(PostActivity.this,replays);
        listView.setAdapter(adapter);

    }


    static int index =-1;
    public void checkIfCategoryExist() {
        int i=0;
        for (Level level : levels)
        {
            if(level.getCategory()!=null && postCategory!=null){
                if(level.getCategory().equals(postCategory))
                {
                    isExist = true;
                    index = i;
                    break;

                }
            }
            i++;
        }
    }


    public void setViewsCount(){
        if(isFirstTimeToSetViewCount){
            postViewsCount.setText((currentPost.getViews()+1)+"");
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Post").child(postID);
            mDatabase.child("views").setValue(currentPost.getViews()+1);
            isFirstTimeToSetViewCount= false;
        }
    }

    @Override
    public void onBackPressed() {
        isPoster=false;
        super.onBackPressed();

    }
}
