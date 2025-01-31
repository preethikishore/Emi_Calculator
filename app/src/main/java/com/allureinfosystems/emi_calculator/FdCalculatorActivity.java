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
import java.util.Objects;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
public class FdCalculatorActivity extends AppCompatActivity {


    private Spinner spinnerCompoundingFrequency;
    private DatePickerDialog datePickerDialog;
    private TextView selectDate;
    private EditText inputDepositAmount;
    private EditText fdRateOfInterest;
    private EditText fdTermYear;
    private EditText fdTermMonth;
    private EditText fdTermDay;
    private TextView fdMaturityAmount;
    private TextView fdInvestmentAmount;
    private TextView fdTotalInterest;
    private TextView fdInvestmentDateValue;
    private TextView fdMaturityDate;
    private GetDate getdate = new GetDate();
    private SpinnerData spinnerData = new SpinnerData();
    private Button shareFDButton;
    private DecimalFormat decimal = new DecimalFormat("####0.0");
    private ArrayList<HashMap<String, String>> fddataDataset;
    private HashMap<String, String> fdmap;
    private ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    private DrawerLayout drawer;
    private String depositInterval;
    private Double tenure;
    private MessageComment messageComment = new MessageComment();
    private InterstitialAd mInterstitialAd;

    private String[] itemList = new String[]{
            "Cumulative", "Quarterly Payout", "Monthly Payout", "Short Term"
    };
    private int tenureValue;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fd_calculator);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Addind advertisement Start

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8564435465482275/3880292929");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // Advertisement end

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inputDepositAmount = findViewById(R.id.fdInputAmountValue);
        fdRateOfInterest = findViewById(R.id.fdRateOfInterestValue);
        fdTermYear = findViewById(R.id.fdTenureYear);
        fdTermDay = findViewById(R.id.fdTenureDay);
        fdTermMonth = findViewById(R.id.fdTenureMonth);
        fdInvestmentAmount = findViewById(R.id.fdInvestmentAmount);
        fdMaturityAmount = findViewById(R.id.fdMaturityValue);
        selectDate = findViewById(R.id.fdInvestDate);
        fdTotalInterest = findViewById(R.id.fdTotalInerest);
        fdInvestmentDateValue = findViewById(R.id.fdCalculatedInvestmentDate);
        fdMaturityDate = findViewById(R.id.fdMaturityDate);
        spinnerCompoundingFrequency = findViewById(R.id.fdDepositFrequency);
        Button buttonGet = findViewById(R.id.fdButtonGetDate);
        Button fdButtonCalculate = findViewById(R.id.fdButtonCalculate);
        Button fdButtonStatistics = findViewById(R.id.fdButtonStatistics);
        drawer = findViewById(R.id.fd_drawer_layout);
        shareFDButton = findViewById(R.id.fdShareResult);
        shareFDButton.setVisibility(View.INVISIBLE);
        Button fdReset = findViewById(R.id.fdButtonReset);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        selectDate.setText(currentDate);

        NavigationView navigationView = findViewById(R.id.fd_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(FdCalculatorActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(FdCalculatorActivity.this, SettingsMenu.class));
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

        shareFDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ShareInputAmount = String.valueOf(inputDepositAmount.getText());
                String ShareRateOfInterest = String.valueOf(fdRateOfInterest.getText());
                String ShareTenureInput = (int)(ParseDouble(fdTermYear.getText().toString())) + "-Year/" + (int)ParseDouble(fdTermMonth.getText().toString())+"-Months/" + (int) ParseDouble(fdTermDay.getText().toString())+"-Days";
                String ShareMaturity = String.valueOf(fdMaturityAmount.getText());
                String ShareTotalInterest = String.valueOf(fdTotalInterest.getText());
                String ShareDepositMode = depositInterval;
                String ShareInvestDate = String.valueOf(fdInvestmentDateValue.getText());
                String ShareMaturityDate = String.valueOf(fdMaturityDate.getText());
                String ShareStatus;
                String a = "FD Details  :" + "\n\n" + "Input Amount : " + ShareInputAmount + " \n" +
                        "Interest Rate : " + ShareRateOfInterest + "%" + " \n" +
                        "Tenure Value : " + ShareTenureInput +  "\n" +
                        "Deposit Mode : " + ShareDepositMode + " \n" + "\n" + "\n" +
                        "Maturity Amount : " + ShareMaturity + "\n" +
                        "Total Interest Value : " + ShareTotalInterest + "\n" + "\n" +
                        "Investment Date :" + ShareInvestDate + "\n" +
                        "Maturity Date :" + ShareMaturityDate;

                String contentShare = new String(a);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "FD Information:");
                share.putExtra(Intent.EXTRA_TEXT, contentShare);
                startActivity(Intent.createChooser(share, "Share via"));

            }
        });

        fdButtonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Double   inputInterestAmount = ParseDouble(String.valueOf(fdRateOfInterest.getText()));
                animationActivity.animation(v);
                double inputAmountValue = ParseDouble(String.valueOf(inputDepositAmount.getText()));
                double inputInterestAmountValue = ParseDouble(String.valueOf(fdRateOfInterest.getText()));
                int fdYearValue = (int) ParseDouble(fdTermYear.getText().toString());
                int  fdMonthValue = (int)  ParseDouble(fdTermMonth.getText().toString());
                int  fdDayValue =  (int)ParseDouble(fdTermDay.getText().toString());
                tenureValue = fdYearValue * 12+fdMonthValue+fdDayValue/ 365 * 12;


                if(inputAmountValue >0 && inputInterestAmountValue>0 && tenureValue>0)
                {
                    if(inputInterestAmountValue <= 50) {

                        if((fdYearValue<=40) ) {
                            if(fdMonthValue <=480) {
                                if( fdDayValue <=14600) {
                                    if(tenureValue <= 480)
                                    {
                                        fdCalculation();
                                        Intent intent = new Intent(v.getContext(), FdStatisticsActivity.class);
                                        intent.putExtra("fdDataset", fddataDataset);
                                        v.getContext().startActivity(intent);
                                        if (mInterstitialAd.isLoaded()) {
                                            mInterstitialAd.show();
                                        } else {
                                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                                        }
                                    }else
                                    {
                                        Toast.makeText(FdCalculatorActivity.this, "Maximum Tenure Value 40 Years", Toast.LENGTH_SHORT).show();

                                    }


                                }
                                else
                                {
                                    Toast.makeText(FdCalculatorActivity.this, messageComment.messageDayComment, Toast.LENGTH_SHORT).show();

                                }
                            }
                            else
                            {
                                Toast.makeText(FdCalculatorActivity.this, messageComment.messagePeroidMonth, Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            Toast.makeText(FdCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();

                        }

                    }else
                    {
                        Toast.makeText(FdCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                    }
                }else

                {
                    Toast.makeText(FdCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }

            }
        });


       fdReset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               animationActivity.animation(v);
               clear();
           }
       });



        fdButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                shareFDButton.setVisibility(View.VISIBLE);
                double inputAmountValue = ParseDouble(String.valueOf(inputDepositAmount.getText()));
                double inputInterestAmountValue = ParseDouble(String.valueOf(fdRateOfInterest.getText()));
                int fdYearValue = (int) ParseDouble(fdTermYear.getText().toString());
                int  fdMonthValue = (int)  ParseDouble(fdTermMonth.getText().toString());
                int  fdDayValue =  (int)ParseDouble(fdTermDay.getText().toString());
                int tenureValue = fdYearValue * 12+fdMonthValue+fdDayValue/ 365 * 12;


                if(inputAmountValue >0 && inputInterestAmountValue>0 && tenureValue>0)
                {
                if(inputInterestAmountValue <= 50) {

                    if((fdYearValue<=40) ) {
                        if(fdMonthValue <=480) {
                            if( fdDayValue <=14600) {
                                if(tenureValue <= 480)
                                {

                                    fdCalculation();
                                }else
                                {
                                    Toast.makeText(FdCalculatorActivity.this, "Maximum Tenure Value 40 Years", Toast.LENGTH_SHORT).show();

                                }


                            }
                            else
                            {
                                Toast.makeText(FdCalculatorActivity.this, messageComment.messageDayComment, Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            Toast.makeText(FdCalculatorActivity.this, messageComment.messagePeroidMonth, Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(FdCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();

                    }

                }else
                {
                    Toast.makeText(FdCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                }
            }else

                {
                    Toast.makeText(FdCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }
            }

        });


        buttonGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                getdate.getdate(selectDate, datePickerDialog, FdCalculatorActivity.this);
            }
        });


        spinnerData.initspinnerfooter(spinnerCompoundingFrequency, FdCalculatorActivity.this, Arrays.asList(itemList));

    }


    private void fdCalculation() {
        Double inputAmount = ParseDouble(String.valueOf(inputDepositAmount.getText()));
        double interestPercent =  ParseDouble(fdRateOfInterest.getText().toString());

        int fdYear = (int) ParseDouble(fdTermYear.getText().toString());
        int  fdMonth = (int)  ParseDouble(fdTermMonth.getText().toString());
        int  fdDay =  (int)ParseDouble(fdTermDay.getText().toString());

        interestPercent = interestPercent / 100 ;

        tenure = Double.valueOf(fdYear * 12 + fdMonth +fdDay / 365 * 12);

        depositInterval = spinnerCompoundingFrequency.getSelectedItem().toString();



        String investDate = (String) selectDate.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(Objects.requireNonNull(sdf.parse(investDate)));

        } catch (ParseException e) {

            e.printStackTrace();
        }
        c.add(Calendar.YEAR, fdYear);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
        String maturity = sdf1.format(c.getTime());
        c.add(Calendar.MONTH, fdMonth);
        maturity = sdf1.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, fdDay);
        maturity = sdf1.format(c.getTime());

        Double i = tenure;
        Double interestPayout = 0.0;
        Double totalInterest = 0.0;
        Double capitalizedInterest = 0.0;
        int quarterCounter = 0;
        Double amount = 0.0;

        amount += inputAmount;
        fddataDataset = new ArrayList<HashMap<String, String>>();



        do {
            c.add(Calendar.MONTH, 1);
            String date = sdf1.format(c.getTime());

            if (i > 1)
                interestPayout = amount * (interestPercent / 12);
            else
                interestPayout = amount * Math.pow((1 + interestPercent / 12), i) - amount;

            if (depositInterval.equals("Cumulative")) {
                quarterCounter++;
                capitalizedInterest += interestPayout;

                if (quarterCounter == 3) {
                    amount += capitalizedInterest;
                    fdmap = new HashMap<>();
                    fdmap.put("fdDate", date);
                    fdmap.put("fdInterestAmount", String.valueOf(decimal.format(interestPayout)));
                    fdmap.put("fdCaptilzedInterest", String.valueOf(decimal.format(capitalizedInterest)));
                    fdmap.put("fdBalance", String.valueOf(decimal.format(amount)));
                    fddataDataset.add(fdmap);
                    capitalizedInterest = 0.0;
                    quarterCounter = 0;
                } else {

                    if (i > 1) {
                        amount += 0;
                        fdmap = new HashMap<>();
                        int defaultCapInterest = 0;
                        fdmap.put("fdDate", date);
                        fdmap.put("fdInterestAmount", String.valueOf(decimal.format(interestPayout)));
                        fdmap.put("fdCaptilzedInterest", String.valueOf(defaultCapInterest));
                        fdmap.put("fdBalance", String.valueOf(decimal.format(amount)));
                        fddataDataset.add(fdmap);
                    }
                    else {
                        amount += capitalizedInterest;
                        fdmap = new HashMap<>();
                        fdmap.put("fdDate", date);
                        fdmap.put("fdInterestAmount", String.valueOf(decimal.format(interestPayout)));
                        fdmap.put("fdCaptilzedInterest", String.valueOf(decimal.format(capitalizedInterest)));
                        fdmap.put("fdBalance", String.valueOf(decimal.format(amount)));
                        fddataDataset.add(fdmap);
                        capitalizedInterest = 0.0;
                        quarterCounter = 0;
                    }
                }

            }
            else if (depositInterval.equals("Quarterly Payout")) {
                quarterCounter++;
                capitalizedInterest += interestPayout;

                if (quarterCounter == 3) {
                    fdmap = new HashMap<>();
                    fdmap.put("fdDate", date);
                    fdmap.put("fdInterestAmount", String.valueOf(decimal.format(interestPayout)));
                    fdmap.put("fdCaptilzedInterest", String.valueOf(decimal.format(capitalizedInterest)));
                    fdmap.put("fdBalance", String.valueOf(decimal.format(amount)));
                    fddataDataset.add(fdmap);
                    capitalizedInterest = 0.0;
                    quarterCounter = 0;
                } else {

                    if (i > 1) {
                        amount += 0;
                        fdmap = new HashMap<>();
                        int defaultCapInterest = 0;
                        fdmap.put("fdDate", date);
                        fdmap.put("fdInterestAmount", String.valueOf(decimal.format(interestPayout)));
                        fdmap.put("fdCaptilzedInterest", String.valueOf(defaultCapInterest));
                        fdmap.put("fdBalance", String.valueOf(decimal.format(amount)));
                        fddataDataset.add(fdmap);
                    }
                    else {
                        fdmap = new HashMap<>();
                        fdmap.put("fdDate", date);
                        fdmap.put("fdInterestAmount", String.valueOf(decimal.format(interestPayout)));
                        fdmap.put("fdCaptilzedInterest", String.valueOf(decimal.format(capitalizedInterest)));
                        fdmap.put("fdBalance", String.valueOf(decimal.format(amount)));
                        fddataDataset.add(fdmap);
                        capitalizedInterest = 0.0;
                        quarterCounter = 0;
                    }
                }

            }
            else
            {
                fdmap = new HashMap<>();
                fdmap.put("fdDate", date);
                fdmap.put("fdInterestAmount", String.valueOf(decimal.format(interestPayout)));
                fdmap.put("fdCaptilzedInterest", String.valueOf(decimal.format(interestPayout)));
                fdmap.put("fdBalance", String.valueOf(decimal.format(amount)));
                fddataDataset.add(fdmap);
            }
            totalInterest += interestPayout;

            i--;
        } while (i > 0);

        fdMaturityDate.setVisibility(View.VISIBLE);
        fdInvestmentDateValue.setVisibility(View.VISIBLE);
        fdInvestmentAmount.setText(String.valueOf(inputAmount));
        fdMaturityAmount.setText(String.valueOf(decimal.format(amount)));
        fdTotalInterest.setText(String.valueOf(decimal.format(totalInterest)));
        fdMaturityDate.setText(maturity);
        fdInvestmentDateValue.setText(investDate);


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

    private void clear() {
        inputDepositAmount.setText("");
        fdRateOfInterest.setText("");
        fdTermYear.setText("");
        fdTermMonth.setText("");
        fdTermDay.setText("");
        fdInvestmentAmount.setText("0");
        fdMaturityAmount.setText("0");
        fdTotalInterest.setText("0");

        fdMaturityDate.setVisibility(View.INVISIBLE);
        fdInvestmentDateValue.setVisibility(View.INVISIBLE);

    }

}
