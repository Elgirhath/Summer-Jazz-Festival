package com.example.festivalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.festivalapp.database.DBmanager;
import com.example.festivalapp.schedule.ProgramFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DBmanager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapFragment()).commit();

        dbManager = new DBmanager(this);

        OnInstallEventManager installManager = new OnInstallEventManager();
        installManager.install();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.nav_map:
                            fragment=new MapFragment();
                            break;

                        case R.id.nav_program:
                            fragment=new ProgramFragment(dbManager);
                            break;
                        case R.id.nav_music:
                            fragment=new MusicFragment();
                            break;
                        case R.id.nav_info:
                            fragment=new InfoFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    return true;
                }
            };

}
