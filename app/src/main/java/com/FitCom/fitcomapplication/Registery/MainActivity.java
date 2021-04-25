package com.FitCom.fitcomapplication.Registery;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.FitCom.fitcomapplication.R;

public class MainActivity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//1,2,3
    @Override
    public void onBackPressed() {
        count++;
        if(count == 1){
            Toast.makeText(this,"Press back button one more time to go back to login screen!",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(count == 2) {
            count = 0;
            Toast.makeText(this,"Exited!",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}