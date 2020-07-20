package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity  {

    //implements NavigationView.OnNavigationItemSelectedListener

    private DrawerLayout drawer;

    private ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button buttonEmi = findViewById(R.id.btnemi);
        Button buttonLoan = findViewById(R.id.buttonloan);
        Button buttonInterestCal = findViewById(R.id.button_interest);
        Button buttonFdCal = findViewById(R.id.button_fd_calc);
        Button buttonRdCal = findViewById(R.id.button_rd_calc);
        Button buttonSipCal = findViewById(R.id.buttonSip);
        Button buttonPPFCal = findViewById(R.id.buttonPPFCal);
        Button buttonGstCalc = findViewById(R.id.buttonGstCalc);
        Button buttonVat = findViewById(R.id.buttonVat);
        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(MainActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(MainActivity.this, SettingsMenu.class));
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


        buttonEmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, EmiCalculatorActivity.class);
                startActivity(intent);
         }
        });

        buttonLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, LoanComparisonActivity.class);
                startActivity(intent);
            }
        });
        buttonInterestCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, InterestCalculatorActivity.class);
                startActivity(intent);
            }
        });
        buttonInterestCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, InterestCalculatorActivity.class);
                startActivity(intent);
            }
        });
        buttonFdCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, FdCalculatorActivity.class);
                startActivity(intent);
            }
        });

        buttonRdCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, RdCalculatorActivity.class);
                startActivity(intent);

            }
        });
        buttonSipCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, SipCalculatorActivity.class);
                startActivity(intent);

            }
        });
        buttonPPFCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, PPFActivity.class);
                startActivity(intent);

            }
        });

        buttonGstCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, GstActivity.class);
                startActivity(intent);

            }
        });
        buttonVat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animationActivity.animation(v);
                Intent intent = new Intent(MainActivity.this, VatCalculatorActivity.class);
                startActivity(intent);

            }
        });

    }

    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else {

            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                            finishAffinity();
                        }
                    }).create().show();
        }
    }


}

