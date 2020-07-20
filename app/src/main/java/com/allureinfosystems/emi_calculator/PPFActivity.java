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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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

public class PPFActivity extends AppCompatActivity {

    private Spinner ppfSpinnerMaturity;
    private Spinner ppfSpinnerDepositMode;
    private DatePickerDialog picker;
    private TextView ppfSelectDate;
    private EditText ppfinputAmount;
    private EditText ppfRateOfInterestValue;
    private TextView ppfMaturityDate;
    private TextView ppfInvestDate;
    private TextView ppfInvestAmount;
    private TextView ppftotalInterestValue;
    private TextView ppfMaturityValue;
    private Button ppfshareResult;
    private ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    private String inputMode;
    private int tenure;
    private Double interestPayout ;
    private Double totalInterest  ;
    private Double capitalizedInterest;
    private Double inputAmount ;
    private Double amount ;
    private Double totalAmountPay;
    private int yearStatus = 0;
    private InterstitialAd mInterstitialAd;
    private String deposit_interval;
    private GetDate getdate = new GetDate();
    private SpinnerData spinnerData = new SpinnerData();
    private DecimalFormat decimal = new DecimalFormat("####0.0");
    private ArrayList<HashMap<String, String>> ppfDataset;
    private HashMap<String, String> ppfmap;
    private MaturityDateCalculation maturityDateCalculation = new MaturityDateCalculation();
    private DrawerLayout drawer;

    private String[] items = new String[]{
            "15 Years",
            "20 Years",
            "25 Years",
            "30 Years",

    };

    private String[] modes = new String[]{
            "Fixed Yearly Amount",
            "Fixed Monthly Amount"
    };


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_p_f);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawer = findViewById(R.id.ppf_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.ppf_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(PPFActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(PPFActivity.this, SettingsMenu.class));
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
        Button ppfReset = findViewById(R.id.ppfReset);
        ppfSpinnerMaturity = findViewById(R.id.ppfMaturityDuration);
        ppfSpinnerDepositMode = findViewById(R.id.ppfSpinnerDepositMode);
        ppfSelectDate = findViewById(R.id.ppf_date_of_investment_value);
        Button ppfButtonGet = findViewById(R.id.ppfButtonGetDate);
        ppfinputAmount = findViewById(R.id.ppfDepositAmountValue);
        ppfRateOfInterestValue = findViewById(R.id.ppfInterestValue);
        ppfInvestDate = findViewById(R.id.ppfInvestDate);
        ppfMaturityDate = findViewById(R.id.ppfMaturityDate);
        Button ppfButtonStstsics = findViewById(R.id.ppfCalcStatistics);
        ppfInvestAmount = findViewById(R.id.ppfInvestAmount);
        ppftotalInterestValue = findViewById(R.id.ppfTotalInterestValue);
        ppfMaturityValue = findViewById(R.id.ppfMaturityValue);
        Button ppfCalculate = findViewById(R.id.ppfCalculation);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        ppfSelectDate.setText(currentDate);
        ppfshareResult = findViewById(R.id.ppf_share_result);
        ppfshareResult.setVisibility(View.INVISIBLE);

        final MessageComment messageComment = new MessageComment();
        ppfCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                animationActivity.animation(v);
                ppfshareResult.setVisibility(View.VISIBLE);

                ppfInvestDate.setVisibility(View.VISIBLE);
                ppfMaturityDate.setVisibility(View.VISIBLE);
                double inputAmountValue = ParseDouble(String.valueOf(ppfinputAmount.getText()));
                double interestPercentValue = ParseDouble(ppfRateOfInterestValue.getText().toString());

                if(inputAmountValue >0 && interestPercentValue >0) {
                    if(interestPercentValue<=50) {

                        ppfCalculation();

                    }
                    else
                    {
                        Toast.makeText(PPFActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                    }
                }else
                {

                    Toast.makeText(PPFActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }

            }
        });

        ppfReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                ppfshareResult.setVisibility(View.INVISIBLE);

                clear(v);
            }
        });

        ppfshareResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maturityAmount = String.valueOf(ppfMaturityValue.getText());
                String ShareInputAmount = String.valueOf(ppfinputAmount.getText());
                String ShareRateOfInterest = String.valueOf(ppfRateOfInterestValue.getText());
                String ShareTenureInput = String.valueOf(deposit_interval);
                String ShareTotalInterest = String.valueOf(ppftotalInterestValue.getText());
                String ShareDepositMode = inputMode;
                String ShareInvestDate = String.valueOf(ppfInvestDate.getText());
                String ShareMaturityDate = String.valueOf(ppfMaturityDate.getText());
                String ShareStatus;

                String a = "PPF Details  :" + "\n\n" + "Input Amount : " + ShareInputAmount + " \n" +
                        "Interest Rate : " + ShareRateOfInterest + "%" + " \n" +
                        "Tenure Value : " + ShareTenureInput +  "\n" +
                        "Deposit Mode : " + ShareDepositMode + " \n" + "\n" + "\n" +
                        "Maturity Amount : " + maturityAmount + "\n" +
                        "Total Interest Value : " + ShareTotalInterest + "\n" + "\n" +
                        "Investment Date :" + ShareInvestDate + "\n" +
                        "Maturity Date :" + ShareMaturityDate;

                String contentShare = new String(a);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "PPF Information:");
                share.putExtra(Intent.EXTRA_TEXT, contentShare);
                startActivity(Intent.createChooser(share, "Share via"));



            }
        });

        ppfButtonStstsics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                animationActivity.animation(v);

                double inputAmountValue = ParseDouble(String.valueOf(ppfinputAmount.getText()));
                double interestPercentValue = ParseDouble(ppfRateOfInterestValue.getText().toString());

              if(inputAmountValue >0 && interestPercentValue >0) {
                  if(interestPercentValue<=50) {

                      ppfCalculation();
                      Intent intent = new Intent(v.getContext(), ppfStatisticsActivity.class);
                      intent.putExtra("ppfDataset", ppfDataset);
                      v.getContext().startActivity(intent);

                      if (mInterstitialAd.isLoaded()) {
                          mInterstitialAd.show();
                      } else {
                          Log.d("TAG", "The interstitial wasn't loaded yet.");
                      }
                  }
                  else
                  {
                      Toast.makeText(PPFActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                  }
              }else
              {

                  Toast.makeText(PPFActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

              }
            }
        });


        ppfButtonGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getdate.getdate(ppfSelectDate,picker, PPFActivity.this);
            }
        });

        spinnerData.initspinnerfooter(ppfSpinnerMaturity,PPFActivity.this, Arrays.asList(items));
        spinnerData.initspinnerfooter(ppfSpinnerDepositMode,PPFActivity.this, Arrays.asList(modes));

    }

    private void ppfCalculation()
    {

        deposit_interval =  ppfSpinnerMaturity.getSelectedItem().toString();

        if(deposit_interval.equals("15 Years"))
        {
            tenure = 15;
        }else if(deposit_interval.equals("20 Years"))
        {
            tenure = 20;
        }else if(deposit_interval.equals("25 Years"))
        {
            tenure = 25;
        }else if(deposit_interval.equals("30 Years"))
        {
            tenure = 30;
        }

//        switch(deposit_interval)
//        {
//            case "15 Years":
//                tenure = 15;
//              break;
//            case "20 Years":
//                tenure = 20;
//                break;
//            case "25 Years":
//                tenure = 25;
//                break;
//            case "30 Years":
//                tenure = 30;
//                break;
//        }
        inputMode =  ppfSpinnerDepositMode.getSelectedItem().toString();
        Double interestPercent = ParseDouble(ppfRateOfInterestValue.getText().toString());

        //interestPercent = (Double.parseDouble(ppfRateOfInterestValue.getText().toString()) / 100) / 12 ;
        interestPercent = interestPercent/100/12;

        boolean status = true;
        maturityDateCalculation.caculateMaturityDate(status,tenure,ppfSelectDate,ppfMaturityDate,ppfInvestDate);
        String ppfDate = (String)  ppfSelectDate.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(Objects.requireNonNull(sdf.parse(ppfDate)));

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
        inputAmount = ParseDouble(String.valueOf(ppfinputAmount.getText()));
        amount = 0.0;


        ppfDataset = new ArrayList<HashMap<String, String>>();

        for (int i=1; i<=tenure; i++) {



           if(inputMode.equals("Fixed Monthly Amount"))
            {
                amount += inputAmount;
                interestPayout = amount * interestPercent;
                totalInterest += interestPayout;
                capitalizedInterest += interestPayout;
                c.add(Calendar.MONTH, 1);
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf1.format(c.getTime());
                if (i % 12 == 0) {
                    amount += capitalizedInterest;
                    ppfmap = new HashMap<>();
                    ppfmap.put("Date", date);
                    ppfmap.put("interest Amount", String.valueOf(decimal.format(interestPayout)));
                    ppfmap.put("captilzedInterest", String.valueOf(decimal.format(capitalizedInterest)));
                    ppfmap.put("RdBalance", String.valueOf(decimal.format(amount)));
                    ppfDataset.add(ppfmap);
                    capitalizedInterest = 0.0;
                } else {
                    ppfmap = new HashMap<>();
                    int defaultCapInterest = 0;
                    ppfmap.put("Date", date);
                    ppfmap.put("interest Amount", String.valueOf(decimal.format(interestPayout)));
                    ppfmap.put("captilzedInterest", String.valueOf(defaultCapInterest));
                    ppfmap.put("RdBalance", String.valueOf(decimal.format(amount)));
                    ppfDataset.add(ppfmap);
                }

            }else
           {

               if(yearStatus%12== 0) {

                   amount += inputAmount;

               }
                   interestPayout = amount * interestPercent;
                   totalInterest += interestPayout;
                   capitalizedInterest += interestPayout;
                   c.add(Calendar.MONTH, 1);
                   SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                   String date = sdf1.format(c.getTime());
                   if (i % 12 == 0) {
                       amount += capitalizedInterest;
                       ppfmap = new HashMap<>();
                       ppfmap.put("Date", date);
                       ppfmap.put("interest Amount", String.valueOf(decimal.format(interestPayout)));
                       ppfmap.put("captilzedInterest", String.valueOf(decimal.format(capitalizedInterest)));
                       ppfmap.put("RdBalance", String.valueOf(decimal.format(amount)));
                       ppfDataset.add(ppfmap);
                       capitalizedInterest = 0.0;
                   } else {
                       ppfmap = new HashMap<>();
                       int defaultCapInterest = 0;
                       ppfmap.put("Date", date);
                       ppfmap.put("interest Amount", String.valueOf(decimal.format(interestPayout)));
                       ppfmap.put("captilzedInterest", String.valueOf(defaultCapInterest));
                       ppfmap.put("RdBalance", String.valueOf(decimal.format(amount)));
                       ppfDataset.add(ppfmap);

                   }

        }
            yearStatus ++;
        }

        totalAmountPay = amount - totalInterest;
        ppfInvestAmount.setText(String.valueOf(decimal.format(totalAmountPay)));
        ppftotalInterestValue.setText(String.valueOf(decimal.format(totalInterest)));
        ppfMaturityValue.setText(String.valueOf(decimal.format(amount)));



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
        ppfinputAmount.setText("");
        ppfRateOfInterestValue.setText("");
        ppfInvestAmount.setText("0");
        ppftotalInterestValue.setText("0");
        ppfMaturityValue.setText("0");

        ppfInvestDate.setVisibility(View.INVISIBLE);
        ppfMaturityDate.setVisibility(View.INVISIBLE);

    }

}
