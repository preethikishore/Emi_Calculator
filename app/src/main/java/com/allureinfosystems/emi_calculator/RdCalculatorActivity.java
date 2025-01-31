package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class RdCalculatorActivity extends AppCompatActivity {


    private DatePickerDialog datePicker;
    private TextView rdSelectDate;
    private EditText rdInputAmountValue;
    private EditText rdRateOfInterestValue;
    private EditText rdTenureValue;
    private TextView rdInvestmentAmount;
    private TextView rdTotalInterest;
    private TextView rdMaturityValue;
    private TextView rdDateOfInvestment;
    private TextView rdDateOfMaturity;
    private RadioGroup rdRadioGroup;
    private RadioButton rdRadioButton;
    private String selectedTenureMode;
    private boolean status = false;
    private TextView rdCalculatedInvestDate;
    private String investDate;
    private String maturity;
    private GetDate getDate = new GetDate();
    private MaturityDateCalculation maturityDateCalculation = new MaturityDateCalculation();
    private Double interestPayout ;
    private Double totalInterest ;
    private Double capitalizedInterest;
    private Double inputAmount ;
    private Double amount ;
    private DecimalFormat decimal = new DecimalFormat("####0.0");
    private ArrayList<HashMap<String, String>> rddataDataset;
    private HashMap<String, String> rdmap;
    private ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    private DrawerLayout drawer;
    private Button shareButton ;
    private InterstitialAd mInterstitialAd;

    private MessageComment messageComment = new MessageComment();


    @SuppressLint({"SourceLockedOrientationActivity", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rd_calculator);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawer = findViewById(R.id.rd_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.rd_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(RdCalculatorActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(RdCalculatorActivity.this, SettingsMenu.class));
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
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8564435465482275/3880292929");
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        rdInputAmountValue = findViewById(R.id.rdDepositAmountValue);
        rdRateOfInterestValue = findViewById(R.id.rdInterestValue);
        rdTenureValue = findViewById(R.id.rdTenureValue);
        rdInvestmentAmount = findViewById(R.id.rdInvestmentAmount);
        rdTenureValue = findViewById(R.id.rdTenureValue);
        rdTotalInterest = findViewById(R.id.rdTotalInterest);
        rdMaturityValue = findViewById(R.id.rdMaturityValue);
        rdDateOfInvestment = findViewById(R.id.rdDateOfInvestment);
        rdDateOfMaturity = findViewById(R.id.rdCalculatorMaturityDate);
        rdCalculatedInvestDate = findViewById(R.id.rdCalculatorInvestmentDate);
        rdRadioGroup = findViewById(R.id.rdRadioButtonGroup);
        rdSelectDate = findViewById(R.id.rdDateOfInvestment);
        Button rdButtonGetDate = findViewById(R.id.rd_buttongetdate);
        Button rdButtonCalculate = findViewById(R.id.rdButtonInterestCalculate);
        Button rdButtonStat = findViewById(R.id.rdButtonCalcStatistics);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        rdSelectDate.setText(currentDate);
        shareButton = findViewById(R.id.rdButtonShare);
        shareButton.setVisibility(View.INVISIBLE);
        Button resetButton = findViewById(R.id.rdButtonReset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                clear(v);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String maturityAmount = decimal.format(amount);
                String ShareInputAmount = String.valueOf(rdInputAmountValue.getText());
                String ShareRateOfInterest = String.valueOf(rdRateOfInterestValue.getText());
                String ShareTenureInput = String.valueOf(rdTenureValue.getText());
                String ShareMaturity = String.valueOf(maturityAmount);
                String ShareTotalInterest = String.valueOf(decimal.format(totalInterest));
                String ShareInvestDate = String.valueOf(rdSelectDate.getText());
                String ShareMaturityDate = String.valueOf(rdDateOfMaturity.getText());
                String ShareStatus;
                if (status) {
                    ShareStatus = "Years";
                } else {
                    ShareStatus = "Months";
                }

                String a = "RD Details  :" + "\n\n" + "Input Amount : " + ShareInputAmount + " \n" +
                        "Interest Rate : " + ShareRateOfInterest + "%" + " \n" +
                        "Tenure Value : " + ShareTenureInput + " - " + ShareStatus + "\n" +
                        "Maturity Amount : " + ShareMaturity + "\n" +
                        "Total Interest Value : " + ShareTotalInterest + "\n" + "\n" +
                        "Investment Date :" + ShareInvestDate + "\n" +
                        "Maturity Date :" + ShareMaturityDate;

                String contentShare = new String(a);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "RD Information:");
                share.putExtra(Intent.EXTRA_TEXT, contentShare);
                startActivity(Intent.createChooser(share, "Share via"));



            }
        });
        rdButtonStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

              animationActivity.animation(v);
                double interestPercentValue  = ParseDouble(rdRateOfInterestValue.getText().toString());
                int  tenureValue = (int)ParseDouble(rdTenureValue.getText().toString());
                inputAmount = ParseDouble(String.valueOf(rdInputAmountValue.getText()));

                if(inputAmount >0 && interestPercentValue >0 && tenureValue > 0) {

                    if (interestPercentValue <= 50) {
                        if (status) {

                            if(tenureValue <= 40)
                            {
                                rdCalculation();
                                Intent intent = new Intent(v.getContext(), RdSatatisticsActivity.class);
                                intent.putExtra("rdDataset", rddataDataset);
                                v.getContext().startActivity(intent);
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                } else {
                                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                                }
                            }
                            else
                            {
                                Toast.makeText(RdCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();
                            }

                        }else
                        {
                          if(tenureValue <= 480)
                          {
                              rdCalculation();
                              Intent intent = new Intent(v.getContext(), RdSatatisticsActivity.class);
                              intent.putExtra("rdDataset", rddataDataset);
                              v.getContext().startActivity(intent);
                              if (mInterstitialAd.isLoaded()) {
                                  mInterstitialAd.show();
                              } else {
                                  Log.d("TAG", "The interstitial wasn't loaded yet.");
                              }

                          }else
                          {
                              Toast.makeText(RdCalculatorActivity.this, messageComment.messagePeroidMonth, Toast.LENGTH_SHORT).show();

                          }


                        }
                    }
                    else
                    {
                        Toast.makeText(RdCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(RdCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }



            }
        });

        rdButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareButton.setVisibility(View.VISIBLE);

                double interestPercentValue  = ParseDouble(rdRateOfInterestValue.getText().toString());
                int  tenureValue = (int)ParseDouble(rdTenureValue.getText().toString());
                inputAmount = ParseDouble(String.valueOf(rdInputAmountValue.getText()));
                if(inputAmount >0 && interestPercentValue >0 && tenureValue > 0) {

                    animationActivity.animation(v);
                    if (interestPercentValue <= 50) {
                        if (status) {
                           if( tenureValue <= 40) {

                               rdCalculation();
                               rdInvestmentAmount.setText(decimal.format(amount - totalInterest));
                               rdMaturityValue.setText(String.valueOf(decimal.format(amount)));
                               rdTotalInterest.setText(String.valueOf(decimal.format(totalInterest)));
                           }else
                           {
                               Toast.makeText(RdCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();
                           }


                        } else

                            {
                                if( tenureValue <= 480)
                                {
                                    rdCalculation();
                                    rdInvestmentAmount.setText(decimal.format(amount - totalInterest));
                                    rdMaturityValue.setText(String.valueOf(decimal.format(amount)));
                                    rdTotalInterest.setText(String.valueOf(decimal.format(totalInterest)));

                                }else
                                {

                                    Toast.makeText(RdCalculatorActivity.this, messageComment.messagePeroidMonth, Toast.LENGTH_SHORT).show();

                                }

                            }

                    } else

                        {
                        Toast.makeText(RdCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();
                        }
                }

                else {
                    Toast.makeText(RdCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();
                }
            }
        });

        rdRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioid = rdRadioGroup.getCheckedRadioButtonId();
                rdRadioButton = findViewById(radioid);
                status = false;

                if (rdRadioButton.isChecked()) {

                  selectedTenureMode = (String) rdRadioButton.getText();

                    status = selectedTenureMode.equals("Year");

                }

            }
        });


        rdButtonGetDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getDate.getdate(rdSelectDate,datePicker, RdCalculatorActivity.this);
            }
        });

    }

    private void rdCalculation()
    {
        rdDateOfMaturity.setVisibility(View.VISIBLE);
        rdCalculatedInvestDate.setVisibility(View.VISIBLE);
        Double interestPercent = ParseDouble(rdRateOfInterestValue.getText().toString());
        inputAmount = ParseDouble(String.valueOf(rdInputAmountValue.getText()));
        interestPercent =   interestPercent /100/12;

        int tenure = (int)ParseDouble(rdTenureValue.getText().toString());
        maturityDateCalculation.caculateMaturityDate(status,tenure,rdDateOfInvestment,rdDateOfMaturity,rdCalculatedInvestDate);
        String rdDate = (String) rdSelectDate.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(Objects.requireNonNull(sdf.parse(rdDate)));

        } catch (ParseException e) {

            e.printStackTrace();
        }

        if(status)
        {
            tenure = tenure * 12;
        }

        interestPayout = 0.0;
        totalInterest = 0.0;
        capitalizedInterest = 0.0;

        amount = 0.0;

        rddataDataset = new ArrayList<HashMap<String, String>>();
        for (int i=1; i<=tenure; i++)
        {
            amount+=inputAmount;
            interestPayout = amount*interestPercent;
            totalInterest +=interestPayout;
            capitalizedInterest+=interestPayout;
            c.add(Calendar.MONTH, 1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf1.format(c.getTime());
                if (i%3 == 0)
                {
                    amount +=capitalizedInterest;
                    rdmap = new HashMap<>();
                    rdmap.put("Date", date);
                    rdmap.put("interest Amount", String.valueOf(decimal.format(interestPayout)));
                    rdmap.put("captilzedInterest", String.valueOf(decimal.format(capitalizedInterest)));
                    rdmap.put("RdBalance", String.valueOf(decimal.format(amount)));
                    rddataDataset.add(rdmap);
                    capitalizedInterest=0.0;
                }
                else
                {
                    rdmap = new HashMap<>();
                    int defaultCapInterest = 0;
                    rdmap.put("Date", date);
                    rdmap.put("interest Amount", String.valueOf(decimal.format(interestPayout)));
                    rdmap.put("captilzedInterest", String.valueOf(defaultCapInterest));
                    rdmap.put("RdBalance", String.valueOf(decimal.format(amount)));
                    rddataDataset.add(rdmap);
                }

        }
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
        rdInputAmountValue.setText("");
        rdRateOfInterestValue.setText("");
        rdTenureValue.setText("");
        rdInvestmentAmount.setText("0");
        rdMaturityValue.setText("0");
        rdTotalInterest.setText("0");

        rdDateOfMaturity.setVisibility(View.INVISIBLE);
        rdCalculatedInvestDate.setVisibility(View.INVISIBLE);

    }

}
