package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
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


    RadioGroup radioGroup;
    RadioButton radioButton;
    Button Calculate;
    private Spinner vatSpinnerData;
    private EditText amountText;
    private EditText vatOtherText;
    private TextView orginalCost;
    private TextView vatPriceText;
    private double vatPrice;
    private double netPrice;
    String selectedOption;
    double selectedvalue = 25.0;
    boolean status = false;
    private double inputAmount;
    private TextView netPriceValue;
    private DrawerLayout drawer;
    public Button shareButton;
    public Button vatReset;
    MessageComment messageComment = new MessageComment();
    ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    final DecimalFormat df = new DecimalFormat("####0.0");

    String[] items = new String[]{
            "ADD GST","REMOVE GST"
    };
    SpinnerData spinnerData = new SpinnerData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_calculator);
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

//                    if(selectedOption.equals("5%"))
//                    {
//                        selectedvalue = 5.0;
//                        status = false;
//                    }else if(selectedOption.equals("10%"))
//                    {
//                        selectedvalue = 10.0;
//                        status = false;
//                    }else if(selectedOption.equals("15%"))
//                    {
//                        selectedvalue = 15.0;
//                        status = false;
//                    }else if(selectedOption.equals("20%"))
//                    {
//                        selectedvalue = 20.0;
//                        status = false;
//                    }else if(selectedOption.equals("25%"))
//                    {
//                        selectedvalue = 25.0;
//                        status = false;
//                    }else if(selectedOption.equals( "Others"))
//                    {
//                        status = true;
//                    }

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
                clear(v);
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


    public void vat_calculate()
    {

        String selected_mode =  vatSpinnerData.getSelectedItem().toString();
        inputAmount =  ParseDouble(String.valueOf(amountText.getText()));
        double vatOtherValue =  ParseDouble(String.valueOf(vatOtherText.getText()));

    if(inputAmount > 0) {
        if (selected_mode.equals("ADD GST")) {

            if (status == true) {
                if(vatOtherValue > 0) {

                    selectedvalue = Double.parseDouble((String.valueOf(vatOtherText.getText())));
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


            if (status == true) {
                if(vatOtherValue > 0) {
                    selectedvalue = Double.valueOf((String.valueOf(vatOtherText.getText())));
                    vatPrice = inputAmount * selectedvalue / 100;
                    netPrice = inputAmount - vatPrice;
                }
                else
                {
                    Toast.makeText(VatCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }
            } else {

                vatPrice = inputAmount * selectedvalue / 100;
                netPrice = inputAmount - vatPrice;

            }

        }
        orginalCost.setText(String.valueOf(inputAmount));
        netPriceValue.setText(String.valueOf(netPrice));
        vatPriceText.setText(String.valueOf(vatPrice));
    }
    else
    {
        Toast.makeText(VatCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

    }

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
        amountText.setText("");
        vatOtherText.setText("");
        orginalCost.setText("0");
        netPriceValue.setText("0");
        vatPriceText.setText("0");

    }

}
