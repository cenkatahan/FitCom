package com.FitCom.fitcomapplication.SettingScreen;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.FitCom.fitcomapplication.R;
import com.FitCom.fitcomapplication.Registery.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    private ImageButton lang_tr, lang_eng, exit, gmail, insta;
    private TextView gmail_link, insta_link;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentLanguage();
        setContentView(R.layout.activity_setting);

        firebaseAuth = FirebaseAuth.getInstance();
        lang_tr = findViewById(R.id.settings_lang_tr);
        lang_eng = findViewById(R.id.settings_lang_eng);
        exit = findViewById(R.id.imageButtonExit);
        gmail = findViewById(R.id.insta_icon);
        insta = findViewById(R.id.gmail_icon);
        gmail_link = findViewById(R.id.email_link);
        insta_link = findViewById(R.id.insta_link);

        gmail_link.setOnClickListener(v -> {
          clickToSendEMail();
        });

        gmail.setOnClickListener(v -> {
            clickToSendEMail();
        });

        insta.setOnClickListener(v -> {
            goToInsta();
        });

        insta_link.setOnClickListener(v -> {
            goToInsta();
        });

        lang_tr.setOnClickListener(v -> {
            setNewLanguage("tr");
            recreate();
        });
        lang_eng.setOnClickListener(v -> {
            setNewLanguage("en");
            recreate();
        });

        exit.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);
        });

        ActionBar ab = getSupportActionBar();
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

    public void clickToSendEMail(){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setType("text/plain");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.email_link)});
        i.setPackage("com.google.android.gm");
        ActivityManager am = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);

        try {
            if (i.resolveActivity(this.getPackageManager()) != null) {
                if(am != null){
                    am.killBackgroundProcesses("com.google.android.gm");
                    i.removeExtra(Intent.EXTRA_EMAIL);
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.email_link)});
                    this.startActivity(i);
                }
                this.startActivity(i);
            }
        } catch (android.content.ActivityNotFoundException ex) {}
    }

    public void goToInsta(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url)));
        startActivity(browserIntent);
    }
}