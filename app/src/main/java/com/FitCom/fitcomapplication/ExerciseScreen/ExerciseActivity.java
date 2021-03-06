package com.FitCom.fitcomapplication.ExerciseScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;

import com.FitCom.fitcomapplication.BlogScreen.BlogActivity;
import com.FitCom.fitcomapplication.HomeScreen.HomePageActivity;
import com.FitCom.fitcomapplication.NutritionScreen.NutritionsActivity;
import com.FitCom.fitcomapplication.R;
import com.FitCom.fitcomapplication.SettingScreen.SettingActivity;
import com.FitCom.fitcomapplication.TraineeScreen.TrainerActivity;
import com.FitCom.fitcomapplication.TrainerScreen.InsertDataActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class ExerciseActivity extends AppCompatActivity {

    private ViewPager viewPager;
    ShareActionProvider shareActionProvider;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String acc_type;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        handleTrainerLayout();

        TabLayout tabLayout = findViewById(R.id.tabLayout_exercise);
        TabItem tabItemAllPrograms = findViewById(R.id.tab_all_programs);
        TabItem tabItemMyPrograms = findViewById(R.id.tab_my_programs);
        viewPager = findViewById(R.id.viewpager_exercise);

        AdapterForViewPager adapterForViewPager = new AdapterForViewPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapterForViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        BottomNavigationView bnv = findViewById(R.id.bottom_nav_ViewE);
        bnv.setSelectedItemId(R.id.exercise);
        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nutrition:
                    startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.exercise:
                    return true;

                case R.id.trainer:
                    if(acc_type.equals("1")){
                        startActivity(new Intent(getApplicationContext(), InsertDataActivity.class));
                    }else if(acc_type.equals("0")){
                        startActivity(new Intent(getApplicationContext(), TrainerActivity.class));
                    }
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.blog:
                    startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        bnv.setOnNavigationItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nutrition:
                    startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                    overridePendingTransition(0, 0);
                    break;

                case R.id.exercise:
                    break;

                case R.id.trainer:
                    if(acc_type.equals("1")){
                        startActivity(new Intent(getApplicationContext(), InsertDataActivity.class));
                    }else if(acc_type.equals("0")){
                        startActivity(new Intent(getApplicationContext(), TrainerActivity.class));
                    }
                    overridePendingTransition(0, 0);
                    break;

                case R.id.blog:
                    startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                    overridePendingTransition(0, 0);
                    break;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    overridePendingTransition(0, 0);
                    break;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent(getString(R.string.str_download));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(ExerciseActivity.this, SettingActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);
    }

    private void handleTrainerLayout(){
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String currentEMail = Objects.requireNonNull(currentUser).getEmail();

        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        collectionReference.whereEqualTo("email", currentEMail).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(this, Objects.requireNonNull(error.getLocalizedMessage()).toString(),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {

                    Map<String,Object> data = snapshot.getData();
                    acc_type = (String) Objects.requireNonNull(data).get("trainer");
                }
            }
        });
    }

    public void onBackPressed() {}
}