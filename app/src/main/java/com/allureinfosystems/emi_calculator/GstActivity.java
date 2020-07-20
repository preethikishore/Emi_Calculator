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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class GstActivity extends AppCompatActivity {

    RadioGroup radio_group;
    RadioButton radio_button;
    Button Calculate;
    private EditText amount_text;
    private Spinner gstSpinnerData;
    private EditText textOthersOption;
    private TextView orginalCost;
    private TextView netGstValue;
    private TextView gstPrice;
    private double netGstCalculatedValue;
    private double inputAmount;
    private double gstValue;
    double selectedvalue =  25.0;
    String selectedOption;
    boolean status = false;
    private DrawerLayout drawer;
    private AdView mAdView;
    ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    MessageComment messageComment = new MessageComment();
    public Button shareButton;
    public Button gstReset;
    DecimalFormat df = new DecimalFormat("####0.00");

    String[] items = new String[]{
            "ADD GST","REMOVE GST"
    };
    SpinnerData spinnerData = new SpinnerData();


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.gst_drawer_layout);
        NavigationView navigationView = findViewById(R.id.gst_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(GstActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(GstActivity.this, SettingsMenu.class));
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

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        amount_text = findViewById(R.id.gst_calculation_amount_value);
        orginalCost = findViewById(R.id.gst_orginal_cost_value);
        netGstValue = findViewById(R.id.gst_net_price_value);
        gstPrice = findViewById(R.id.gst_price_value);
        radio_group = (RadioGroup)findViewById(R.id.gstradioButtonGroup);
        Calculate = findViewById(R.id.gst_button_Interest_Calc_Calculate);
        gstSpinnerData = findViewById(R.id.add_gst);
        textOthersOption = findViewById(R.id.gst_radio_Others_text);
        shareButton = findViewById(R.id.gst_share_result);
        shareButton.setVisibility(View.INVISIBLE);
        gstReset = findViewById(R.id.gstResetButton);
        gstReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                clear(v);
            }
        });

        spinnerData.initspinnerfooter(gstSpinnerData, GstActivity.this, Arrays.asList(items));

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {


            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                int radioid = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(radioid);


                if(radio_button.isChecked())
                {
                    selectedOption = (String) radio_button.getText();

                    if(selectedOption.equals("Others"))
                    {
                        textOthersOption.setVisibility(View.VISIBLE);

                    }else

                        {

                        textOthersOption.setVisibility(View.INVISIBLE);

                         }

                    switch(selectedOption)
                    {
                        case "5%": selectedvalue = 5.0;
                            status = false;
                            break;
                        case "10%": selectedvalue = 10.0;
                              status = false;
                             break;
                        case "15%": selectedvalue = 15.0;
                              status = false;
                             break;
                        case "20%": selectedvalue = 20.0;
                             status = false;
                             break;
                        case "25%": selectedvalue = 25.0;
                             status = false;
                             break;
                        case "Others":
                            status = true;
                             break;

                    }

                }

            }
        });

        Calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shareButton.setVisibility(View.VISIBLE);

                String spinnerValue = gstSpinnerData.getSelectedItem().toString();
                animationActivity.animation(v);
                inputAmount = ParseDouble(String.valueOf(amount_text.getText()));
                Double otherOptionValue = ParseDouble((String.valueOf(textOthersOption.getText())));


                if (inputAmount > 0) {

                    if (spinnerValue.equals("ADD GST")) {


                        if (status == true) {

                            if(otherOptionValue >0) {

                                selectedvalue = ParseDouble((String.valueOf(textOthersOption.getText())));
                                gstValue = inputAmount * selectedvalue / 100;
                                netGstCalculatedValue = inputAmount + gstValue;
                            }
                            else
                            {
                                Toast.makeText(GstActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            gstValue = inputAmount * selectedvalue / 100;
                            netGstCalculatedValue = inputAmount + gstValue;

                        }
                    } else {

                        if (status == true) {
                            if(otherOptionValue >0) {

                                selectedvalue = ParseDouble((String.valueOf(textOthersOption.getText())));
                                netGstCalculatedValue = inputAmount;
                                gstValue = netGstCalculatedValue - (netGstCalculatedValue/(1+selectedvalue/100));
                                inputAmount = netGstCalculatedValue/(1+selectedvalue/100);
                            }
                            else
                            {
                                Toast.makeText(GstActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            netGstCalculatedValue = inputAmount;
                            gstValue = netGstCalculatedValue - (netGstCalculatedValue/(1+selectedvalue/100));
                            inputAmount = netGstCalculatedValue/(1+selectedvalue/100);

                        }


                    }
                    orginalCost.setText(df.format(inputAmount));
                    netGstValue.setText(df.format(netGstCalculatedValue));
                    gstPrice.setText(df.format(gstValue));


                }
                else
                {

                    Toast.makeText(GstActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }
            }


        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                String ShareInputAmount = String.valueOf(amount_text.getText());
                String ShareRateOfInterest = String.valueOf(selectedvalue);
                String ShareMaturity = String.valueOf(netGstCalculatedValue);
                String ShareVatRate = String.valueOf(gstValue);


                String a = "VAT Details  :" + "\n\n" + "Input Amount : " + ShareInputAmount + " \n" +
                        "VAT Rate : " + ShareRateOfInterest + "%" + " \n" +
                        "VAT Price :" + ShareVatRate +" \n "+
                        "Net Price : " + ShareMaturity ;

                String contentShare = new String(a);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "SIP Information:");
                share.putExtra(Intent.EXTRA_TEXT, contentShare);
                startActivity(Intent.createChooser(share, "Share via"));





            }
        });

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
    public void clear(View v) {
        amount_text.setText("");
        textOthersOption.setText("");
        orginalCost.setText("0");
        netGstValue.setText("0");
        gstPrice.setText("0");

    }

}




