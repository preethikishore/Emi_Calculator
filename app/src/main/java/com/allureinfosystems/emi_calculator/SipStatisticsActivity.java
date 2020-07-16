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

public class SipStatisticsActivity extends AppCompatActivity {

    private RecyclerView sipRecyclerView;
    private RecyclerView.Adapter sipAdapter;
    private RecyclerView.LayoutManager sipLayoutManager;
    private ArrayList<? extends HashMap<String, String>> sipItems;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sip_statistics);
        drawer = findViewById(R.id.sip_stat_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.sip_stat_navigator_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(SipStatisticsActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(SipStatisticsActivity.this, SettingsMenu.class));
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


        sipItems = (ArrayList<? extends HashMap<String, String>>) getIntent().getSerializableExtra("sipDataset");
        sipRecyclerView = (RecyclerView) findViewById(R.id.sip_data_recyclerview);
        sipRecyclerView.setHasFixedSize(true);
        sipLayoutManager = new LinearLayoutManager(getApplicationContext());
        sipRecyclerView.setLayoutManager(sipLayoutManager);
        sipAdapter = new SipDataAdapter(sipItems);
        sipRecyclerView.setAdapter(sipAdapter);


    }
}
