package com.fitness.fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            startActivity(new Intent(getApplicationContext(),Home.class));
            overridePendingTransition(0,0);
            updateUI(currentUser);
        }
        else {
            startActivity(new Intent(MainActivity.this, UserRegister.class));
            currentUser = mAuth.getCurrentUser();
            updateUI_D(currentUser);
        }
            }
        }, 5000);
    }

    private void updateUI_D(FirebaseUser currentUser) {
    }
    public static void updateUI(FirebaseUser currentUser) {
    }

}