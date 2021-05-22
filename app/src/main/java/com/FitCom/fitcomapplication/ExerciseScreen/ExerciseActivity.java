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
import com.FitCom.fitcomapplication.HomePageActivity;
import com.FitCom.fitcomapplication.NutritionScreen.NutritionsActivity;
import com.FitCom.fitcomapplication.R;
import com.FitCom.fitcomapplication.Registery.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ExerciseActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem tabItemAllPrograms;
    private TabItem tabItemMyPrograms;
    private ViewPager viewPager;
    private AdapterForViewPager adapterForViewPager;
    private ShareActionProvider shareActionProvider;
    private FirebaseAuth firebaseAuth;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        firebaseAuth = FirebaseAuth.getInstance();
        tabLayout = findViewById(R.id.tabLayout_exercise);
        tabItemAllPrograms = findViewById(R.id.tab_all_programs);
        tabItemMyPrograms = findViewById(R.id.tab_my_programs);
        viewPager = findViewById(R.id.viewpager_exercise);

        adapterForViewPager = new AdapterForViewPager(getSupportFragmentManager(), tabLayout.getTabCount());
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
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nutrition:
                        startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.exercise:
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
            }
        });

        bnv.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nutrition:
                        startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.exercise:
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
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("Download the Fitcom Application!");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            firebaseAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(this,"Signed Out!",Toast.LENGTH_SHORT).show();
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

    public void onBackPressed() {
        count++;
        if(count == 1){
            Toast.makeText(this,"Press back button one more time to go back to login screen!",Toast.LENGTH_SHORT).show();
        }
        else if(count == 2) {
            count = 0;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            firebaseAuth.signOut();
            finish();
            startActivity(intent);
            Toast.makeText(this,"Signed Out!",Toast.LENGTH_SHORT).show();
        }
    }
}