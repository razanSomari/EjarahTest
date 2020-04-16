package com.example.ruzun.ejarahtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class levelFragment extends Fragment {



    ListView listView;
    String mTitle[] = {"Helth", "Education", "Electronic", "Music", "Advice/question","Tv show","Writing/ stories","Sport","Profession" , " Travel" };
    String mDescription[] = {"Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description"};
    int images[] = {R.drawable.doctor, R.drawable.abc, R.drawable.responsive, R.drawable.guitar, R.drawable.question , R.drawable.music, R.drawable.poetry
            , R.drawable.soccer , R.drawable.light, R.drawable.cityscape};
    ProgressBar bar ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level,container,false);


        listView = view.findViewById(R.id.categories_list);
        // now create an adapter class

        categoriesAdapter adapter = new categoriesAdapter(getContext(), mTitle, images , bar);
        listView.setAdapter(adapter);





        return view ;
    }
}



