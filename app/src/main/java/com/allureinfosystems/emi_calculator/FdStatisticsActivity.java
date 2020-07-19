package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class FdStatisticsActivity extends AppCompatActivity {


    private RecyclerView fdRecyclerView;
    private RecyclerView.Adapter fdAdapter;
    private RecyclerView.LayoutManager fdLayoutManager;
    private ArrayList<? extends HashMap<String, String>> items;
    private DrawerLayout drawer;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fd_statistics);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.fdStat_drawer_layout);

        NavigationView navigationView = findViewById(R.id.fd_statistics_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(FdStatisticsActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(FdStatisticsActivity.this, SettingsMenu.class));
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        items = (ArrayList<? extends HashMap<String, String>>) getIntent().getSerializableExtra("fdDataset");
        fdRecyclerView = (RecyclerView) findViewById(R.id.fd_data_recyclerview);
        fdRecyclerView.setHasFixedSize(true);
        fdLayoutManager = new LinearLayoutManager(getApplicationContext());
        fdRecyclerView.setLayoutManager(fdLayoutManager);

        fdAdapter = new FdDataAdapter(items);
        fdRecyclerView.setAdapter(fdAdapter);


    }
}
