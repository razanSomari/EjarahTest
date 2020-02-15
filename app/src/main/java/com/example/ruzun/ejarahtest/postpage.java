package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class postpage extends AppCompatActivity {

    EditText title, edit1, edit2;
    String text1,text2,text3;
    Context context;
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


                if (title.getText().equals("")) {
                    Toast.makeText(context, "Please enter the title", Toast.LENGTH_SHORT).show();
                } else if (edit1.getText().equals("")) {
                    Toast.makeText(context, "Please enter your problem", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(getApplicationContext(),Timeline.class);
                    startActivity(i);
                }


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!title.getText().equals("") || !edit1.getText().equals("")) {
                    AlertDialog diaBox = AskOption();
                    diaBox.show();
                }else {
                    Intent i = new Intent(getApplicationContext(),Timeline.class);
                    startActivity(i);
                }



            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




            }
        });

    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Exite")
                .setMessage("Are You Sure Want To EXit ?")


                .setPositiveButton("Exite", new DialogInterface.OnClickListener() {

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
