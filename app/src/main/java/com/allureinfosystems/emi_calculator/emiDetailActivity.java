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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class emiDetailActivity extends AppCompatActivity {

    private DecimalFormat df = new DecimalFormat("####0.0");
    private DrawerLayout drawer;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.emi_detaildrawer_layout);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        NavigationView navigationView = findViewById(R.id.emi_detail_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(emiDetailActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(emiDetailActivity.this, SettingsMenu.class));
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

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //noinspection unchecked
        ArrayList<? extends HashMap<String, String>> items = (ArrayList<? extends HashMap<String, String>>) getIntent().getSerializableExtra("emiDataset");
        double interestSumValue = getIntent().getDoubleExtra("inerestSum", 0);
        double totalPayValue = getIntent().getDoubleExtra("emiTotalPayText", 0);
        double loanAmountValue = getIntent().getDoubleExtra("emiPrinciple", 0);
        double emiInterestValue = getIntent().getDoubleExtra("emiInterest", 0);
        double emiPeriodValue = getIntent().getDoubleExtra("emiperiod", 0);
        double emiValue = getIntent().getDoubleExtra("emiValue", 0);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.emi_data_recyclerview);
        TextView emiMonthly = findViewById(R.id.emiMonthlyEmiValue);
        TextView loanAmount = findViewById(R.id.emitextAmountValue);
        TextView interestRate = findViewById(R.id.emiDetailInterestValue);
        TextView period = findViewById(R.id.emiDetailPeriodValue);
        TextView totalPay = findViewById(R.id.emiTotalPaymentText);
        TextView interestSum = findViewById(R.id.emiTotalInterestValue);


        loanAmount.setText(df.format(loanAmountValue));
        interestRate.setText(df.format(emiInterestValue));
        period.setText(df.format(emiPeriodValue));
        emiMonthly.setText(df.format(emiValue));
        interestSum.setText(df.format(interestSumValue));
        totalPay.setText(df.format(totalPayValue));


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new EmidataAdapter(items);
        recyclerView.setAdapter(mAdapter);

    }

}
