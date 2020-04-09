package com.example.ruzun.ejarahtest;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class PostActivity extends AppCompatActivity {

    TextView textViewName;
    TextView textViewContent;
    TextView commentsCount;

    FirebaseAuth mFirebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String email;
    PostAdapter<Post> adapter;
    User currentUser;

    ListView listView;
    String name;

    String postID;

    final ArrayList<Post> replays = new ArrayList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = mFirebaseAuth.getCurrentUser().getEmail();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        commentsCount = findViewById(R.id.TextViewPostComments);
        listView = (ListView) findViewById(R.id.replay_list);


        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user.getEmail()!=null&&email!=null)
                        if (user.getEmail().toLowerCase().equals(name.toLowerCase()))
                            currentUser = user;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child("Replay").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                replays.removeAll(replays);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post replay = snapshot.getValue(Post.class);
                    if(replay.getPostID().equals(postID))
                        replays.add(replay);


                }
                dispaly();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        String content;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name= "";
                content = "";
                postID="";
            } else {
                content = extras.getString("CONTENT");
                name = extras.getString("NAME");
                postID = extras.getString("POST_ID");
            }
        } else {
            content= (String) savedInstanceState.getSerializable("CONTENT");
            name= (String) savedInstanceState.getSerializable("NAME");
            postID = (String) savedInstanceState.getSerializable("POST_ID");
        }

        textViewName = findViewById(R.id.textViewPostUsername);
        textViewContent = findViewById(R.id.TextViewPostContent);

        textViewName.setText(name);
        textViewContent.setText(content);
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
}
