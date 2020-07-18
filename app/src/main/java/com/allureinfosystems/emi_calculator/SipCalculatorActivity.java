package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SipCalculatorActivity extends AppCompatActivity {

    private Spinner sipSpinnerDepositFrequency;
    private DatePickerDialog datePickerDialog;
    private Button sipButtonDateGet;
    private Button sipButtonCalculate;
    private TextView sipSelectDate;
    private RadioGroup sipRadioGroup;
    private RadioButton sipRadioButton;

    private EditText sipAmountOfDeposit;
    private EditText sipRateOfInterest;
    private TextView tenureInput;
    private TextView sipDateOfInvestment;
    private TextView sipMaturityDate;
    private TextView sipInvestAmount;
    private TextView sipInterest;
    private TextView sipMaturityValue;
    private TextView sipTotalInterest;
    private Button sipShareResult;
    GetDate getdate = new GetDate();
    SpinnerData spinnerData = new SpinnerData();

    private double inputAmount;
    private double interestPercent;
    private int tenure;
    private double maturityAmt = 0;
    private double investmentAmt = 0;
    private double interestAmt = 0;
    private String selected_tenure_mode;
    boolean status;
    private Button sipReset;
    private ArrayList<HashMap<String, String>> sipdataDataset;
    HashMap<String, String> sipmap;
    MaturityDateCalculation maturityDateCalculation = new MaturityDateCalculation();
    private Button sipButtonStat;
    private DrawerLayout drawer;
    ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    String deposit_interval;


    DecimalFormat decimal = new DecimalFormat("####0.0");

    String[] items = new String[]{
            "Yearly", "Monthly", "Quarterly", "Half Yearly", "Bi-Monthly", "Thrice-Yearly"
    };
    MessageComment messageComment = new MessageComment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sip_calculator);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.sip_drawer_layout);

        NavigationView navigationView = findViewById(R.id.sip_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.help:
                        startActivity(new Intent(SipCalculatorActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(SipCalculatorActivity.this, SettingsMenu.class));
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

        sipSpinnerDepositFrequency = findViewById(R.id.sipDepositFrequecy);
        sipSelectDate = findViewById(R.id.sipInvestDate);
        sipButtonDateGet = findViewById(R.id.sipButtonDateGet);
        sipAmountOfDeposit = findViewById(R.id.sipInputAmoutValue);
        sipRateOfInterest = findViewById(R.id.sipRateOfInterestValue);
        tenureInput = findViewById(R.id.sipTenureValue);
        sipRadioGroup = findViewById(R.id.sipRadioButtonGroup);
        sipDateOfInvestment = findViewById(R.id.sipInvestMentCalculatedDate);
        sipMaturityDate = findViewById(R.id.sipCalculatedMaturityValue);
        sipInvestAmount = findViewById(R.id.sipInvestAmountResult);
        sipInterest = findViewById(R.id.sipRateOfInterestValue);
        sipMaturityValue = findViewById(R.id.sipMaturityValue);
        sipButtonCalculate = findViewById(R.id.sipButtonCalculate);
        sipTotalInterest = findViewById(R.id.sip_calculator_total_interest_value);
        sipButtonStat = findViewById(R.id.sipButtonStati);
        sipShareResult = findViewById(R.id.sip_share_result);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        sipSelectDate.setText(currentDate);
        sipReset = findViewById(R.id.sipButtonReset);
        sipShareResult.setVisibility(View.INVISIBLE);

        final DecimalFormat df = new DecimalFormat("####0.00");
        sipReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sipShareResult.setVisibility(View.INVISIBLE);
                animationActivity.animation(v);
                clear(v);
            }
        });

        sipShareResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String maturityAmount = df.format(maturityAmt);
                String ShareInputAmount = String.valueOf(sipAmountOfDeposit.getText());
                String ShareRateOfInterest = String.valueOf(sipRateOfInterest.getText());
                String ShareTenureInput = String.valueOf(tenureInput.getText());
                String ShareMaturity = String.valueOf(maturityAmount);
                String ShareTotalInterest = String.valueOf(df.format(interestAmt));
                String ShareDepositMode = deposit_interval;
                String ShareInvestDate = String.valueOf(sipSelectDate.getText());
                String ShareMaturityDate = String.valueOf(sipMaturityDate.getText());
                String ShareStatus;
                if (status) {
                    ShareStatus = "Years";
                } else {
                    ShareStatus = "Months";
                }

                String a = "SIP Details  :" + "\n\n" + "Input Amount : " + ShareInputAmount + " \n" +
                        "Interest Rate : " + ShareRateOfInterest + "%" + " \n" +
                        "Tenure Value : " + ShareTenureInput + " - " + ShareStatus + "\n" +
                        "Deposit Mode : " + ShareDepositMode + " \n" + "\n" + "\n" +
                        "Maturity Amount : " + ShareMaturity + "\n" +
                        "Total Interest Value : " + ShareTotalInterest + "\n" + "\n" +
                        "Investment Date :" + ShareInvestDate + "\n" +
                        "Maturity Date :" + ShareMaturityDate;

                String contentShare = new String(a);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "SIP Information:");
                share.putExtra(Intent.EXTRA_TEXT, contentShare);
                startActivity(Intent.createChooser(share, "Share via"));

            }
        });

        sipButtonStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);


                inputAmount = ParseDouble(String.valueOf(sipAmountOfDeposit.getText()));
                double sipInterest = ParseDouble(String.valueOf(sipRateOfInterest.getText()));
                double sipTenure = ParseDouble(String.valueOf(tenureInput.getText()));
                if (inputAmount < 0 && sipInterest < 0 && sipTenure < 0) {

                    if (sipInterest <= 50) {

                        if (status) {

                            if (sipTenure <= 40) {
                                sip_calculation();
                                Intent intent = new Intent(v.getContext(), SipStatisticsActivity.class);
                                intent.putExtra("sipDataset", sipdataDataset);
                                v.getContext().startActivity(intent);
                            } else {
                                Toast.makeText(SipCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (sipTenure <= 480) {
                                sip_calculation();
                                Intent intent = new Intent(v.getContext(), SipStatisticsActivity.class);
                                intent.putExtra("sipDataset", sipdataDataset);
                                v.getContext().startActivity(intent);
                            } else {
                                Toast.makeText(SipCalculatorActivity.this, messageComment.messagePeroidMonth, Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        Toast.makeText(SipCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(SipCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();
                }


            }
        });


        sipRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioid = sipRadioGroup.getCheckedRadioButtonId();
                sipRadioButton = findViewById(radioid);
                status = false;

                if (sipRadioButton.isChecked()) {

                    selected_tenure_mode = (String) sipRadioButton.getText();

                    if (selected_tenure_mode.equals("Year")) {
                        status = true;

                    } else {
                        status = false;

                    }

                }

            }
        });


        sipButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animationActivity.animation(v);
                sipShareResult.setVisibility(View.VISIBLE);
                inputAmount = ParseDouble(String.valueOf(sipAmountOfDeposit.getText()));
                double sipInterest = ParseDouble(String.valueOf(sipRateOfInterest.getText()));
                double sipTenure = ParseDouble(String.valueOf(tenureInput.getText()));
                if (inputAmount > 0 && sipInterest > 0 && sipTenure > 0) {

                    if (sipInterest <= 50) {

                        if (status) {

                            if (sipTenure <= 40) {
                                sip_calculation();
                                sipInvestAmount.setText(df.format(investmentAmt));
                                sipMaturityValue.setText(df.format(maturityAmt));
                                sipTotalInterest.setText(df.format(interestAmt));
                            } else {
                                Toast.makeText(SipCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (sipTenure <= 480) {
                                sip_calculation();
                                sipInvestAmount.setText(df.format(investmentAmt));
                                sipMaturityValue.setText(df.format(maturityAmt));
                                sipTotalInterest.setText(df.format(interestAmt));
                            } else {
                                Toast.makeText(SipCalculatorActivity.this, messageComment.messagePeroidMonth, Toast.LENGTH_SHORT).show();
                            }


                        }

                    } else {
                        Toast.makeText(SipCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(SipCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();
                }

            }
        });

        sipButtonDateGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getdate.getdate(sipSelectDate, datePickerDialog, SipCalculatorActivity.this);
            }
        });

        spinnerData.initspinnerfooter(sipSpinnerDepositFrequency, SipCalculatorActivity.this, Arrays.asList(items));

    }

    private void sip_calculation() {

        maturityAmt = 0;
        investmentAmt = 0;
        interestAmt = 0;


        deposit_interval = sipSpinnerDepositFrequency.getSelectedItem().toString();
        tenure = (int) ParseDouble(String.valueOf(tenureInput.getText()));

        maturityDateCalculation.caculateMaturityDate(status, tenure, sipSelectDate, sipMaturityDate, sipDateOfInvestment);

        if (status) {
            tenure = tenure * 12;
        }

        String rdDate = (String) sipSelectDate.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(rdDate));

        } catch (ParseException e) {

            e.printStackTrace();
        }

        inputAmount = ParseDouble(String.valueOf(sipAmountOfDeposit.getText()));
        interestPercent = ParseDouble(String.valueOf(sipRateOfInterest.getText()));
        interestPercent = (interestPercent / 100) / 12;

        sipdataDataset = new ArrayList<HashMap<String, String>>();

        for (int i = 1; i <= tenure; i++) {
            c.add(Calendar.MONTH, 1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf1.format(c.getTime());

            if (deposit_interval.equals("Monthly")) {
                investmentAmt += inputAmount;
                maturityAmt += inputAmount;
            }
            else if (deposit_interval.equals("Yearly") && i % 12 == 1) {
                investmentAmt += inputAmount;
                maturityAmt += inputAmount;
            } else if (deposit_interval.equals("Quarterly") && i % 3 == 1) {
                investmentAmt += inputAmount;
                maturityAmt += inputAmount;
            } else if (deposit_interval.equals("Half Yearly") && i % 6 == 1) {
                investmentAmt += inputAmount;
                maturityAmt += inputAmount;
            } else if (deposit_interval.equals("Bi-Monthly") && i % 2 == 1) {
                investmentAmt += inputAmount;
                maturityAmt += inputAmount;
            } else if (deposit_interval.equals("Thrice-Yearly") && i % 4 == 1) {
                investmentAmt += inputAmount;
                maturityAmt += inputAmount;
            } else {
                investmentAmt += 0;
                maturityAmt += 0;
            }


            interestAmt = maturityAmt * interestPercent;
            maturityAmt += interestAmt;
            sipmap = new HashMap<>();
            int defaultCapInterest = 0;
            sipmap.put("sipDate", date);
            sipmap.put("sipInvestAmt", String.valueOf(investmentAmt));
            sipmap.put("sipInterestAmount", (decimal.format(interestAmt)));
            sipmap.put("sipBalance", (decimal.format(maturityAmt)));
            sipdataDataset.add(sipmap);

        }

    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;
            }
        } else return 0;
    }
    public void clear(View v) {
        sipAmountOfDeposit.setText("");
        sipRateOfInterest.setText("");
        tenureInput.setText("");
        sipInvestAmount.setText("0");
        sipMaturityValue.setText("0");
        sipTotalInterest.setText("0");
        sipMaturityDate.setVisibility(View.INVISIBLE);
        sipDateOfInvestment.setVisibility(View.INVISIBLE);

    }

}
