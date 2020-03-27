package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ruzun.ejarahtest.Post;
import com.example.ruzun.ejarahtest.R;

import java.util.List;

public class PostAdapter <T> extends ArrayAdapter<Post> {

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


        Post currentPost = getItem(position);

        TextView name =  listItemView.findViewById(R.id.textview_username);

        name.setText(currentPost.getName());

        TextView content = (TextView) listItemView.findViewById(R.id.textview_content);

        content.setText(currentPost.getContent());

        TextView points = (TextView) listItemView.findViewById(R.id.textview_points);

        points.setText(currentPost.getPoints()+"");


        return listItemView;

    }
}
