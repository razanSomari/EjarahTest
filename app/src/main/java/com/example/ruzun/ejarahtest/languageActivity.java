package com.example.ruzun.ejarahtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class languageActivity extends AppCompatActivity {
    Button lang;
    RadioButton RB;
    RadioGroup langRG;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_language);


        langRG=findViewById(R.id.langRG);

        lang=findViewById(R.id.buttonLang);

        lang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                id=langRG.getCheckedRadioButtonId();
                RB=v.findViewById(id);

                if(id==0){
                    setLocale("ar");
                    recreate();
                }
                else if(id==1){
                    setLocale("en");
                    recreate();
                }
                /*} else{
                    Toast.makeText(getContext(),"Please choose a language", Toast.LENGTH_LONG);
                }*/


            }
        });
    }


    public void setLocale(String lang){
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor=getSharedPreferences("Settings", Activity.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs= getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang=prefs.getString("My_Lang", "");
        setLocale(lang);
    }
}
