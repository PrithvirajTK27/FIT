package com.fitness.fit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigationView;
    private Button signOut;
    private LinearLayout del_con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        signOut = (Button) findViewById(R.id.signOut);
        signOut.setOnClickListener(this);
        del_con = (LinearLayout) findViewById(R.id.del_con);
        del_con.setOnClickListener(this);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.progress:
                        startActivity(new Intent(getApplicationContext(), Progress.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signOut:
                signOut();
                break;
            case R.id.del_con:
                Database_manager obj = new Database_manager(this);
                obj.drop();
                obj.close();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
        }
    }
}