package com.example.ruzun.ejarahtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {

    TextView textViewName;
    TextView textViewContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        String name;
        String content;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name= "";
                content = "";
            } else {
                content = extras.getString("CONTENT");
                name = extras.getString("NAME");
            }
        } else {
            content= (String) savedInstanceState.getSerializable("CONTENT");
            name= (String) savedInstanceState.getSerializable("NAME");
        }

        textViewName = findViewById(R.id.textViewPostUsername);
        textViewContent = findViewById(R.id.TextViewPostContent);

        textViewName.setText(name);
        textViewContent.setText(content);










    }
}
