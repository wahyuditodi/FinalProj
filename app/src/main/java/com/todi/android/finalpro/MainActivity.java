package com.todi.android.finalpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.matches:
                        fragment = Match.newInstance();
                        break;
                    case R.id.teams:
                        fragment = Team.newInstance();
                        break;
                    case R.id.favorites:
                        fragment = Favorit.newInstance();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    transaction.replace(R.id.main_container, fragment);
                }
                transaction.commit();
                return true;
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Intent test = getIntent();
        String apa = test.getStringExtra("frag");
        if (!test.hasExtra("frag")) {
            transaction.replace(R.id.main_container, Match.newInstance());
            navigation.setSelectedItemId(R.id.matches);
        } else {
            switch (apa) {
                case "match":
                    transaction.replace(R.id.main_container, Match.newInstance());
                    navigation.setSelectedItemId(R.id.matches);
                    break;
                case "teams":
                    transaction.replace(R.id.main_container, Team.newInstance());
                    navigation.setSelectedItemId(R.id.teams);
                    break;
                case "favorit":
                    transaction.replace(R.id.main_container, Favorit.newInstance());
                    navigation.setSelectedItemId(R.id.favorites);
                    break;
            }
        }
        transaction.commit();
    }
}