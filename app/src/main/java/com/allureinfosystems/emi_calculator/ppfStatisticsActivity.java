package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class ppfStatisticsActivity extends AppCompatActivity {

    private RecyclerView ppfRecyclerView;
    private RecyclerView.Adapter ppfAdapter;
    private RecyclerView.LayoutManager ppfLayoutManager;
    private ArrayList<? extends HashMap<String, String>> ppfItems;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppf_statistics);
        drawer = findViewById(R.id.ppf_stat_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.ppf_stat_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(ppfStatisticsActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(ppfStatisticsActivity.this, SettingsMenu.class));
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



        ppfItems = (ArrayList<? extends HashMap<String, String>>) getIntent().getSerializableExtra("ppfDataset");
        ppfRecyclerView = (RecyclerView) findViewById(R.id.ppf_data_recyclerview);
        ppfRecyclerView.setHasFixedSize(true);
        ppfLayoutManager = new LinearLayoutManager(getApplicationContext());
        ppfRecyclerView.setLayoutManager(ppfLayoutManager);
        ppfAdapter = new PPFDataAdapter(ppfItems);
        ppfRecyclerView.setAdapter(ppfAdapter);





    }
         }
