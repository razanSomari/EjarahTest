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


public class categoriesAdapter extends ArrayAdapter<String> {
    /* int count = 0 ;
     FirebaseAuth mFirebaseAuth;
     FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
     DatabaseReference databaseReference = firebaseDatabase.getReference();
 */
    Context context;
    String rTitle[];
    int rImgs[];
    ProgressBar progressBar ;

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
        ProgressBar progressBar1 = listItemView.findViewById(R.id.bar);


        progressBar1.setProgress(10);


        // now set our resources on views
        images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle[position]);





        return listItemView;
    }
}
