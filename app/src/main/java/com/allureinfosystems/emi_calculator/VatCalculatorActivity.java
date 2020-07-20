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

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class VatCalculatorActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button Calculate;
    private Spinner vatSpinnerData;
    private EditText amountText;
    private EditText vatOtherText;
    private TextView orginalCost;
    private TextView vatPriceText;
    private double vatPrice;
    private double netPrice;
    private String selectedOption;
    private double selectedvalue = 25.0;
    private boolean status = false;
    private TextView netPriceValue;
    private DrawerLayout drawer;
    private Button shareButton;
    private Button vatReset;
    private MessageComment messageComment = new MessageComment();
    private ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    private final DecimalFormat df = new DecimalFormat("####0.0");

    private String[] items = new String[]{
            "ADD GST","REMOVE GST"
    };
    private SpinnerData spinnerData = new SpinnerData();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_calculator);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        vatReset = findViewById(R.id.vatResetButton);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.vat_drawer_layout);

        NavigationView navigationView = findViewById(R.id.vat_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(VatCalculatorActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(VatCalculatorActivity.this, SettingsMenu.class));
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


        amountText = findViewById(R.id.vat_calculation_amount_value);
        vatOtherText = findViewById(R.id.vat_radio_Others_text);
        orginalCost = findViewById(R.id.vat_orginal_cost_value);
        vatPriceText = findViewById(R.id.vat_price_value);
        netPriceValue = findViewById(R.id.vat_net_price_value);
        radioGroup = (RadioGroup) findViewById(R.id.vatRadioButtonGroup);
        Calculate = findViewById(R.id.vat_button_Interest_Calc_Calculate);
        shareButton = findViewById(R.id.vat_share_result);
        shareButton.setVisibility(View.INVISIBLE);

        vatSpinnerData = findViewById(R.id.add_vat);
        spinnerData.initspinnerfooter(vatSpinnerData, VatCalculatorActivity.this, Arrays.asList(items));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioid = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioid);


                if (radioButton.isChecked()) {
                    selectedOption = (String) radioButton.getText();

                    if (selectedOption.equals("Others")) {
                        vatOtherText.setVisibility(View.VISIBLE);

                    } else {
                        vatOtherText.setVisibility(View.INVISIBLE);
                    }

                    switch (selectedOption) {
                        case "5%":
                            selectedvalue = 5.0;
                            status = false;
                            break;
                        case "10%":
                            selectedvalue = 10.0;
                            status = false;
                            break;
                        case "15%":
                            selectedvalue = 15.0;
                            status = false;
                            break;
                        case "20%":
                            selectedvalue = 20.0;
                            status = false;
                            break;
                        case "25%":
                            selectedvalue = 25.0;
                            status = false;
                            break;
                        case "Others":
                            status = true;
                            break;
                    }
                }

            }
        });

        vatReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                shareButton.setVisibility(View.INVISIBLE);
                clear();
            }
        });

        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                shareButton.setVisibility(View.VISIBLE);
                    vat_calculate();


            }
        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ShareInputAmount = String.valueOf(amountText.getText());
                String ShareRateOfInterest = String.valueOf(selectedvalue);
                String ShareMaturity = String.valueOf(netPrice);
                String ShareVatRate = String.valueOf(vatPrice);


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


    private void vat_calculate()
    {

        String selected_mode =  vatSpinnerData.getSelectedItem().toString();
        double inputAmount = ParseDouble(String.valueOf(amountText.getText()));
        double vatOtherValue =  ParseDouble(String.valueOf(vatOtherText.getText()));

    if(inputAmount > 0) {
        if (selected_mode.equals("ADD GST")) {

            if (status) {
                if(vatOtherValue > 0) {

                    selectedvalue = ParseDouble((String.valueOf(vatOtherText.getText())));
                    vatPrice = inputAmount * selectedvalue / 100;
                    netPrice = inputAmount + vatPrice;
                }
                else
                {
                    Toast.makeText(VatCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }

            } else {
                vatPrice = inputAmount * selectedvalue / 100;
                netPrice = inputAmount + vatPrice;
            }

        } else {


            if (status) {
                if(vatOtherValue > 0) {
                    selectedvalue = ParseDouble((String.valueOf(vatOtherText.getText())));

                    netPrice = inputAmount;
                    vatPrice = netPrice - (netPrice/(1+selectedvalue/100));
                    inputAmount = netPrice/(1+selectedvalue/100);

                }
                else
                {
                    Toast.makeText(VatCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }
            } else {

                netPrice = inputAmount;
                vatPrice = netPrice - (netPrice/(1+selectedvalue/100));
                inputAmount = netPrice/(1+selectedvalue/100);

            }

        }
        orginalCost.setText(df.format(inputAmount));
        netPriceValue.setText(df.format(netPrice));
        vatPriceText.setText(df.format(vatPrice));
    }
    else
    {
        Toast.makeText(VatCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

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
    private void clear() {
        amountText.setText("");
        vatOtherText.setText("");
        orginalCost.setText("0");
        netPriceValue.setText("0");
        vatPriceText.setText("0");

    }

}
