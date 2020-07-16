package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

public class LoanComparisonActivity extends AppCompatActivity {

    private EditText loanAmountOne;
    private EditText loanAmountTwo;
    private EditText loanInterestOne;
    private EditText loanInterestTwo;
    private EditText loanPeriodOne;
    private EditText loanPeriodTwo;
    private TextView loanEmiOne;
    private TextView loanEmiTwo;
    private TextView loanIntetrestPayOne;
    private TextView loanIntetrestPayTwo;
    private TextView loanTotalPayOne;
    private TextView loanTotalPayTwo;
    private RadioGroup loanRadioGroupOne;
    private RadioGroup loanRadioGroupTwo;
    private TextView   loanTotalPaymentDifference;
    private TextView loanEmiDifference;
    private Button loanCalculate;
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;
    double inputAmt;
    double interest;
    double tenure;
    double emi;
    double emiOne;
    double emiTwo;
    double emiDifference;
   double interestOne;
   double interestTwo;
   double interestDifference;
   boolean statusOne = false;
   boolean statusTwo = false;
   double totalPayOneValue;
   double totalPayTwoValue;
   double totalPayDifference;
    private DrawerLayout drawer;
    private Boolean showDetailsOne  = true;
    private Boolean showDetailsTwo = true;
    MessageComment messageComment = new  MessageComment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_comparison);
        drawer = findViewById(R.id.loan_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loanAmountOne = findViewById(R.id.loanAmountOneValue);
        loanAmountTwo = findViewById(R.id.loanAmountTwoValue);
        loanInterestOne = findViewById(R.id.loanInterestOneValue);
        loanInterestTwo = findViewById(R.id.loanInterestTwoValue);
        loanPeriodOne = findViewById(R.id.loanperiodOneValue);
        loanPeriodTwo = findViewById(R.id.loanperiodTwoValue);
        loanIntetrestPayOne = findViewById(R.id.InterstPayOneValue);
        loanIntetrestPayTwo = findViewById(R.id.InterstPayTwoValue);
        loanEmiOne = findViewById(R.id.monthlyLoanPayOneValue);
        loanEmiTwo = findViewById(R.id.monthlyLoanPayTwoValue);
        loanRadioGroupOne = findViewById(R.id.loanOneRadioGroup);
        loanRadioGroupTwo = findViewById(R.id.loanTwoRadioGroup);
        loanTotalPayOne = findViewById(R.id.totalLoanPayOneValue);
        loanTotalPayTwo = findViewById(R.id.totalLoanPayTwoValue);
        loanTotalPaymentDifference = findViewById(R.id.loanTotalPaymentDifference);
        loanEmiDifference = findViewById(R.id.loanTotalEmiDifference);
        loanCalculate = findViewById(R.id.loanButtonCalculate);
        final ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();



        NavigationView navigationView = findViewById(R.id.loan_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(LoanComparisonActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(LoanComparisonActivity.this, SettingsMenu.class));
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




        loanRadioGroupOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioid = loanRadioGroupOne.getCheckedRadioButtonId();
                radioButtonOne = findViewById(radioid);

                String selectedTenureMode;

                if (radioButtonOne.isChecked()) {

                    selectedTenureMode = (String) radioButtonOne.getText();

                    System.out.println("Selected tenure  mode :"  +selectedTenureMode);


                    if (selectedTenureMode.equals("Year"))
                    {
                        statusOne = true;

                    }else
                    {
                        statusOne = false;

                    }

                }
            }
        });


        loanRadioGroupTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioid = loanRadioGroupTwo.getCheckedRadioButtonId();
                radioButtonTwo = findViewById(radioid);

                String selectedTenureMode;

                if (radioButtonTwo.isChecked()) {

                    selectedTenureMode = (String) radioButtonTwo.getText();

                    System.out.println("Selected tenure  mode two :"  +selectedTenureMode);


                    if (selectedTenureMode.equals("Year"))
                    {
                        statusTwo = true;

                    }else
                    {
                        statusTwo = false;

                    }

                }
            }
        });

        loanCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animationActivity.animation(v);


                showDetailsOne =true;
                showDetailsTwo =  true;
                Double interestOneText = ParseDouble(String.valueOf(loanInterestOne.getText()));
                Double tenureOne = ParseDouble(String.valueOf(loanPeriodOne.getText()));
                Double loanInputAmountOne = ParseDouble(String.valueOf(loanAmountOne.getText()));
                Double interestTwoText = ParseDouble(String.valueOf(loanInterestTwo.getText()));
                Double tenureTwo = ParseDouble(String.valueOf(loanPeriodTwo.getText()));
                Double loanInputAmountTwo =ParseDouble(String.valueOf(loanAmountTwo.getText()));
                DecimalFormat df = new DecimalFormat("####0.00");

                System.out.println("statusOne :"  +statusOne);
                System.out.println("tenureOne :"  +tenureOne);
                System.out.println("statusTwo :"  +statusTwo);
                System.out.println("tenureTwo:"  +tenureTwo);
                System.out.println("interestOneText :"  +interestOneText);
                System.out.println("interestTwoText:"  +interestTwoText);

                if  (loanInputAmountOne > 0 && interestOneText > 0 && tenureOne > 0 &&
                        loanInputAmountTwo > 0 && interestTwoText > 0 && tenureTwo > 0)
                {
                    if (interestOneText <= 50)
                    {
                        if (statusOne)
                        {
                            if (tenureOne<=40 && tenureOne > 0)
                            {
                                // emi one
                                emiOne = loanCalculation(loanAmountOne, loanInterestOne, loanPeriodOne,statusOne);
                                //interest one  and Total Pay
                                interestOne = loanInterest(loanAmountOne, loanInterestOne, loanPeriodOne, statusOne, emiOne);
                            }
                            else if (tenureOne > 40)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter Number of Years up to 40 for Loan 1", Toast.LENGTH_SHORT).show();
                                showDetailsOne = false;
                            }
                            else if  (tenureOne <= 0)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter a Valid Number of Years for Loan 1", Toast.LENGTH_SHORT).show();
                                showDetailsOne = false;
                            }
                        }
                        else
                        {
                            if (tenureOne<=480 && tenureOne > 0)
                            {
                                // emi one
                                emiOne = loanCalculation(loanAmountOne, loanInterestOne, loanPeriodOne,statusOne);
                                //interest one  and Total Pay
                                interestOne = loanInterest(loanAmountOne, loanInterestOne, loanPeriodOne, statusOne, emiOne);
                            }
                            else if (tenureOne > 480)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter Number of Months up to 480 for Loan 1", Toast.LENGTH_SHORT).show();
                                showDetailsOne = false;
                            }
                            else if  (tenureOne <= 0)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter a Valid Number of Months for Loan 1", Toast.LENGTH_SHORT).show();
                                showDetailsOne = false;
                            }
                        }
                    }
                    else if (interestOneText > 50)
                    {
                        Toast.makeText(LoanComparisonActivity.this, "Please Enter Interest up to 50% for Loan 1", Toast.LENGTH_SHORT).show();
                        showDetailsOne = false;
                    }
                    else if  (interestOneText <= 0)
                    {
                        Toast.makeText(LoanComparisonActivity.this, "Please Enter a Valid value  for Interest for Loan 1", Toast.LENGTH_SHORT).show();
                        showDetailsOne = false;
                    }

                    if (interestTwoText <= 50)
                    {
                        if (statusTwo)
                        {
                            if (tenureTwo<=40 && tenureTwo > 0)
                            {
                                // emi two
                                emiTwo = loanCalculation(loanAmountTwo, loanInterestTwo, loanPeriodTwo,statusTwo);
                                //interest two  and Total Pay
                                interestTwo = loanInterest(loanAmountTwo, loanInterestTwo, loanPeriodTwo, statusTwo, emiTwo);
                            }
                            else if (tenureTwo > 40)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter Number of Years up to 40 for Loan 2", Toast.LENGTH_SHORT).show();
                                showDetailsTwo = false;
                            }
                            else if  (tenureTwo <= 0)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter a Valid Number of Years for Loan 2", Toast.LENGTH_SHORT).show();
                                showDetailsTwo = false;
                            }
                        }
                        else
                        {
                            if (tenureTwo<=480 && tenureTwo > 0)
                            {
                                // emi two
                                emiTwo = loanCalculation(loanAmountTwo, loanInterestTwo, loanPeriodTwo,statusTwo);
                                //interest two  and Total Pay
                                interestTwo = loanInterest(loanAmountTwo, loanInterestTwo, loanPeriodTwo, statusTwo, emiTwo);
                            }
                            else if (tenureTwo > 480)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter Number of Months up to 480 for Loan 2", Toast.LENGTH_SHORT).show();
                                showDetailsTwo = false;
                            }
                            else if  (tenureTwo <= 0)
                            {
                                Toast.makeText(LoanComparisonActivity.this, "Please Enter a Valid Number of Months for Loan 2", Toast.LENGTH_SHORT).show();
                                showDetailsTwo = false;
                            }
                        }
                    }
                    else if (interestTwoText > 50)
                    {
                        Toast.makeText(LoanComparisonActivity.this, "Please Enter Interest up to 50% for Loan 2", Toast.LENGTH_SHORT).show();
                        showDetailsTwo = false;
                    }
                    else if  (interestTwoText <= 0)
                    {
                        Toast.makeText(LoanComparisonActivity.this, "Please Enter a Valid value  for Interest for Loan 2", Toast.LENGTH_SHORT).show();
                        showDetailsTwo = false;
                    }

                    //Set the fields
                    // Emi Values
                    if (showDetailsOne && showDetailsTwo)
                    {
                        loanEmiOne.setText(df.format(emiOne));
                        loanIntetrestPayOne.setText(df.format(interestOne));
                        totalPayOneValue = interestOne + loanInputAmountOne;
                        loanTotalPayOne.setText(df.format(totalPayOneValue));

                        loanEmiTwo.setText(df.format(emiTwo));
                        loanIntetrestPayTwo.setText(df.format(interestTwo));
                        totalPayTwoValue = interestTwo + loanInputAmountTwo;
                        loanTotalPayTwo.setText(df.format(totalPayTwoValue));

                        // emi Difference
                        emiDifference = Math.abs(emiOne) - Math.abs(emiTwo);
                        loanEmiDifference.setText(df.format(emiDifference));
                        totalPayDifference = (long) (Math.abs(totalPayOneValue) - Math.abs(totalPayTwoValue));
                        if (totalPayDifference < 0) {
                            totalPayDifference = totalPayDifference * -1;
                        }
                        loanTotalPaymentDifference.setText(df.format((totalPayDifference)));
                    }
                }
                else
                {
                    Toast.makeText(LoanComparisonActivity.this, "Please Enter All the Fields", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }
    private double loanCalculation(TextView loanInput, TextView loanInterest, TextView loanTenure, Boolean status)
    {
        inputAmt = ParseDouble(String.valueOf(loanInput.getText()));
        interest = ParseDouble(String.valueOf(loanInterest.getText()))/1200;
        tenure = ParseDouble(String.valueOf(loanTenure.getText()));

        if(status)
        {
            tenure = tenure * 12;
        }

        emi = ((inputAmt * interest) * Math.pow(1 + interest, tenure))/(Math.pow(1 + interest, tenure)-1);

        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("EMI: " + df.format(emi));

      return emi;

    }

    private double loanInterest(TextView loanInput, TextView loanInterest, TextView loanTenure, Boolean status, Double emi)
    {
        inputAmt = ParseDouble(String.valueOf(loanInput.getText()));
        interest =ParseDouble(String.valueOf(loanInterest.getText()))/1200;
        tenure = ParseDouble(String.valueOf(loanTenure.getText()));
       if(status)
       {
           tenure = tenure * 12;
       }

        Double principleRemain = inputAmt;
        int period = 0;
        Double interestCalc = 0.0;
        Double interestNew = 0.0;
        Double principlePaid = 0.0;
        while (principleRemain >0)
        {
            interestCalc = principleRemain*interest;
            interestNew = interestNew + interestCalc;
            period++;
            principlePaid = emi - interestCalc;
            principleRemain -= principlePaid;
            if(principleRemain <0)
            {
                principleRemain = 0.0;
            }

        }


        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Interest: " + df.format(interestNew));

        return interestNew;

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
