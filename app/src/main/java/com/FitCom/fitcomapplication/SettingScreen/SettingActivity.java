package com.FitCom.fitcomapplication.SettingScreen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.FitCom.fitcomapplication.R;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    private ImageButton lang_tr, lang_eng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentLanguage();
        setContentView(R.layout.activity_setting);

        lang_tr = findViewById(R.id.settings_lang_tr);
        lang_eng = findViewById(R.id.settings_lang_eng);

        lang_tr.setOnClickListener(v -> {
            setNewLanguage("tr");
            recreate();
        });
        lang_eng.setOnClickListener(v -> {
            setNewLanguage("en");
            recreate();
        });

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getCurrentLanguage();
    }
    public void getCurrentLanguage(){
        SharedPreferences sharedPrefs = getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        String selected_language = sharedPrefs.getString("selected_lang" ,"");
        setNewLanguage(selected_language);
    }

    public void setNewLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration newConfiguration = new Configuration();
        newConfiguration.locale = locale;
        getBaseContext().getResources().updateConfiguration(newConfiguration,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("preferences",MODE_PRIVATE).edit();
        editor.putString("selected_lang" ,language);
        editor.apply();
    }
}