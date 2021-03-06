package com.FitCom.fitcomapplication.Registery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.FitCom.fitcomapplication.HomeScreen.HomePageActivity;
import com.FitCom.fitcomapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String selected_lang;
    SharedPreferences sharedPref;
    SharedPreferences.Editor ed;
    Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xxx();
        //getCurrentLanguage();
        setContentView(R.layout.activity_main);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null) {

            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void getCurrentLanguage(){
        SharedPreferences sharedPrefs = getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        String selected_language = sharedPrefs.getString("selected_lang" ,"");
        setNewLanguage(selected_language);
    }

    public void xxx(){
        String newLoc = Locale.getDefault().getLanguage().toString();

        sharedPref = getSharedPreferences("preferences", MODE_PRIVATE);
        selected_lang = sharedPref.getString("selected_lang" ,"");

        if(sharedPref.contains("preferences")){
            selected_lang = sharedPref.getString("selected_lang" ,"");
            setNewLanguage(selected_lang);
        }else if(!sharedPref.contains("preferences")){
            ed = sharedPref.edit();
            ed.putString("selected_lang" ,newLoc);
            ed.apply();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lang_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_en) {
            setNewLanguage("en");
            recreate();
            return true;
        }
        else {
            setNewLanguage("tr");
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       // getCurrentLanguage();
        xxx();
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