package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class EmiCalculatorActivity extends AppCompatActivity {

    private Button buttonStatistices;
    private PieChart pieChartOne;
    private PieChart pieChartTwo;
    private Button emiCalculate;
    private TextView emiMonthly;
    private TextView emiTotalPaymentText;
    CreatePieChart piechart = new CreatePieChart();
    private EditText principle;
    private EditText interest;
    private EditText term;
    double emi;
    double p;
    double n;
    double r;
    private ArrayList<HashMap<String, String>> emidataDataset;
    HashMap<String, String> map;
    DecimalFormat df = new DecimalFormat("####0.00");
    Double interestSum = 0.0;
    Double emiTotalPayment  = 0.0;
    CommonFuns commonFuns = new CommonFuns();
    ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    private DrawerLayout drawer;
    MessageComment messageComment = new MessageComment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonStatistices = findViewById(R.id.buttonStatistics);
        pieChartOne = findViewById(R.id.piechart_one);
        pieChartTwo = findViewById(R.id.piechart_two);
        principle = findViewById(R.id.emi_edit_principle);
        interest = findViewById(R.id.emi_edit_interest);
        term = findViewById(R.id.emi_edit_year);
        emiCalculate = findViewById(R.id.emi_buttonCalculate);
        emiMonthly = findViewById(R.id.textViewMonthllyemi);
        piechart.CreatePieChartOne(pieChartOne);
        piechart.CreatePieChartOne(pieChartTwo);
        emiTotalPaymentText = findViewById(R.id.emiTotalPayValue);
        drawer = findViewById(R.id.emi_drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_emiview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(EmiCalculatorActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(EmiCalculatorActivity.this, SettingsMenu.class));
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


        emiCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double interestVaue = ParseDouble(String.valueOf(interest.getText()));
                Double termValue = ParseDouble(String.valueOf(term.getText()));
                Double principleAmount = ParseDouble(String.valueOf(principle.getText()));
                animationActivity.animation(v);
                if (principleAmount > 0 && interestVaue > 0 && termValue > 0)  {
                    if (interestVaue <= 50) {
                        if (termValue <= 40) {
                            statsCalc();
                        } else {
                            Toast.makeText(EmiCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(EmiCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EmiCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonStatistices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);

                Double interestVaue = ParseDouble(String.valueOf(interest.getText()));
                Double   termValue = ParseDouble(String.valueOf(term.getText()));


                if (interestVaue <= 50) {


                    if(termValue <= 40)
                    {
                        statsCalc();
                        Intent intent = new Intent(EmiCalculatorActivity.this, emiDetailActivity.class);
                        intent.putExtra("emiDataset", emidataDataset);
                        intent.putExtra("inerestSum", interestSum);
                        intent.putExtra("emiTotalPayText", emiTotalPayment);
                        intent.putExtra("emiPrinciple", p);
                        intent.putExtra("emiInterest", r * 1200);
                        intent.putExtra("emiperiod", n);
                        intent.putExtra("emiValue", emi);
                        v.getContext().startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(EmiCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();

                    }

                }
                else

                {
                    Toast.makeText(EmiCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                }


            }
        });


    }



    private void statsCalc() {
        Double principleAmount =ParseDouble(String.valueOf(principle.getText()));
        Double interestRate = ParseDouble(String.valueOf(interest.getText()));
        Double   tenureValue = ParseDouble(String.valueOf(term.getText()));
        r = interestRate /1200;
        n = tenureValue * 12;
        emi = Math.ceil(((principleAmount * r) * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1));

        Double principleRemain = principleAmount;
        int period = 0;
        Double interestCalc = 0.0;
        Double principlePaid = 0.0;

        emidataDataset = new ArrayList<HashMap<String, String>>();

            do {

                interestCalc = commonFuns.round(principleRemain * r, 0);

                interestSum += interestCalc;
                period++;
                principlePaid = emi - interestCalc;
                if (principlePaid > principlePaid) {
                    principlePaid = principleRemain;
                }
                principleRemain -= principlePaid;
                if (principleRemain < 1) {
                    principleRemain = 0.0;
                }

                map = new HashMap<>();
                map.put("period", String.valueOf(period));
                map.put("principlePaid", df.format(principlePaid));
                map.put("interestCalc", df.format(interestCalc));
                map.put("principleRemain", (df.format(principleRemain)));
                emidataDataset.add(map);


            } while (principleRemain > 0);

            emiTotalPayment = principleAmount + interestSum;
            emiMonthly.setText(df.format(emi));
            emiTotalPaymentText.setText(df.format(emiTotalPayment));
        }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }



}
