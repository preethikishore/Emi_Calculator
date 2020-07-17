package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


    RadioGroup radio_group;
    RadioButton radio_button;
    Button Calculate;
    private Spinner vat_Spinner_Data;
    private EditText amount_text;
    private EditText vat_other_text;
    private TextView orginal_cost;
    private TextView vat_price_text;
    private double vat_price;
    private double net_price;
    String selectedOption;
    double selectedvalue = 25.0;
    boolean status = false;
    private double input_amount;
    private TextView net_price_value;
    private DrawerLayout drawer;
    public Button shareButton;
    MessageComment messageComment = new MessageComment();
    final DecimalFormat df = new DecimalFormat("####0.00");

    String[] items = new String[]{
            "ADD GST","REMOVE GST"
    };
    SpinnerData spinnerData = new SpinnerData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
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


        amount_text = findViewById(R.id.vat_calculation_amount_value);
        vat_other_text = findViewById(R.id.vat_radio_Others_text);
        orginal_cost = findViewById(R.id.vat_orginal_cost_value);
        vat_price_text = findViewById(R.id.vat_price_value);
        net_price_value = findViewById(R.id.vat_net_price_value);
        radio_group = (RadioGroup) findViewById(R.id.vatRadioButtonGroup);
        Calculate = findViewById(R.id.vat_button_Interest_Calc_Calculate);
        shareButton = findViewById(R.id.vat_share_result);

        vat_Spinner_Data = findViewById(R.id.add_vat);
        spinnerData.initspinnerfooter(vat_Spinner_Data, VatCalculatorActivity.this, Arrays.asList(items));

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioid = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(radioid);


                if (radio_button.isChecked()) {
                    selectedOption = (String) radio_button.getText();

                    if (selectedOption.equals("Others")) {
                        vat_other_text.setVisibility(View.VISIBLE);

                    } else {
                        vat_other_text.setVisibility(View.INVISIBLE);
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

        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              vat_calculate();

            }
        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ShareInputAmount = String.valueOf(amount_text.getText());
                String ShareRateOfInterest = String.valueOf(selectedvalue);
                String ShareMaturity = String.valueOf(net_price);
                String ShareVatRate = String.valueOf(vat_price);


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

        String selected_mode =  vat_Spinner_Data.getSelectedItem().toString();
        input_amount =  ParseDouble(String.valueOf(amount_text.getText()));
        double vatOtherValue =  ParseDouble(String.valueOf(vat_other_text.getText()));

    if(input_amount > 0) {
        if (selected_mode.equals("ADD GST")) {

            if (status == true) {
                if(vatOtherValue > 0) {

                    selectedvalue = Double.parseDouble((String.valueOf(vat_other_text.getText())));
                    vat_price = input_amount * selectedvalue / 100;
                    net_price = input_amount + vat_price;
                }
                else
                {
                    Toast.makeText(VatCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }

            } else {
                vat_price = input_amount * selectedvalue / 100;
                net_price = input_amount + vat_price;
            }

        } else {


            if (status == true) {
                if(vatOtherValue > 0) {
                    selectedvalue = Double.valueOf((String.valueOf(vat_other_text.getText())));
                    vat_price = input_amount * selectedvalue / 100;
                    net_price = input_amount - vat_price;
                }
                else
                {
                    Toast.makeText(VatCalculatorActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }
            } else {

                vat_price = input_amount * selectedvalue / 100;
                net_price = input_amount - vat_price;

            }

        }
        orginal_cost.setText(String.valueOf(input_amount));
        net_price_value.setText(String.valueOf(net_price));
        vat_price_text.setText(String.valueOf(vat_price));
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

}
