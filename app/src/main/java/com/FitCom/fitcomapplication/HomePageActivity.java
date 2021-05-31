package com.FitCom.fitcomapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import com.FitCom.fitcomapplication.BlogScreen.BlogActivity;
import com.FitCom.fitcomapplication.ExerciseScreen.ExerciseActivity;
import com.FitCom.fitcomapplication.NutritionScreen.NutritionsActivity;
import com.FitCom.fitcomapplication.Registery.MainActivity;
import com.FitCom.fitcomapplication.SettingScreen.SettingActivity;
import com.FitCom.fitcomapplication.TraineeScreen.TrainerActivity;
import com.FitCom.fitcomapplication.TrainerScreen.InsertDataActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class HomePageActivity extends AppCompatActivity {

    private BottomNavigationView bnv;
    private ShareActionProvider shareActionProvider;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore;
    private String acc_type;
    private String currentEMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        handleTrainerLayout();

        bnv = findViewById(R.id.bottom_nav_View);
        bnv.setSelectedItemId(R.id.home);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nutrition:
                        startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.exercise:
                        startActivity(new Intent(getApplicationContext(), ExerciseActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.blog:
                        startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.trainer:
                        if(acc_type.equals("1")){
                            startActivity(new Intent(getApplicationContext(), InsertDataActivity.class));
                        }else if(acc_type.equals("0")){
                            startActivity(new Intent(getApplicationContext(), TrainerActivity.class));
                        }
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
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

                    case R.id.trainer:
                        if(acc_type.equals("1")){
                            startActivity(new Intent(getApplicationContext(), InsertDataActivity.class));
                        }else if(acc_type.equals("0")){
                            startActivity(new Intent(getApplicationContext(), TrainerActivity.class));
                        }
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.exercise:
                        startActivity(new Intent(getApplicationContext(), ExerciseActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.blog:
                        startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.home:
                        break;
                }
            }
        });
    }

    private void handleTrainerLayout(){
        currentUser = firebaseAuth.getCurrentUser();
        currentEMail = currentUser.getEmail();


        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        collectionReference.whereEqualTo("email",currentEMail).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(this, error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {

                    Map<String,Object> data = snapshot.getData();
                    acc_type = (String) data.get("trainer");
                }
            }
        });
    }

    private void setTrainerLayout(){

    }

    private void setTraineeLayout(){

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
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(HomePageActivity.this, SettingActivity.class);
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

    @Override
    public void onBackPressed(){}
}
