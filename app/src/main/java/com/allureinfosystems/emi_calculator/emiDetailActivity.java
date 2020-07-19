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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class emiDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<? extends HashMap<String, String>> items;
    private double interestSumValue;
    private double totalPayValue;
    private double loanAmountValue;
    private double emiInterestValue;
    private double emiPeriodValue;
    private double emiValue;
    private TextView interestSum;
    private TextView totalPay;
    private TextView loanAmount;
    private TextView interestRate;
    private TextView period;
    private TextView emiMonthly;
    DecimalFormat df = new DecimalFormat("####0.0");
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.emi_detaildrawer_layout);

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


        items = (ArrayList<? extends HashMap<String, String>>) getIntent().getSerializableExtra("emiDataset");
        interestSumValue = getIntent().getDoubleExtra("inerestSum",0);
        totalPayValue = getIntent().getDoubleExtra("emiTotalPayText",0);
        loanAmountValue = getIntent().getDoubleExtra("emiPrinciple",0);
        emiInterestValue = getIntent().getDoubleExtra("emiInterest",0);
        emiPeriodValue = getIntent().getDoubleExtra("emiperiod",0);
        emiValue = getIntent().getDoubleExtra("emiValue",0);
        recyclerView = (RecyclerView) findViewById(R.id.emi_data_recyclerview);
        emiMonthly = findViewById(R.id.emiMonthlyEmiValue);
        loanAmount = findViewById(R.id.emitextAmountValue);
        interestRate = findViewById(R.id.emiDetailInterestValue);
        period = findViewById( R.id.emiDetailPeriodValue);
        totalPay = findViewById(R.id.emiTotalPaymentText);
        interestSum = findViewById(R.id.emiTotalInterestValue);


        loanAmount.setText(df.format(loanAmountValue));
        interestRate.setText(df.format(emiInterestValue));
        period.setText(df.format(emiPeriodValue));
        emiMonthly.setText(df.format(emiValue));
        interestSum.setText(df.format(interestSumValue));
        totalPay.setText(df.format(totalPayValue));


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new EmidataAdapter(items);
        recyclerView.setAdapter(mAdapter);

    }

}
