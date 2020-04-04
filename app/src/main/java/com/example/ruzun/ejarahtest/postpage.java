package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class postpage extends AppCompatActivity {

    EditText contant, tag;
    String text1,text2 ,username , name;
    ImageView pic;

    Context context;

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Post Post;
    User currentUser;
    UserLocation currentUserLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpage);
        mFirebaseAuth = FirebaseAuth.getInstance();
        username =mFirebaseAuth.getCurrentUser().getEmail();
        context = this;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user.getEmail()!=null&&username!=null)
                    {
                        if(user.getEmail().toLowerCase().equals(username.toLowerCase()))
                        {
                            currentUser = user;
                        }
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("userLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserLocation userLocation = snapshot.getValue(UserLocation.class);
                    if(userLocation!=null)
                    {
                        if (snapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                            currentUserLocation = userLocation;
                    }


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        contant = (EditText) findViewById(R.id.edit1);
        tag = (EditText) findViewById(R.id.tag);

        ImageView pic = findViewById(R.id.addimage);

        ImageView send = findViewById(R.id.send);
        ImageView cancel = findViewById(R.id.cancel);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text1=contant.getText().toString();
                text2=tag.getText().toString();


                Post P = new Post(username,text1,text2,currentUser.getName(),currentUserLocation.getL());
                String key = databaseReference.child("Post").push().getKey();
                P.setPostID(key);
                databaseReference.child("Post").child(key).setValue(P);
                Toast.makeText(context, "post sucessfully", Toast.LENGTH_SHORT).show();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (!contant.getText().equals("")) {
                    AlertDialog diaBox = AskOption();
                    diaBox.show();
                }else {
                    Intent i = new Intent(getApplicationContext(),homeFragment.class);
                    startActivity(i);
                }

            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent image = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(image, 1234);

            }
        });




    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Exit")
                .setMessage("Are You Sure Want To Exit ?")


                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
}