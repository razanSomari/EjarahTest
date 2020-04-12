package com.example.ruzun.ejarahtest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

public class languageFragment extends Fragment {
    Button lang;
    RadioButton RB;
    RadioGroup langRG;
    int id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadLocale();
        final View root = inflater.inflate(R.layout.fragment_language, container, false);

        langRG=root.findViewById(R.id.langRG);

        lang=root.findViewById(R.id.buttonLang);
        //id=langRG.getCheckedRadioButtonId();
        RB=root.findViewById(id);

        langRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RB=group.findViewById(checkedId);
                switch (id){
                    case R.id.englishRB:
                        setRB("en");
                        break;
                    case R.id.arabicRB:
                        setRB("ar");
                        break;
            }
        }});
        return root;
        }

    public void setRB(String l){
        setLocale(l);
        getActivity().recreate();
        }

    public void setLocale(String lang){
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences prefs= getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang=prefs.getString("My_Lang", "");
        setLocale(lang);
    }

}

