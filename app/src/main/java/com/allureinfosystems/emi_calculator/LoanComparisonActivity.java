package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;

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
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;
    private double inputAmt;
    private double interest;
    private double tenure;
    private double emi;
    private double emiOne;
    private double emiTwo;
    private double emiDifference;
   private double interestOne;
   private double interestTwo;
   double interestDifference;
   private boolean statusOne = false;
   private boolean statusTwo = false;
   private double totalPayOneValue;
   private double totalPayTwoValue;
   private double totalPayDifference;
    private DrawerLayout drawer;
    private Boolean showDetailsOne  = true;
    private Boolean showDetailsTwo = true;
    MessageComment messageComment = new  MessageComment();
    private Button shareButton;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_comparison);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        Button loanCalculate = findViewById(R.id.loanButtonCalculate);
        shareButton = findViewById(R.id.loan_compare_share);
        shareButton.setVisibility(View.INVISIBLE);
        Button resetButton = findViewById(R.id.loanButtonReset);

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

      resetButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          animationActivity.animation(v);
          clear(v);
         }
      });


        loanRadioGroupOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioid = loanRadioGroupOne.getCheckedRadioButtonId();
                radioButtonOne = findViewById(radioid);

                String selectedTenureMode;

                if (radioButtonOne.isChecked()) {

                    selectedTenureMode = (String) radioButtonOne.getText();

                    System.out.println("Selected tenure  mode :"  +selectedTenureMode);


                    statusOne = selectedTenureMode.equals("Year");

                }
            }
        });

 shareButton.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         String shareStatusOne;
         String shareStatusTwo;

         if(statusOne)
         {
             shareStatusOne =  "Year";
         }else
         {
             shareStatusOne =  "Months";
         }

         if(statusTwo)
         {
             shareStatusTwo =  "Year";
         }else
         {
             shareStatusTwo =  "Months";
         }

         String shareLoanAmountOne = String.valueOf(loanAmountOne.getText());
         String shareLoanAmountTwo = String.valueOf(loanAmountTwo.getText());
         String ShareInterestOne = String.valueOf(loanInterestOne.getText());
         String ShareInterestTwo = String.valueOf(loanInterestTwo.getText());
         String sharePeriodOne = String.valueOf(loanPeriodOne.getText());
         String sharePeriodTwo = String.valueOf(loanPeriodTwo.getText());
         String shareMonthlyemiOne = String.valueOf(loanEmiOne.getText());
         String shareMonthlyemiTwo = String.valueOf(loanEmiTwo.getText());
         String shareInterestPayOne = String.valueOf(loanIntetrestPayOne.getText());
         String shareInterestPayTwo = String.valueOf(loanIntetrestPayTwo.getText());
         String c = String.valueOf(loanTotalPayOne.getText());
         String shareTotalPayTwo = String.valueOf(loanTotalPayTwo.getText());
         String shareTotalDifference = String.valueOf(loanTotalPaymentDifference.getText());
         String shareEmiDifference = String.valueOf(loanEmiDifference.getText());


         String a = "Loan Comparison Details  :" + "\n\n" +
                 "Loan Amount One : " + shareLoanAmountOne + " \n" +
                 "Interest Rate One: " + ShareInterestOne + "%" + " \n" +
                 "Period One: " + sharePeriodOne +" - "+ shareStatusOne +"\n\n" +
                 "Emi Amount One : " + shareMonthlyemiOne + "\n" +
                 "Interest Pay One : " + shareInterestPayOne + "\n" +
                 "Total Pay One : " + shareInterestPayOne + "\n\n" +

                 "Loan Amount Two : " + shareLoanAmountTwo + " \n" +
                 "Interest Rate Two: " + ShareInterestTwo + "%" + " \n" +
                 "Period Two: " + sharePeriodTwo +" - "+ shareStatusTwo +"\n\n" +
                 "Emi Amount Two : " + shareMonthlyemiTwo + "\n" +
                 "Interest Pay Two : " + shareInterestPayTwo + "\n" +
                 "Total Pay Two : " + shareInterestPayTwo + "\n\n" +

                 "Emi Difference Two : " + shareEmiDifference + "\n" +
                 "Total Pay Difference  : " + shareTotalDifference + "\n";

         String contentShare = new String(a);
         Intent share = new Intent(android.content.Intent.ACTION_SEND);
         share.setType("text/plain");
         share.putExtra(Intent.EXTRA_SUBJECT, "PPF Information:");
         share.putExtra(Intent.EXTRA_TEXT, contentShare);
         startActivity(Intent.createChooser(share, "Share via"));


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


                    statusTwo = selectedTenureMode.equals("Year");

                }
            }
        });

        loanCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                shareButton.setVisibility(View.VISIBLE);
                animationActivity.animation(v);


                showDetailsOne = true;
                showDetailsTwo =  true;
                double interestOneText = ParseDouble(String.valueOf(loanInterestOne.getText()));
                double tenureOne = ParseDouble(String.valueOf(loanPeriodOne.getText()));
                double loanInputAmountOne = ParseDouble(String.valueOf(loanAmountOne.getText()));
                double interestTwoText = ParseDouble(String.valueOf(loanInterestTwo.getText()));
                double tenureTwo = ParseDouble(String.valueOf(loanPeriodTwo.getText()));
                double loanInputAmountTwo =ParseDouble(String.valueOf(loanAmountTwo.getText()));
                DecimalFormat df = new DecimalFormat("####0.00");


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
                        emiDifference = Math.abs(emiOne - emiTwo);
                        loanEmiDifference.setText(df.format(emiDifference));
                        totalPayDifference = (long) (Math.abs(totalPayOneValue - totalPayTwoValue));

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

    private double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }

    private void clear(View v) {
        loanAmountOne.setText("");
        loanAmountTwo.setText("");
        loanInterestOne.setText("");
        loanInterestTwo.setText("");
        loanPeriodOne.setText("");
        loanPeriodTwo.setText("");
        loanEmiOne.setText("0");
        loanEmiTwo.setText("0");
        loanTotalPayOne.setText("0");
        loanTotalPayTwo.setText("0");
        loanIntetrestPayOne.setText("0");
        loanIntetrestPayTwo.setText("0");
        loanTotalPayOne.setText("0");
        loanTotalPayTwo.setText("0");
        loanEmiDifference.setText("0");
        loanTotalPaymentDifference.setText("0");
    }



}
