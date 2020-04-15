package com.example.ruzun.ejarahtest;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter <T> extends ArrayAdapter<Post> {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public PostAdapter(@NonNull Context context, @NonNull List<Post> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        final Post currentPost = getItem(position);

        TextView name =  listItemView.findViewById(R.id.textview_username);

        name.setText(currentPost.getName());

        TextView content = (TextView) listItemView.findViewById(R.id.textview_content);

        content.setText(currentPost.getContent());

        TextView points = (TextView) listItemView.findViewById(R.id.textview_points);

        points.setText(currentPost.getPoints()+"");

        ImageView thumpUp = (ImageView) listItemView.findViewById(R.id.textview_thump_up);

        if (!PostActivity.isPoster)
            thumpUp.setVisibility(View.GONE);
        else
            thumpUp.setImageResource(R.drawable.ic_thumb_up_24dp);


        thumpUp.setOnClickListener(new View.OnClickListener(){
            //------------------------Inner class ----------------------------------//

            //intilization of variables
            ArrayList<Level> levels = new ArrayList<Level>();
            int index =-1;
            boolean isExist =false;
            boolean isFirst =true;
            User poster;


            //----- method to check if category already exists for the user --------//
            public void checkIfCategoryExist()
            {
                int i=0;
                for (Level level : levels)
                {

                    if(level.getCategory()!=null &&PostActivity.postCategory!=null){
                        Log.i("STATE", "TRUE + List size"+levels.size());
                        Log.i("CAR ", level.getCategory() + "POSR CAT "+PostActivity.postCategory);
                        Log.i("BOOLEAN ", level.getCategory().equals(PostActivity.postCategory)+"");
                        if(level.getCategory().equals(PostActivity.postCategory))
                        {
                            isExist = true;
                            index = i;
                            break;
                        }
                    }
                    else{
                        Log.i("STATE", "FLASE + List size"+levels.size());
                    }
                    i++;
                }
                add();
            }

            //------------------------------------------------------------------------------------------------
            public void add()
            {

                Log.i("IS EXIST ", isExist+"");

                if (PostActivity.isPoster&&poster!=null&&isFirst)
                {




                    if (!isExist){
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(poster.getUserID());
                        Level level = new Level(5, PostActivity.postCategory);


                        String k = mDatabase.child("level").push().getKey();
                        level.setID(k);
                        databaseReference.child("User").child(poster.getUserID()).child("level").child(k).setValue(level);
                        isFirst=false;
                    }
                    else{
                        Log.i("LEVEL", "It already exists");
                        Log.i("LEVEL", "index is equal to "+index+" which is "+levels.get(index).getCategory()+" with id"+levels.get(index).getID());
                        String levelID = levels.get(index).getID();
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(poster.getUserID()).child("level").child(levelID);
                        mDatabase.child("points").setValue((levels.get(index).getPoints())+5);
                        isFirst=false;

                    }
                }
            }

            //---------------------------------------------------------------------------------------
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();

                final String email =  currentPost.getUsername();

                databaseReference.child("User").addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            User user = snapshot.getValue(User.class);
                            if(user.getEmail()!=null)
                            {
                                if (user.getEmail().toLowerCase().equals(email.toLowerCase()))
                                    poster = user;
                            }
                        }
                        //--------------//
                        databaseReference.child("User").child(poster.getUserID()).child("level").addValueEventListener(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                levels.removeAll(levels);
                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    Level level = snapshot.getValue(Level.class);
                                    levels.add(level);
                                }
                                checkIfCategoryExist();

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
        });
        return listItemView;

    }
}
