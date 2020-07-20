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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class EmiCalculatorActivity extends AppCompatActivity {

    private TextView emiMonthly;
    private TextView emiTotalPaymentText;
    private EditText principle;
    private EditText interest;
    private EditText term;
    private Button shareButton;
    private double emi;
    private double n;
    private double r;
    private ArrayList<HashMap<String, String>> emidataDataset;
    private HashMap<String, String> map;
    private InterstitialAd mInterstitialAd;

    private DecimalFormat df = new DecimalFormat("####0.0");
    private Double interestSum = 0.0;
    private Double emiTotalPayment  = 0.0;
    private CommonFuns commonFuns = new CommonFuns();
    private ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    private DrawerLayout drawer;
    private MessageComment messageComment = new MessageComment();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Addind advertisement Start

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8564435465482275/3880292929");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // Advertisement end


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button buttonStatistices = findViewById(R.id.buttonStatistics);

        principle = findViewById(R.id.emi_edit_principle);
        interest = findViewById(R.id.emi_edit_interest);
        term = findViewById(R.id.emi_edit_year);
        Button emiCalculate = findViewById(R.id.emi_buttonCalculate);
        emiMonthly = findViewById(R.id.textViewMonthllyemi);

        emiTotalPaymentText = findViewById(R.id.emiTotalPayValue);
        shareButton = findViewById(R.id.emi_share_result);
        Button resetButton = findViewById(R.id.emiButtonReset);
        shareButton.setVisibility(View.INVISIBLE);
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

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                clear();
            }
        });

        emiCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double interestVaue = ParseDouble(String.valueOf(interest.getText()));
                double termValue = ParseDouble(String.valueOf(term.getText()));
                double principleAmount = ParseDouble(String.valueOf(principle.getText()));
                animationActivity.animation(v);
                shareButton.setVisibility(View.VISIBLE);
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


                Double interestVaule = ParseDouble(String.valueOf(interest.getText()));
                Double   termValue = ParseDouble(String.valueOf(term.getText()));
                Double principleAmount = ParseDouble(String.valueOf(principle.getText()));
                if( principleAmount >0 && interestVaule>0 && termValue >0 ) {

                    if (interestVaule <= 50) {


                        if (termValue <= 40) {
                            statsCalc();
                            Intent intent = new Intent(EmiCalculatorActivity.this, emiDetailActivity.class);
                            intent.putExtra("emiDataset", emidataDataset);
                            intent.putExtra("inerestSum", interestSum);
                            intent.putExtra("emiTotalPayText", emiTotalPayment);
                            intent.putExtra("emiPrinciple", principleAmount);
                            intent.putExtra("emiInterest", interestVaule);
                            intent.putExtra("emiperiod", termValue);
                            intent.putExtra("emiValue", emi);
                            v.getContext().startActivity(intent);
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }

                        } else {
                            Toast.makeText(EmiCalculatorActivity.this, messageComment.messageYearComment, Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(EmiCalculatorActivity.this, messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(EmiCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();


                }


            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String monthlyAmount = String.valueOf(emiMonthly.getText());
                String ShareInputAmount = String.valueOf(principle.getText());
                String ShareRateOfInterest = String.valueOf(interest.getText());
                String ShareTenureInput = String.valueOf(term.getText());
                String ShareTotalPay = String.valueOf(emiTotalPaymentText.getText());



                String a = "PPF Details  :" + "\n\n" + "Input Amount : " + ShareInputAmount + " \n" +
                        "Interest Rate : " + ShareRateOfInterest + "%" + " \n" +
                        "Period : " + ShareTenureInput +"- Years"+  "\n" +
                        "Emi Amount : " + monthlyAmount + "\n" +
                        "Total Payment Value : " + ShareTotalPay + "\n" + "\n"
                       ;

                String contentShare = new String(a);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "PPF Information:");
                share.putExtra(Intent.EXTRA_TEXT, contentShare);
                startActivity(Intent.createChooser(share, "Share via"));

            }
        });


    }



    private void statsCalc() {
        emiTotalPayment = 0.0;
        interestSum = 0.0;
        Double principleAmount = ParseDouble(String.valueOf(principle.getText()));
        double interestRate = ParseDouble(String.valueOf(interest.getText()));
        double tenureValue = ParseDouble(String.valueOf(term.getText()));
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

    private double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
    private void clear() {
        principle.setText("");
        interest.setText("");
        term.setText("");
        emiMonthly.setText("0");
        emiTotalPaymentText.setText("0");

    }


}
