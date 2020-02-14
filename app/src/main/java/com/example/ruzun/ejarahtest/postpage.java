package com.example.ruzun.ejarahtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class postpage extends AppCompatActivity {

    EditText title, edit1, edit2;
    String text1,text2,text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpage);

        title = (EditText) findViewById(R.id.editText);
        edit1  = (EditText) findViewById(R.id.editText2);
        edit2  = (EditText) findViewById(R.id.editText3);

        ImageButton button1 = findViewById(R.id.imageButton2);
        ImageButton button2 = findViewById(R.id.imageButton);
        ImageButton button3 = findViewById(R.id.imageButton3);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text1=title.getText().toString();
                text2=edit1.getText().toString();
                text3=edit2.getText().toString();


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(0);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });

    }
}
