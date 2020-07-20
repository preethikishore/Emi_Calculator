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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ppfStatisticsActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private AdView mAdView;

    @SuppressWarnings("unchecked")
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppf_statistics);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ArrayList<? extends HashMap<String, String>> ppfItems = (ArrayList<? extends HashMap<String, String>>) getIntent().getSerializableExtra("ppfDataset");
        RecyclerView ppfRecyclerView = (RecyclerView) findViewById(R.id.ppf_data_recyclerview);
        ppfRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager ppfLayoutManager = new LinearLayoutManager(getApplicationContext());
        ppfRecyclerView.setLayoutManager(ppfLayoutManager);
        RecyclerView.Adapter ppfAdapter = new PPFDataAdapter(ppfItems);
        ppfRecyclerView.setAdapter(ppfAdapter);





    }
         }
