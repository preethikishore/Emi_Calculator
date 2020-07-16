package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity  {

    //implements NavigationView.OnNavigationItemSelectedListener

    private Button buttonEmi;
    private Button buttonLoan;
    private Button buttonInterestCal;
    private Button buttonFdCal;
    private Button buttonRdCal;
    private Button buttonSipCal;
    private Button buttonPPFCal;
    private Button buttonGstCalc;
    private Button buttonVat;
    private DrawerLayout drawer;
    ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonEmi = findViewById(R.id.btnemi);
        buttonLoan = findViewById(R.id.buttonloan);
        buttonInterestCal = findViewById(R.id.button_interest);
        buttonFdCal = findViewById(R.id.button_fd_calc);
        buttonRdCal = findViewById(R.id.button_rd_calc);
        buttonSipCal = findViewById(R.id.buttonSip);
        buttonPPFCal = findViewById(R.id.buttonPPFCal);
        buttonGstCalc = findViewById(R.id.buttonGstCalc);
        buttonVat = findViewById(R.id.buttonVat);
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



    public void OnBackPressed()
    {
     if (drawer.isDrawerOpen(GravityCompat.START))
     {
         drawer.closeDrawer(GravityCompat.START);
     }else
     {
         super.onBackPressed();
     }

    }


}

